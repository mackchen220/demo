package org.jeecg.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hy/user")
@Api(tags = "前台用户模块")
public class UserController {

    @Resource
    private UserModelService userModelService;
    @Resource
    private RedisUtil redisUtil;


    @ApiOperation("测试接口")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<JSONObject> login(String str) {
        UserModel user = userModelService.getUserById("12");
        Result<JSONObject> result = new Result<JSONObject>();
        log.info("这是测试接口{},user{}", str, JSONObject.toJSONString(user));
        return result;
    }


    @ApiOperation("前台用户登录接口")
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result<JSONObject> login(String inviteCode,  String captcha, String phone,String s) {
        Result<JSONObject> result = new Result<JSONObject>();
        if (captcha == null) {
            result.error500("请输入验证码");
            return result;
        }
        ValidateTool.checkParamIsMobile(phone);
//        String lowerCaseCaptcha = captcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(captcha + phone, "utf-8");
        Object checkCode = redisUtil.get(realKey);
        //当进入登录页时，有一定几率出现验证码错误 #1714
        if (checkCode == null || !checkCode.toString().equals(captcha)) {
            result.error500("验证码错误");
            return result;
        }
        JSONObject object = userModelService.userLogin(inviteCode, captcha, phone, "");
        result.setResult(object);
        return result;
    }
}
