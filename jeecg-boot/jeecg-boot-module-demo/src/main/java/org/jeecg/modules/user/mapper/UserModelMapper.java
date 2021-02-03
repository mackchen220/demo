package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserModel;

@Mapper
public interface UserModelMapper {


    int insertSelective(UserModel record);

    UserModel loadUser(@Param("Id") String userId,@Param("phone") String phone,@Param("nikename") String nikename,@Param("inviteCode") String invite_code);

    int updateByPrimaryKeySelective(UserModel record);

}
