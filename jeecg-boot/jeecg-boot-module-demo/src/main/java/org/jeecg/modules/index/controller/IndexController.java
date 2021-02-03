package org.jeecg.modules.index.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.index.service.CaptchaCodeService;
import org.jeecg.modules.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/hy/index")
@Api(tags = "前台首页模块")
public class IndexController {


    @Resource
    private IndexService indexService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("刷新首页接口")
    @RequestMapping(value = "/loadIndexlist", method = RequestMethod.POST)
    public Result<JSONObject> loadIndexlist() {

        Result result = indexService.loadIndexlist();


        return result;
    }



}
