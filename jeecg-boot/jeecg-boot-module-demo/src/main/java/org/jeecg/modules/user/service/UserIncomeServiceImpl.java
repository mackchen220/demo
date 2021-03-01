package org.jeecg.modules.user.service;

import lombok.extern.log4j.Log4j2;
import org.jeecg.modules.commons.util.DateHelper;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserIncomeDetailMapper;
import org.jeecg.modules.user.mapper.UserIncomeMapper;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.model.UserIncome;
import org.jeecg.modules.user.model.UserIncomeDetail;
import org.jeecg.modules.user.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Log4j2
@Service
public class UserIncomeServiceImpl implements UserIncomeService {

    @Resource
    private UserIncomeMapper userIncomeMapper;
    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private UserIncomeDetailMapper userIncomeDetailMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return userIncomeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserIncome record) {
        return userIncomeMapper.insert(record);
    }

    @Override
    public int insertSelective(UserIncome record) {
        return userIncomeMapper.insertSelective(record);
    }

    @Override
    public UserIncome selectByPrimaryKey(String id) {
        return userIncomeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UserIncome record) {
        return userIncomeMapper.updateByPrimaryKey(record);
    }

    //添加用户消费记录 并发问题后期mq队列解决
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void addUserIncome(String userId, Integer type, String contect, Long money) {
        log.warn("添加收入记录userId{},type{},contect{},money{}", userId, type, contect, money);
        UserIncomeDetail userIncomeDetail = new UserIncomeDetail();
        userIncomeDetail.setPayMoney(money);
        userIncomeDetail.setIncomeContent(contect);
        userIncomeDetail.setUserId(userId);
        userIncomeDetail.setId(SeqUtils.nextIdStr());
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);
        userIncomeDetail.setExcess(userModel.getMoney());
        userIncomeDetail.setPayType(String.valueOf(type));
        userIncomeDetailMapper.insertSelective(userIncomeDetail);

        String str = userId + DateHelper.getTodayTime();

        UserIncome userIncome = userIncomeMapper.getUserIncome(str);
        if (ValidateTool.isNull(userIncome)) {
            //添加记录
            UserIncome income = new UserIncome();
            income.setId(SeqUtils.nextIdStr());
            setMoney(type,userIncome,money);
            income.setSeqUnique(str);
            userIncomeMapper.insertSelective(income);
        } else {
            //修改记录
            UserIncome income = new UserIncome();
            income.setId(userIncome.getId());
            setMoney(type,income,money);
            userIncomeMapper.updateUserMoney(income);
        }

    }


    public void setMoney(int type,UserIncome userIncome,Long money){
        switch (type) {
            // 充值金额，单位：分
            case 1:
                userIncome.setRechargeMoney(money);
                break;
            //   * 推广收入，单位：分
            case 2:
                userIncome.setExtensionMoney(money);
                break;
            // 医美项目支出，单位：分
            case 3:
                userIncome.setProjectMoney(money);
                break;
            // 课程支出，单位：分
            case 4:
                userIncome.setCourseMoney(money);
                break;
            //佣金收入，单位：分
            case 5:
                userIncome.setCommissionMoney(money);
                break;
            //其他收入
            case 6:
                userIncome.setOtherMoney(money);
                break;
            //提现金额
            case 7:
                userIncome.setGetOutMoney(money);
                break;
            //其它支出
            default:
                userIncome.setOtherOutMoney(money);
        }
    }
}
