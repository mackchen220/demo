package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;
import org.jeecg.modules.course.model.Activity;
import org.jeecg.modules.course.model.vo.CourseInfoVo;
import org.jeecg.modules.course.model.vo.CourseVo;
import org.jeecg.modules.course.model.vo.UserCourseDetailVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.UserModelVo;

import java.util.List;
import java.util.Map;


public interface CourseService {

    IPage<CommunityModelVo> followList(Page<CommunityModelVo> page, String userId);

    IPage<UserCourseVo> findList(int pageNo, int pageSize, int type, String city);

    IPage<UserCourseVo> searchList(int pageNo, int pageSize, int type, String search);

    IPage<UserModelVo> loadMyFocus(Page<UserModelVo> page, String userId);

    UserCourseDetailVo searchInfoDetail(String id, int courseType, String userId);

    Map loadHengYangCourse(String type);

    List<PartyModel> loadHengYangActivity();

    Page<CourseVo> loadCommendCourse(Page<CourseVo> page ,String type);

    CourseVo getCourseInfo(String id, UserModel userModel);

    List<CourseInfoVo> getCourseInfoList(String courseId);

    Page<CourseVo> loadCourseModelList(Page<CourseVo> page, @Param("search")String search);

    Map addUserCourse(String courseId, UserModel userModel);

    String courseCallBack(String orderId);

}
