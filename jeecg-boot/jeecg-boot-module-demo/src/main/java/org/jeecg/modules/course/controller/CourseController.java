package org.jeecg.modules.course.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.course.service.CourseService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: CourseController
 * @description: 社区模块
 * @author: LongXiang
 * @data: 2021-02-05 16:41
 * @version: V1.0
 */
@RestController
@RequestMapping("/hy/course")
@Api(tags = "前台社区模块")
public class CourseController {

    @Resource
    private UserModelService userModelService;
    @Resource
    private CourseService courseService;

    @ApiOperation("关注列表")
    @PostMapping("/followList")
    public Result<?> followList(String token,
                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userId = userModelService.getUserIdByToken(token);
        IPage<Course> page = courseService.followList(new Page(pageNo, pageSize), userId);
        return Result.OK(page);
    }

}
