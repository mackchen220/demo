package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.UserCourseMapper;
import org.jeecg.modules.user.model.UserCourse;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Resource
    private UserCourseMapper userCourseMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return userCourseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserCourse record) {
        return userCourseMapper.insert(record);
    }

    @Override
    public int insertSelective(UserCourse record) {
        return userCourseMapper.insertSelective(record);
    }

    @Override
    public UserCourse selectByPrimaryKey(String id) {
        return userCourseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserCourse record) {
        return userCourseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserCourse record) {
        return userCourseMapper.updateByPrimaryKey(record);
    }

}
