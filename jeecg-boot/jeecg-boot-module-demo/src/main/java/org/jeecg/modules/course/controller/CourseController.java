package org.jeecg.modules.course.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.course.model.vo.UserCourseDetailVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;
import org.jeecg.modules.course.service.CourseService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @className: CourseController
 * @description: 社区模块
 * @author: LongXiang
 * @data: 2021-02-05 16:41
 * @version: V1.0
 */
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
    public Result<IPage<CommunityModel>> followList(String token,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userId = userModelService.getUserIdByToken(token);
        IPage<CommunityModel> page = courseService.followList(new Page<>(pageNo, pageSize), userId);
        return Result.OK(page);
    }

    @ApiOperation("社区发现动态列表")
    @PostMapping("/findList")
    public Result<IPage<UserCourseVo>> findList(@ApiParam(name = "查询类型", value = "1-推荐 2-视频 3-照片 4-知识 5-Vlog 6-活动 7-课程 不传默认1") Integer type,
                              @ApiParam(name = "城市", value = "不传默认全国") String city,
                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (ValidateTool.isNull(type)) {
            type = 1;
        }
        IPage<UserCourseVo> page = courseService.findList(pageNo, pageSize, type, city);
        return Result.OK(page);
    }

    @ApiOperation("社区搜索动态列表")
    @PostMapping("/searchList")
    public Result<IPage<UserCourseVo>> searchList(@ApiParam(name = "查询类型", value = "1-综合 2-最热 3-最新 不传默认1") Integer type,
                                @ApiParam(name = "查询关键词", value = "查询关键词") String search,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (ValidateTool.isNull(type)) {
            type = 1;
        }
        IPage<UserCourseVo> page = courseService.searchList(pageNo, pageSize, type, search);
        return Result.OK(page);
    }

    @ApiOperation("社区搜索动态-数据详情")
    @PostMapping("/searchInfoDetail")
    public Result<UserCourseDetailVo> searchInfoDetail(String token,
                                                       @ApiParam(name = "记录ID", required = true) String id,
                                                       @ApiParam(name = "数据来源类型", required = true, value = "1-朋友圈 2-课程 3-活动") Integer courseType) {
        if (ValidateTool.isNull(id) || ValidateTool.isNull(courseType)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        String userId = userModelService.getUserIdByToken(token);
        UserCourseDetailVo info = courseService.searchInfoDetail(id, courseType, userId);
        return Result.OK(info);
    }

    @ApiOperation("热搜标题")
    @PostMapping("/topSearch")
    public Result<List<String>> topSearch() {
        List<String> list = courseService.topSearch();
        return Result.OK(list);
    }

}
