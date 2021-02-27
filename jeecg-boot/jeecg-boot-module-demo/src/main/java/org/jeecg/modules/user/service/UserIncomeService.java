package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.UserIncome;
public interface UserIncomeService{


    int deleteByPrimaryKey(String id);

    int insert(UserIncome record);

    int insertSelective(UserIncome record);

    UserIncome selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserIncome record);

    int updateByPrimaryKey(UserIncome record);

    void addUserIncome(String userId,String type,String contect,String money);

}
