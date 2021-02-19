package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserAgencyModel;

@Mapper
public interface UserAgencyModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserAgencyModel record);

    int insertSelective(UserAgencyModel record);

    UserAgencyModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserAgencyModel record);

    int updateByPrimaryKey(UserAgencyModel record);

    int updateNum(@Param("id") String id);

    UserAgencyModel loadUserAgency(@Param("userId") String userId,@Param("puserId") String puserId);

}
