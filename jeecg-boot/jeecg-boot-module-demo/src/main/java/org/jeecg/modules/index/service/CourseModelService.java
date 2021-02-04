package org.jeecg.modules.index.service;

import org.jeecg.modules.index.model.CourseModel;
public interface CourseModelService{


    int deleteByPrimaryKey(String id);

    int insert(CourseModel record);

    int insertSelective(CourseModel record);

    CourseModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CourseModel record);

    int updateByPrimaryKey(CourseModel record);

}
