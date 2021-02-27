package org.jeecg.modules.user.service;

import org.jeecg.modules.commons.util.DateHelper;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserIncomeDetailMapper;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.model.UserIncomeDetail;
import org.jeecg.modules.user.model.UserModel;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.UserIncomeMapper;
import org.jeecg.modules.user.model.UserIncome;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserIncomeServiceImpl implements UserIncomeService{

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
    public int updateByPrimaryKeySelective(UserIncome record) {
        return userIncomeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserIncome record) {
        return userIncomeMapper.updateByPrimaryKey(record);
    }

    //添加用户消费记录 并发问题后期mq队列解决
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void addUserIncome(String userId, String type, String contect, String money) {
        UserIncomeDetail userIncomeDetail = new UserIncomeDetail();
        userIncomeDetail.setPayMoney(Long.valueOf(money));
        userIncomeDetail.setIncomeContent(contect);
        userIncomeDetail.setUserId(userId);
        userIncomeDetail.setId(SeqUtils.nextIdStr());
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);
        userIncomeDetail.setExcess(userModel.getMoney());
        userIncomeDetail.setPayType(type);
        userIncomeDetailMapper.insertSelective(userIncomeDetail);

        String str= userId+DateHelper.getTodayTime();

        UserIncome userIncome = userIncomeMapper.getUserIncome(str);
        if (ValidateTool.isNull(userIncome)){
            //添加记录
            UserIncome income = new UserIncome();
            income.setId(SeqUtils.nextIdStr());

        }else {
            //修改记录
        }

    }


}
