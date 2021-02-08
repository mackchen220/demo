package org.jeecg.modules.course.mapper;

import org.jeecg.modules.course.model.TopSearch;

import java.util.List;

public interface TopSearchMapper {
    int deleteByPrimaryKey(String id);

    int insert(TopSearch record);

    int insertSelective(TopSearch record);

    TopSearch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TopSearch record);

    int updateByPrimaryKey(TopSearch record);

    List<TopSearch> selectAll();

}