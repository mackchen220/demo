package org.jeecg.modules.course.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.course.model.vo.CourseInfoVo;
import org.jeecg.modules.course.model.vo.CourseVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;
import org.jeecg.modules.index.model.CourseModel;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {

    int insertSelective(Course record);

    Course selectByPrimaryKey(String id);

    CourseVo getCourseInfo(String id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> findListByUserId(Page page, @Param("userId") String userId);

    List<UserCourseVo> getListOrderByLikeNum(Page<UserCourseVo> page, @Param("courseType") Integer courseType,
                                             @Param("city") String city, @Param("userId") String userId);

    List<UserCourseVo> searchListOrderByType(Page<UserCourseVo> page, @Param("type") Integer type,
                                       @Param("search") String search, @Param("userId") String userId);


    List<CourseVo> loadCourseModelList(Page<CourseVo> page, @Param("search")String search);

    //首页四个课程推荐
    List<Course> loadIndexCourseModelList(@Param("limit") String limit);


    CourseVo getCourse(@Param("recommend") String recommend, @Param("banner") String banner);

    List<CourseVo> loadCourseListByType(@Param("courseType") String courseType,@Param("contentType") String contentType);

    List<CourseVo> loadCourseListPage(Page<CourseVo> page, @Param("courseType") String courseType);

    List<CourseVo> searchCourse(Page<CourseVo> page, @Param("search") String search);

    List<CourseInfoVo> getCourseInfoList(@Param("courseId") String courseId);


    //更新课程表收藏点赞转发观看数
    int updateCourseNum(@Param("courseId") String courseId, @Param("watchNum")Integer watchNum ,@Param("starNum")Integer starNum,
                       @Param("goodNum")Integer goodNum,@Param("forwardNum")Integer forwardNum);
}
