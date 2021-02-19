package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserStar;

@Mapper
public interface UserStarMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserStar record);

    int insertSelective(UserStar record);

    UserStar selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserStar record);

    int updateStar(@Param("communityId") String communityId, @Param("userId") String userId,
                   @Param("star") String star,@Param("good") String good);

    int isStar(@Param("communityId") String communityId, @Param("userId") String userId,
               @Param("star") String star,@Param("good") String good);


    UserStar loadUserStarByUserId(@Param("communityId") String communityId, @Param("userId") String userId);

}
