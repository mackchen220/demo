package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserFocusModel;

@Mapper
public interface UserFocusModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFocusModel record);

    int insertSelective(UserFocusModel record);

    UserFocusModel selectById(@Param("userId") String userId,@Param("focusUserId") String focusUserId);

    int updateByPrimaryKeySelective(UserFocusModel record);

    int updateByPrimaryKey(UserFocusModel record);

    int updatedDelFlag(@Param("userId") String userId,@Param("focusUserId") String focusUserId,@Param("flag") String flag);



}
