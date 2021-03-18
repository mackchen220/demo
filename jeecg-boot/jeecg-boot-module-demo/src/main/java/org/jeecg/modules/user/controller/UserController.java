package org.jeecg.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.*;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.model.*;
import org.jeecg.modules.user.model.vo.AddressModelVo;
import org.jeecg.modules.user.model.vo.ExtensionVo;
import org.jeecg.modules.user.model.vo.UserBankVo;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;
import org.jeecg.modules.user.service.*;
import org.jeecg.modules.webAdmin.bank.entity.AdminBank;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private VipModelService vipModelService;


    @DynamicResponseParameters(name = "login", properties = {
            @DynamicParameter(name = "headImage", value = "头像"),
            @DynamicParameter(name = "nickname", value = "昵称"),
            @DynamicParameter(name = "first", value = "是否第一次注册 1是 0不是"),
            @DynamicParameter(name = "token", value = "token 登录以后每个`接口都需要token参数"
                    , example = "88291a6fbf21a186165037f866aedc63,3c1138239ddca40a8f49d8a89d78c6f3"),
    })
    @ApiOperation("前台用户登录接口")
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result<JSONObject> login(String inviteCode, String captcha, String phone, String unionId, HttpServletRequest request) {

        JSONObject object = userModelService.userLogin(inviteCode, captcha, phone, unionId, IPUtils.getIpAddr(request));
        return Result.OK(object);
    }


    @ApiOperation("微信登录")
    @RequestMapping(value = "/weixinLogin", method = RequestMethod.POST)
    public Result weixinLogin(WeiXinModel weiXinModel, String phone) {

        Map<String, Object> map = userModelService.weixinLogin(weiXinModel, phone);

        return Result.OK(map);
    }

    @ApiOperation("微信登录，绑定手机号")
    @RequestMapping(value = "/bindUserPhone", method = RequestMethod.POST)
    public Result bindUserPhone(String unionId, String phone, String captcha) {
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

        userModelService.bindUserPhone(unionId, phone, captcha);

        return Result.OK();
    }


    @ApiOperation("填写邀请码")
    @RequestMapping(value = "/addUserAgency", method = RequestMethod.POST)
    public Result addUserAgency(String token, String inviteCode) {
        String id = userModelService.getUserIdByToken(token);
        userModelService.addUserAgencyModel(inviteCode, id);
        return Result.OK();
    }


    @ApiOperation("添加银行卡接口")
    @RequestMapping(value = "/addUserBank", method = RequestMethod.POST)
    public Result<JSONObject> loadIndexlist(UserBankModel userBankModel, String phone, String captchaCode, HttpServletRequest request) {

        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Result result = userBankModelService.insertUserBank(userBankModel, captchaCode, phone, id);
        return result;
    }


    @ApiOperation("用户银行卡列表接口")
    @RequestMapping(value = "/loadUserCardList", method = RequestMethod.POST)
    public Result<Object> loadUserCardList(HttpServletRequest request) {
        Result<Object> result = new Result<>();
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        List<UserBankVo> userBankModels = userBankModelService.loadUserCard(id);
        result.setResult(userBankModels);
        return result;
    }


    @ApiOperation(value = "银行列表")
    @PostMapping(value = "/loadBankList")
    public Result<?> loadBankList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<BankModel> page = new Page<BankModel>(pageNo, pageSize);
        IPage<BankModel> pageList = userBankModelService.loadBankList(page);
        return Result.OK(pageList);
    }

    @ApiOperation("用户关注接口")
    @RequestMapping(value = "/addUserFocus", method = RequestMethod.POST)
    public Result addUserFocus(HttpServletRequest request, String userId) {
        String token = request.getHeader("token");
        String id = userModelService.getUserIdByToken(token);
        Result result = userFocusModelService.addUserFocus(id, userId);
        return result;
    }


    @ApiOperation("用户取消关注接口")
    @RequestMapping(value = "/delUserFocus", method = RequestMethod.POST)
    public Result delUserFocus(HttpServletRequest request, String userId) {
        String token = request.getHeader("token");
        String id = userModelService.getUserIdByToken(token);
        Result result = userFocusModelService.delUserFocus(id, userId);
        return result;
    }


    @ApiOperation("修改用户信息")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Result updateUserInfo(String nickName, String headImage, String sign, HttpServletRequest request, String wechat) {
        String token = request.getHeader("token");
        UserModel userModel = userModelService.getUserModelByToken(token);
        userModelService.updateUserInfo(userModel, nickName, headImage, sign, wechat);
        return Result.OK("修改成功", null);
    }

    @ApiOperation("用户修改手机号")
    @RequestMapping(value = "/updateUserPhone", method = RequestMethod.POST)
    public Result updateUserPhone(String phone, String captcha, HttpServletRequest request) {
        String token = request.getHeader("token");
        UserModel userModel = userModelService.getUserModelByToken(token);
        userModelService.updateUserPhone(phone, captcha, userModel);
        return Result.OK("修改成功", null);
    }


    @ApiOperation("我的页面点击头像加载用户信息")
    @RequestMapping(value = "/loadUserInfo", method = RequestMethod.POST)
    public Result<Map> loadUserInfo(HttpServletRequest request) {

        Map map = userModelService.loadUserInfo(TokenUtils.getToken(request));
        return Result.OK(map);
    }


    @ApiOperation("我的收货地址")
    @RequestMapping(value = "/loadUserAddressList", method = RequestMethod.POST)
    public Result<List> loadUserAddressList(HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        List list = addressModelService.loadUserAddressList(id);
        return Result.OK(list);
    }


    @ApiOperation("添加收货地址")
    @RequestMapping(value = "/addUserAddress", method = RequestMethod.POST)
    public Result addUserAddress(AddressModelVo model, HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        model.setUserId(id);
        addressModelService.insertSelective(model);
        return Result.OK();
    }


    @ApiOperation("修改收货地址")
    @RequestMapping(value = "/updateUserAddress", method = RequestMethod.POST)
    public Result updateUserAddress(AddressModel model, HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        model.setUserId(id);
        addressModelService.updateByPrimaryKeySelective(model);
        return Result.OK();
    }


    @ApiOperation("vip列表")
    @RequestMapping(value = "/getVipList", method = RequestMethod.POST)
    public Result<List> getVipList() {
        List list = vipModelService.getVipList();
        return Result.OK(list);
    }

    @ApiOperation("购买会员卡，提交订单")
    @RequestMapping(value = "/addVipOrder", method = RequestMethod.POST)
    public Result<Map> addVipOrder(HttpServletRequest request, String addressId, String vipId) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Map map = vipModelService.addVipOrder(addressId, vipId, id);
        return Result.OK(map);
    }


    @ApiOperation("购买会员卡，确认订单")
    @RequestMapping(value = "/getVipOrder", method = RequestMethod.POST)
    public Result<Map> getVipOrder(HttpServletRequest request, String addressId, String vipId, String orderId) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Map map = vipModelService.getVipOrder(addressId, vipId, orderId,id);
        return Result.OK(map);
    }


    @ApiOperation("我的钱包")
    @RequestMapping(value = "/loadMyWalletInfo", method = RequestMethod.POST)
    public Result<Map> loadMyWalletInfo(HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Map map = userModelService.loadMyWalletInfo(id);
        return Result.OK(map);
    }


    @ApiOperation("收益记录")
    @RequestMapping(value = "/loadIncomeDetail", method = RequestMethod.POST)
    public Result<Page<UserIncomeDetailVo>> loadIncomeDetail(HttpServletRequest request, Integer pageNo, Integer pageSize, Integer type) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Page<UserIncomeDetailVo> page = new Page<>(pageNo, pageSize);
        Page<UserIncomeDetailVo> incomeDetail = userModelService.loadIncomeDetail(id, page, type, null, null);
        return Result.OK(incomeDetail);
    }


    @ApiOperation("实名认证")
    @RequestMapping(value = "/addUserVerified", method = RequestMethod.POST)
    public Result addUserVerified(HttpServletRequest request, String userName, String idNum, String image) {
        UserModel user = userModelService.getUserModelByToken(TokenUtils.getToken(request));
        userModelService.addUserVerified(user, userName, idNum, image);
        return Result.OK();
    }


    //代理中心
    @ApiOperation("代理中心")
    @PostMapping("/loadProxyCenter")
    public Result loadProxyCenter(HttpServletRequest request) {
        UserModel model = userModelService.getUserModelByToken(TokenUtils.getToken(request));
        Map map = userModelService.loadProxyCenter(model);
        return Result.OK(map);
    }

    //代理中心分页详情
    @ApiOperation("代理中心分页详情")
    @PostMapping("/loadProxyIncome")
    public Result loadProxyIncome(HttpServletRequest request,  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));

        Page<ExtensionVo> page = new Page<>(pageNo, pageSize);
        Page<ExtensionVo> extensionVoPage = userModelService.loadProxyIncome(id, page);

        return Result.OK(extensionVoPage);
    }


}
