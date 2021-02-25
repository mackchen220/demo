package org.jeecg.modules.course.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.course.model.vo.CourseInfoVo;
import org.jeecg.modules.course.model.vo.CourseVo;
import org.jeecg.modules.index.model.CourseModel;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {

    int insertSelective(Course record);

    Course selectByPrimaryKey(String id);

    CourseVo getCourseInfo(String id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> findListByUserId(Page page, @Param("userId") String userId);

    List<Course> getListOrderByLikeNum(Page<Course> page, @Param("courseType") Integer courseType, @Param("city") String city);

    List<Course> searchListOrderByType(Page<Course> page, @Param("type") Integer type, @Param("search") String search);


    List<Course> loadCourseModelList(Page<CourseModel> page, @Param("search")String search);

    //首页四个课程推荐
    List<Course> loadIndexCourseModelList(@Param("limit") String limit);


    CourseVo getCourse(@Param("recommend") String recommend, @Param("banner") String banner);

    List<CourseVo> loadCourseListByType(@Param("courseType") String courseType,@Param("contentType") String contentType);


    List<CourseInfoVo> getCourseInfoList(@Param("courseId") String courseId);
}
