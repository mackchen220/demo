package org.jeecg.modules.index.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.index.mapper.CourseModelMapper;
import org.jeecg.modules.index.model.CourseModel;
import org.jeecg.modules.index.service.CourseModelService;
@Service
public class CourseModelServiceImpl implements CourseModelService{

    @Resource
    private CourseModelMapper courseModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return courseModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CourseModel record) {
        return courseModelMapper.insert(record);
    }

    @Override
    public int insertSelective(CourseModel record) {
        return courseModelMapper.insertSelective(record);
    }

    @Override
    public CourseModel selectByPrimaryKey(String id) {
        return courseModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CourseModel record) {
        return courseModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CourseModel record) {
        return courseModelMapper.updateByPrimaryKey(record);
    }

}
