package org.jeecg.modules.course.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;
import org.jeecg.modules.course.model.vo.CourseInfoVo;
import org.jeecg.modules.course.model.vo.CourseVo;
import org.jeecg.modules.course.model.vo.UserCourseDetailVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;
import org.jeecg.modules.course.service.CourseService;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.UserModelVo;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/hy/course")
@Api(tags = "前台社区模块",
        basePath = "/hy/course",
        description = "社区这一栏的视频vlog和照片数据是来自发朋友圈功能，知识，活动，课程是后台添加的，知识是文章版的课程")
public class CourseController {

    @Resource
    private UserModelService userModelService;
    @Resource
    private CourseService courseService;

    @ApiOperation("社区关注动态列表")
    @PostMapping("/followList")
    public Result<IPage<CommunityModelVo>> followList(HttpServletRequest request,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userId = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        IPage<CommunityModelVo> page = courseService.followList(new Page<>(pageNo, pageSize), userId);
        return Result.OK(page);
    }

    @ApiOperation("社区关注人列表")
    @PostMapping("/loadMyFocus")
    public Result<IPage<UserModelVo>> loadMyFocus(HttpServletRequest request, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userId = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Page<UserModelVo> page = new Page<>(pageNo, pageSize);
        IPage<UserModelVo> pageRsult = courseService.loadMyFocus(page, userId);
        return Result.OK(pageRsult);
    }


    @ApiOperation("社区发现动态列表")
    @PostMapping("/findList")
    public Result<IPage<UserCourseVo>> findList(@ApiParam(name = "查询类型", value = "1-推荐 2-视频 3-照片 4-知识 5-Vlog 6-活动 7-课程 不传默认1") Integer type,
                                                @ApiParam(name = "城市", value = "不传默认全国") String city,
                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                HttpServletRequest request) {
        String userId = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        IPage<UserCourseVo> page = courseService.findList(pageNo, pageSize, ValidateTool.isNull(type) ? 1 : type, city, userId);
        return Result.OK(page);
    }

    @ApiOperation("社区搜索动态列表")
    @PostMapping("/searchList")
    public Result<IPage<UserCourseVo>> searchList(@ApiParam(name = "查询类型", value = "1-综合 2-最热 3-最新 不传默认1") Integer type,
                                                  @ApiParam(name = "查询关键词", value = "查询关键词") String search,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest request) {
        String userId = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        IPage<UserCourseVo> page = courseService.searchList(pageNo, pageSize, ValidateTool.isNull(type) ? 1 : type, search, userId);
        return Result.OK(page);
    }

    @ApiOperation("社区搜索动态-数据详情")
    @PostMapping("/searchInfoDetail")
    public Result<UserCourseDetailVo> searchInfoDetail(HttpServletRequest request, @ApiParam(name = "记录ID", required = true) String id,
                                                       @ApiParam(name = "数据来源类型", required = true, value = "1-朋友圈 2-课程 3-活动")
                                                               Integer courseType) {
        if (ValidateTool.isNull(id) || ValidateTool.isNull(courseType)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        String userId = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        UserCourseDetailVo info = courseService.searchInfoDetail(id, courseType, userId);
        return Result.OK(info);
    }


    @ApiOperation("亨氧学院")
    @PostMapping("/loadHengYangCourse")
    public Result loadHengYangCourse(String type) {

        Map map = courseService.loadHengYangCourse(type);
        return Result.OK(map);
    }

    @ApiOperation("亨氧学院,活动")
    @PostMapping("/loadHengYangActivity")
    public Result loadHengYangActivity() {

        List<PartyModel> partyModels = courseService.loadHengYangActivity();
        return Result.OK(partyModels);
    }

    @ApiOperation("课程详情")
    @PostMapping("/getCourseInfo")
    public Result getCourseInfo(HttpServletRequest request, String id) {

        String token = TokenUtils.getToken(request);
        UserModel userModel = userModelService.getUserModelByToken(token);
        CourseVo courseInfo = courseService.getCourseInfo(id, userModel);
        return Result.OK(courseInfo);
    }

    @ApiOperation("课程推荐")
    @PostMapping("/loadCommendCourse")
    public Result loadCommendCourse(String type, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<CourseVo> page = new Page<>(pageNo, pageSize);
        Page<CourseVo> courseVoPage = courseService.loadCommendCourse(page, type);
        return Result.OK(courseVoPage);
    }

    @ApiOperation("课程章节列表")
    @PostMapping("/getCourseInfoList")
    public Result getCourseInfoList(String token, String id) {

        List<CourseInfoVo> courseInfoList = courseService.getCourseInfoList(id);
        return Result.OK(courseInfoList);
    }


    @ApiOperation("购买课程")
    @RequestMapping(value = "/addUserCourse", method = RequestMethod.POST)
    public Result<Map> addUserCourse(HttpServletRequest request, String courseId) {
        UserModel model = userModelService.getUserModelByToken(TokenUtils.getToken(request));
        Map map = courseService.addUserCourse(courseId, model);
        return Result.OK(map);
    }


    @ApiOperation("首页搜索课程")
    @PostMapping("/searchCourse")
    public Result searchCourse(String search, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<CourseVo> page = new Page<>(pageNo, pageSize);
        Page<CourseVo> courseVoPage = courseService.searchCourse(page, search);
        return Result.OK(courseVoPage);
    }



}
