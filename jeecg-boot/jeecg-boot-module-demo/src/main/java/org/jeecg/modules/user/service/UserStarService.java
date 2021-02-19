package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.UserStar;
public interface UserStarService{


    int deleteByPrimaryKey(String id);

    int insert(UserStar record);

    int insertSelective(UserStar record);

    UserStar selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserStar record);


}
