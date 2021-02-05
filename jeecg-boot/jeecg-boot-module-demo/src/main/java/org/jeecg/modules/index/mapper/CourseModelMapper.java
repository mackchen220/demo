package org.jeecg.modules.index.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.Community.model.CommunityModel;
import org.jeecg.modules.index.model.CourseModel;

import java.util.List;

public interface CourseModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(CourseModel record);

    int insertSelective(CourseModel record);

    CourseModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CourseModel record);

    int updateByPrimaryKey(CourseModel record);


    List<CourseModel> loadCourseModelList(Page<CourseModel> page, @Param("search")String search);

    //首页四个课程推荐
    List<CourseModel> loadIndexCourseModelList(@Param("limit") String limit);

}
