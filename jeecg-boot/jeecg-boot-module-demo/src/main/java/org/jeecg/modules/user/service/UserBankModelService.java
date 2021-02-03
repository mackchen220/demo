package org.jeecg.modules.user.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserBankModel;

import java.util.List;

public interface UserBankModelService{



    Result insertUserBank(UserBankModel record,String captchaCode,String phone);

    UserBankModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserBankModel record);

    List<UserBankModel> loadUserCard(String userId);


}
