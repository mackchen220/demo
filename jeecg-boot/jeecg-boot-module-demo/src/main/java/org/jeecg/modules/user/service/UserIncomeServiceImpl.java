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
    public synchronized void addUserIncome(String userId, Integer type, String contect, Long money, String other) {
        log.warn("添加收入记录userId{},type{},contect{},money{}", userId, type, contect, money);
        UserIncomeDetail userIncomeDetail = new UserIncomeDetail();
        userIncomeDetail.setPayMoney(money);
        userIncomeDetail.setIncomeContent(contect);
        userIncomeDetail.setUserId(userId);
        userIncomeDetail.setId(SeqUtils.nextIdStr());
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null, null);
        userIncomeDetail.setExcess(ValidateTool.isNotNull(userModel) ? userModel.getMoney() : 0);
        //    * 收入支出类型1:提现 2充值 推广奖励  4项目佣金 5购买课程 6购买项目
        userIncomeDetail.setIncomeType(type);
        if (type == 1) {
            //手续费
            userIncomeDetail.setFee(other);
        }
        if (type == 3) {
            //手续费
            userIncomeDetail.setSendId(other);
        }
        userIncomeDetailMapper.insertSelective(userIncomeDetail);

        String str = userId + DateHelper.getToday();

        UserIncome userIncome = userIncomeMapper.getUserIncome(str);
        if (ValidateTool.isNull(userIncome)) {
            //添加记录
            UserIncome income = new UserIncome();
            income.setId(SeqUtils.nextIdStr());
            setMoney(type, income, money);
            income.setSeqUnique(str);
            income.setUserId(userId);
            userIncomeMapper.insertSelective(income);
        } else {
            //修改记录
            UserIncome income = new UserIncome();
            income.setId(userIncome.getId());
            setMoney(type, income, money);
            userIncomeMapper.updateUserMoney(income);
        }

    }

    //    * 收入支出类型1:提现 2充值 推广奖励  4项目佣金 5购买课程 6购买项目
    public void setMoney(int type, UserIncome userIncome, Long money) {
        switch (type) {
            case 1:
                userIncome.setGetOutMoney(money);
                break;
            case 2:
                userIncome.setRechargeMoney(money);
                break;
            case 3:
                userIncome.setExtensionMoney(money);
                break;
            case 4:
                userIncome.setCommissionMoney(money);
                break;
            case 5:
                userIncome.setCourseMoney(money);
                break;
            case 6:
                userIncome.setProjectMoney(money);
                break;
            case 7:
                userIncome.setOtherMoney(money);
                break;
            default:
                userIncome.setOtherOutMoney(money);
        }
    }
}
