package org.jeecg.modules.user.mapper;

import org.jeecg.modules.user.model.UserCourse;

public interface UserCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserCourse record);

    int insertSelective(UserCourse record);

    UserCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCourse record);

    int updateByPrimaryKey(UserCourse record);
}
