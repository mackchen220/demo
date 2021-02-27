package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserBankModel;

import java.util.List;

public interface UserBankModelMapper {


    int insertSelective(UserBankModel record);

    UserBankModel loadBankInfoByUserId(@Param("cardNumber") String cardNumber,@Param("id") String id
            , @Param("userId") String userId);

    int updateByPrimaryKeySelective(UserBankModel record);


    List<UserBankModel> loadUserCard(@Param("userId") String userId);
}
