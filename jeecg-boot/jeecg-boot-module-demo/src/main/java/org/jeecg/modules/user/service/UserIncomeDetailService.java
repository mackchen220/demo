package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.UserIncomeDetail;
public interface UserIncomeDetailService{


    int deleteByPrimaryKey(String id);

    int insert(UserIncomeDetail record);

    int insertSelective(UserIncomeDetail record);

    UserIncomeDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserIncomeDetail record);

    int updateByPrimaryKey(UserIncomeDetail record);

}
