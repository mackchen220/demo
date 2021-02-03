package org.jeecg.modules.user.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserBankModel;
import org.jeecg.modules.user.model.vo.UserBankVo;

import java.util.List;

public interface UserBankModelService{



    Result insertUserBank(UserBankModel record,String captchaCode,String phone,String token);

    UserBankModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserBankModel record);

    List<UserBankVo> loadUserCard(String userId);


}
