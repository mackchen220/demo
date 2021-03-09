package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserModel;

@Mapper
public interface UserModelMapper {


    int insertSelective(UserModel record);

    UserModel loadUser(@Param("Id") String userId, @Param("phone") String phone, @Param("nickName") String nickName,
                       @Param("inviteCode") String invite_code, @Param("weixinId") String weixinId);

    UserModel loadUserByUserName(@Param("userName") String userName);

    int updateByPrimaryKeySelective(UserModel record);

    int updateUserMoney(@Param("userId") String userId, @Param("money") String money, @Param("type") Integer type);


    void updateUserDelFlag(@Param("userId") String userId, @Param("type") Integer type);

}
