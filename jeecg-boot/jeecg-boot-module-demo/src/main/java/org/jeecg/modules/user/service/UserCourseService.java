package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.UserCourse;
public interface UserCourseService {


    int deleteByPrimaryKey(String id);

    int insert(UserCourse record);

    int insertSelective(UserCourse record);

    UserCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCourse record);

    int updateByPrimaryKey(UserCourse record);

}
