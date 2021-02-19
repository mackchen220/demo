package org.jeecg.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
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
import org.jeecg.modules.user.model.UserBankModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.AddressModelVo;
import org.jeecg.modules.user.model.vo.UserBankVo;
import org.jeecg.modules.user.service.AddressModelService;
import org.jeecg.modules.user.service.UserBankModelService;
import org.jeecg.modules.user.service.UserFocusModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private UserBankModelService userBankModelService;
    @Resource
    private UserFocusModelService userFocusModelService;

    @Resource
    private AddressModelService addressModelService;

//    @ApiOperation("测试接口")
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public Result<JSONObject> login(String str) {
//        UserModel user = userModelService.getUserById("12");
//        Result<JSONObject> result = new Result<JSONObject>();
//        log.info("这是测试接口{},user{}", str, JSONObject.toJSONString(user));
//        return result;
//    }



    @DynamicResponseParameters(name = "login",properties = {
            @DynamicParameter(name = "headImage",value = "头像"),
            @DynamicParameter(name = "nickname",value = "昵称" ),
            @DynamicParameter(name = "first",value = "是否第一次注册 1是 0不是" ),
            @DynamicParameter(name = "token",value = "token 登录以后每个`接口都需要token参数"
                    ,example = "88291a6fbf21a186165037f866aedc63,3c1138239ddca40a8f49d8a89d78c6f3"),
    })
    @ApiOperation("前台用户登录接口")
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result<JSONObject> login(String inviteCode, String captcha, String phone) {
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
        JSONObject object = userModelService.userLogin(inviteCode, captcha, phone);
        result.setResult(object);
        return result;
    }

    @ApiOperation("填写邀请码")
    @RequestMapping(value = "/addUserAgency", method = RequestMethod.POST)
    public Result addUserAgency( String token,String inviteCode) {
        String id = userModelService.getUserIdByToken(token);
        userModelService.addUserAgencyModel(inviteCode,id);
        return Result.OK();
    }


    @ApiOperation("添加银行卡接口")
    @RequestMapping(value = "/addUserBank", method = RequestMethod.POST)
    public Result<JSONObject> loadIndexlist(UserBankModel userBankModel, String phone, String captchaCode, String token) {

        String id = userModelService.getUserIdByToken(token);


        Result result = userBankModelService.insertUserBank(userBankModel, captchaCode, phone, id);


        return result;
    }


    @ApiOperation("用户银行卡列表接口")
    @RequestMapping(value = "/loadUserCardList", method = RequestMethod.POST)
    public Result<Object> loadUserCardList( String token) {
        Result<Object> result = new Result<>();
        String id = userModelService.getUserIdByToken(token);
        List<UserBankVo> userBankModels = userBankModelService.loadUserCard(id);
        result.setResult(userBankModels);
        return result;
    }


    @ApiOperation("用户关注接口")
    @RequestMapping(value = "/addUserFocus", method = RequestMethod.POST)
    public Result addUserFocus( String token,String userId) {
        String id = userModelService.getUserIdByToken(token);
        Result result = userFocusModelService.addUserFocus(id, userId);
        return result;
    }


    @ApiOperation("用户取消关注接口")
    @RequestMapping(value = "/delUserFocus", method = RequestMethod.POST)
    public Result delUserFocus( String token,String userId) {
        String id = userModelService.getUserIdByToken(token);
        Result result = userFocusModelService.delUserFocus(id, userId);
        return result;
    }



    @ApiOperation("修改用户信息")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Result updateUserInfo(String nickName,String headImage,String sign,String token) {
        UserModel userModel = userModelService.getUserModelByToken(token);
        userModelService.updateUserInfo(userModel, nickName, headImage, sign);
        return Result.OK("修改成功",null);
    }


    @ApiOperation("我的页面点击头像加载用户信息")
    @RequestMapping(value = "/loadUserInfo", method = RequestMethod.POST)
    public Result<Map> loadUserInfo(String token) {
        Map map = userModelService.loadUserInfo(token);
        return Result.OK(map);
    }


    @ApiOperation("我的收货地址")
    @RequestMapping(value = "/loadUserAddressList", method = RequestMethod.POST)
    public Result<List> loadUserAddressList(String token) {
        String id = userModelService.getUserIdByToken(token);
        List list = addressModelService.loadUserAddressList(id);
        return Result.OK(list);
    }


    @ApiOperation("添加收货地址")
    @RequestMapping(value = "/addUserAddress", method = RequestMethod.POST)
    public Result addUserAddress(AddressModelVo model,String token) {
        String id = userModelService.getUserIdByToken(token);
        model.setUserId(id);
        addressModelService.insertSelective(model);
        return Result.OK();
    }



}
