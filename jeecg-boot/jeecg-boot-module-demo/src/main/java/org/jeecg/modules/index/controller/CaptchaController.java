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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/hy/index")
@Api(tags = "前台验证码模块")
public class CaptchaController {


    @Resource
    private CaptchaCodeService captchaCodeService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("获取验证码接口")
    @RequestMapping(value = "/getCaptchaCode", method = RequestMethod.POST)
    public Result<JSONObject> getCaptchaCode(String phone) {
        ValidateTool.checkParamIsMobile(phone);
        //随机验证码六位数，短信验证码暂时不做
        String captchaCode = captchaCodeService.getCaptchaCode(6);
        String realKey = MD5Util.MD5Encode(captchaCode + phone, "utf-8");
        redisUtil.set(realKey, captchaCode, 60);
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", captchaCode);
        result.setResult(jsonObject);
        log.info("checkKey{},,,,验证码{},,,,,phone{}",phone,captchaCode,realKey);
        return result;
    }


    @ApiOperation("获取手机验证码接口")
    @RequestMapping(value = "/getPhoneCaptchaCode", method = RequestMethod.POST)
    public Result<JSONObject> getPhoneCaptchaCode(String phone) {

        String captchaCode = captchaCodeService.getCaptchaCode(6);
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", captchaCode);
        result.setResult(jsonObject);
        return result;
    }

}
