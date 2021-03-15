package org.jeecg.modules.user.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.DateHelper;
import org.jeecg.modules.commons.util.RandomUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.*;
import org.jeecg.modules.user.model.*;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class UserModelServiceImpl implements UserModelService {


    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private UserAgencyModelMapper userAgencyModelMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private UserIncomeDetailMapper userIncomeDetailMapper;
    @Resource
    private UserIncomeMapper userIncomeMapper;

    @Value("${justauth.type.WECHAT_OPEN.client-id}")
    private String accessKeyId;

    @Value("${justauth.type.WECHAT_OPEN.client-client-secret}")
    private String secret;

    @Resource
    private VerifiedLogMapper verifiedLogMapper;

    @Resource
    private VerifiedConfigMapper verifiedConfigMapper;

    @Resource
    private UserAgencyModelMapper userAgencyModelMapperl;

    @Override
    public UserModel getUserById(String id) {
        UserModel userModel = userModelMapper.loadUser(id, null, null, null, null);
        return userModel;
    }

    @Override
    public UserModel getUserModelByToken(String token) {
        String userId = (String) redisUtil.get(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token);
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null, null);
        if (!ValidateTool.checkIsNull(userModel)) {
            throw new JeecgBootException("登录信息过期，请重新登录");
        }
        return userModel;
    }

    @Override
    public String getUserIdByToken(String token) {
        String userId = (String) redisUtil.get(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token);
        if (!ValidateTool.checkIsNull(userId)) {
            throw new JeecgBootException("登录信息过期，请重新登录");
        }
        return userId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject userLogin(String inviteCode, String captcha, String phone, String unionId, String ip) {
        if (ValidateTool.isNull(phone) && ValidateTool.isNull(unionId)) {
            throw new JeecgBootException("非法参数");
        }
        JSONObject jsonObject = new JSONObject();
        if (ValidateTool.isNotNull(phone) && ValidateTool.isNotNull(unionId)) {
            //第一次登录
            checkCaptchaCode(phone, captcha);
            UserModel userModel = userModelMapper.loadUser(null, phone, null, null, unionId);
            UserModel model = reg(userModel, unionId, inviteCode, phone, ip);
            login(model, jsonObject);
        } else if (ValidateTool.isNotNull(phone)) {
            checkCaptchaCode(phone, captcha);
            UserModel userModel = userModelMapper.loadUser(null, phone, null, null, null);
            if (ValidateTool.isNull(userModel)) {
                throw new JeecgBootException("手机号未注册");
            }
            login(userModel, jsonObject);
        } else {
            //微信登录
            UserModel userModel = userModelMapper.loadUser(null, null, null, null, unionId);
            if (ValidateTool.isNull(userModel)) {
                throw new JeecgBootException("微信号未授权");
            }
            login(userModel, jsonObject);
        }

        return jsonObject;
    }

    public void checkCaptchaCode(String phone, String captcha) {
        //手机号或者微信登录
        if (captcha == null) {
            throw new JeecgBootException("请输入验证码");
        }
        ValidateTool.checkParamIsMobile(phone);
//        String realKey = MD5Util.MD5Encode(captcha + phone, "utf-8");
//        Object checkCode = redisUtil.get(realKey);
//        //当进入登录页时，有一定几率出现验证码错误 #1714
//        if (checkCode == null || !checkCode.toString().equals(captcha)) {
//            throw new JeecgBootException("验证码错误");
//        }
        Object code = redisUtil.get(RedisKey.SMS_CODE + phone);

        //当进入登录页时，有一定几率出现验证码错误 #1714
        if (code == null || !code.toString().equals(captcha)) {
            throw new JeecgBootException("验证码错误");
        }


    }

    public void login(UserModel userModel, JSONObject object) {
        // 生成前台token
        String token;
        String encrypt = MD5Util.MD5Encode(userModel.getId(), "utf-8");

        String timeMillis = MD5Util.MD5Encode(String.valueOf(System.currentTimeMillis()), "utf-8");
        // 设置token缓存有效时间
        redisUtil.set(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + encrypt, timeMillis);
        redisUtil.expire(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + encrypt, JwtUtil.EXPIRE_TIME * 336);
//        redisUtil.hset(RedisKey.USER_LOGIN_TOKEN +RedisKey.KEY_SPLIT+token, userModel.getId(), JSONObject.toJSONString(userModel));
        token = encrypt + "," + timeMillis;
        redisUtil.set(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token, userModel.getId());
        redisUtil.expire(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token, JwtUtil.EXPIRE_TIME * 336);
        object.put("token", token);
        object.put("headImage", userModel.getHeadImage());
        object.put("nickname", userModel.getNickName());
    }


    public UserModel reg(UserModel userModel, String unionId, String inviteCode, String phone, String ip) {
        if (ValidateTool.isNull(inviteCode)) {
            throw new JeecgBootException("请填写邀请码");
        }
        if (ValidateTool.isNull(unionId)) {
            throw new JeecgBootException("请绑定微信");
        }
        UserModel userModel2 = userModelMapper.loadUser(null, null, null, inviteCode.toUpperCase(), null);
        if (ValidateTool.isNull(userModel2)) {
            throw new JeecgBootException("邀请码错误");
        }
        if (ValidateTool.isNull(userModel)) {
            userModel = new UserModel();
            //未注册的新用户自动注册
            userModel.setId(SeqUtils.nextIdStr());
            userModel.setPhone(phone);
            // 获取微信用户信息
            Object city = redisUtil.hget(RedisKey.WEIXIN_USER_INFO + unionId, "city");
            Object province = redisUtil.hget(RedisKey.WEIXIN_USER_INFO + unionId, "province");
            Object nickName = redisUtil.hget(RedisKey.WEIXIN_USER_INFO + unionId, "nickName");
            Object gender = redisUtil.hget(RedisKey.WEIXIN_USER_INFO + unionId, "gender");
            //五位数邀请码
            userModel.setInviteCode(nextInviteCode());
            userModel.setPassword(phone);
            userModel.setWeixinId(unionId);
            //腾讯1男2女
            userModel.setGender(ValidateTool.isNotNull(gender) ? (Integer.parseInt(String.valueOf(gender)) == 1 ? 1 : 0) : 0);
            userModel.setNickName(String.valueOf(nickName));
            userModel.setUserName(String.valueOf(nextUserName()));
            userModel.setCity(String.valueOf(city));
            userModel.setProvince(String.valueOf(province));
            userModel.setRegisterIp(ip);
            userModelMapper.insertSelective(userModel);
        } else {
            return userModel;
        }
        //添加上下级关系
        UserAgencyModel userAgencyModel = new UserAgencyModel();
        userAgencyModel.setNum(0);
        userAgencyModel.setId(SeqUtils.nextIdStr());
        userAgencyModel.setpUserId(userModel2.getId());
        userAgencyModel.setUserId(userModel.getId());
        userAgencyModel.setNum(0);
        userAgencyModelMapper.updateNum(userModel2.getId());
        userAgencyModelMapper.insertSelective(userAgencyModel);
        return userModel;
    }


    public String nextInviteCode() {
        //生成一个不重复的邀请码
        String code = RandomUtil.nextInviteCode(5, 5);
        log.info("生成邀请码1{}", code);
        do {
            UserModel userModel = userModelMapper.loadUser(null, null, null, code, null);
            if (ValidateTool.isNull(userModel)) {
                log.warn("生成邀请码未重复{}", code);
                break;
            } else {
                log.warn("生成邀请码重复{}", code);
                code = RandomUtil.nextInviteCode(5, 5);
                log.warn("再次生成邀请码{}", code);
            }
        } while (true);
        log.info("最终生成邀请码{}", code);
        return code;
    }


    public int nextUserName() {
        //生成一个不重复的邀请码
        int number = RandomUtil.nextNumber(1000000, 9999999);
        log.info("生成用户名{}", number);
        do {
            UserModel userModel = userModelMapper.loadUserByUserName(String.valueOf(number));
            if (ValidateTool.isNull(userModel)) {
                log.warn("生成用户名未重复{}", number);
                break;
            } else {
                log.warn("生成用户名重复{}", number);
                number = RandomUtil.nextNumber(1000000, 9999999);
                log.warn("再次生成用户名{}", number);
            }
        } while (true);
        log.info("最终生成用户名{}", number);
        return number;
    }


    @Override
    public int checkfirst(String phone) {
        UserModel userModel = userModelMapper.loadUser(null, phone, null, null, null);
        // 第一次
        int first = 0;
        if (ValidateTool.isNull(userModel)) {
            first = 1;
        }
        return first;
    }

    //添加上下级关系
//    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserAgencyModel(String inviteCode, String userId) {
        if (ValidateTool.isNull(inviteCode)) {
            throw new JeecgBootException("请填写邀请码");
        }
        UserModel userModel2 = userModelMapper.loadUser(null, null, null, inviteCode.toUpperCase(), null);
        if (!ValidateTool.checkIsNull(userModel2)) {
            throw new JeecgBootException("邀请码错误");
        }
//        UserAgencyModel agency = userAgencyModelMapper.loadUserAgency(userId, userModel2.getId());
//        if (ValidateTool.isNotNull(agency)){
//            log.warn("邀请码重复填写");
//            return;
//        }
//        //添加上下级关系
//        UserAgencyModel userAgencyModel = new UserAgencyModel();
//        userAgencyModel.setNum(0);
//        //邀请码不为空，建立上下级关系
////        if (ValidateTool.checkIsNull(inviteCode)) {
//
//            userAgencyModel.setId(SeqUtils.nextIdStr());
//            userAgencyModel.setpUserId(userModel2.getId());
//            userAgencyModel.setUserId(userId);
//            userAgencyModel.setNum(0);
//            userAgencyModelMapper.updateNum(userModel2.getId());
////        } else {
////            userAgencyModel.setId(SeqUtils.nextIdStr());
////            userAgencyModel.setpUserId("-1");
////            userAgencyModel.setUserId(userId);
////        }
//        userAgencyModelMapper.insertSelective(userAgencyModel);
    }

    @Override
    public Map loadUserInfo(String token) {
        UserModel model = getUserModelByToken(token);
        Map<Object, Object> map = new HashMap<>();
        map.put("headImage", model.getHeadImage());
        map.put("nickName", model.getNickName());
        map.put("sige", model.getSign());
        map.put("inviteCode", model.getInviteCode());
        map.put("wx", ValidateTool.isNull(model.getWeixinId()) ? 0 : 1);
        String phone = "";
        if (ValidateTool.isNotNull(model.getPhone())) {
            phone = model.getPhone().replace(model.getPhone().substring(3, 7), "****");
        }
        map.put("phone", phone);
        map.put("id", model.getId());
        return map;
    }

    @Override
    public void updateUserPhone(String phone, String captcha, UserModel userModel) {
        ValidateTool.checkParamIsMobile(phone);
        if (ValidateTool.isNull(captcha)) {
            throw new JeecgBootException("请输入验证码");
        }
        Object code = redisUtil.get(RedisKey.SMS_CODE + phone);
        if (code == null || !code.toString().equals(captcha)) {
            throw new JeecgBootException("验证码错误");
        }
        UserModel userModel1 = new UserModel();
        userModel1.setId(userModel.getId());
        userModel1.setPhone(phone);
        userModelMapper.updateByPrimaryKeySelective(userModel1);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserModel userModel, String nickName, String headImage, String content) {
        UserModel userModel1 = new UserModel();
        userModel1.setId(userModel.getId());
        if (ValidateTool.isNotNull(headImage)) {
            userModel1.setHeadImage(headImage);
        }
        if (ValidateTool.isNotNull(nickName)) {
            userModel1.setNickName(nickName);
        }
        if (ValidateTool.isNotNull(content)) {
            if (content.length()>16){
                throw new JeecgBootException("个性签名限制十六个字");
            }
            userModel1.setSign(content);
        }
        if (ValidateTool.isNull(headImage) && ValidateTool.isNull(nickName) && ValidateTool.isNull(content)) {
            throw new JeecgBootException("修改失败");
        }
        userModelMapper.updateByPrimaryKeySelective(userModel1);
    }

    @Override
    public Map loadMyWalletInfo(String userId) {

        Map map = new HashMap<>();
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null, null);

        map.put("headImage", userModel.getHeadImage());
        map.put("nickName", userModel.getNickName());

        //可提现收入
        map.put("excess", userModel.getMoney());
        //审核中 todo
        map.put("review", 0);

        //手续费 todo
        map.put("handlingFee", "0.1");

        //今日收入
        String startTimeToday = DateHelper.getToday() + DateHelper.DEFUALT_TIME_START;
        String endTimeToday = DateHelper.getToday() + DateHelper.DEFUALT_TIME_END;
        String incomeToday = userIncomeMapper.getIncomeByTime(userId, startTimeToday, endTimeToday);
        map.put("incomeToday", ValidateTool.isNull(incomeToday) ? 0 : incomeToday);

        //本月收入
        String firstDayOfMonth = DateHelper.getFirstDayOfMonth();
        String lastDayOfMonth = DateHelper.getLastDayOfMonth();
        String incomeMonth = userIncomeMapper.getIncomeByTime(userId, firstDayOfMonth, lastDayOfMonth);
        map.put("incomeMonth", ValidateTool.isNull(incomeMonth) ? 0 : incomeMonth);

        //总收入
        String incomeTotal = userIncomeMapper.getIncomeByTime(userId, firstDayOfMonth, lastDayOfMonth);

        map.put("incomeTotal", ValidateTool.isNull(incomeTotal) ? 0 : incomeTotal);

        return map;
    }


    @Override
    public Page<UserIncomeDetailVo> loadIncomeDetail(String userId, Page<UserIncomeDetailVo> page, Integer type, String startTime, String endTime) {

        List<UserIncomeDetailVo> detailVos = userIncomeDetailMapper.loadUserIncomeList(page, userId, type == 1 ? 1 : 3, startTime, endTime);

        if (Constant.TYPE_INT_2 == type && detailVos.size() > 0) {
            for (UserIncomeDetailVo incomeDetailVo : detailVos) {
                UserAgencyModel userAgencyModel = userAgencyModelMapper.loadUserAgency(incomeDetailVo.getSendId(), userId);
                incomeDetailVo.setSondType(ValidateTool.isNotNull(userAgencyModel) ? 1 : 2);
                UserModel userModel = userModelMapper.loadUser(incomeDetailVo.getSendId(), null, null, null, null);
                incomeDetailVo.setNickName(userModel.getNickName());
            }
        }
        return page.setRecords(detailVos);
    }


    @Override
    public Map<String, Object> weixinLogin(WeiXinModel weiXinModel, String phone) {

        if (ValidateTool.isNull(weiXinModel) || ValidateTool.isNull(weiXinModel.getUnionId())) {
            throw new JeecgBootException("未授权成功");
        }
//
//        String strUri = StrUtil.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code",
//                accessKeyId, secret, code);
//        // 使用 Hutool开发工具包，执行请求接口，获取相关信息
//        HttpResponse response = HttpRequest.get(strUri).execute();
//        // 转成JSON对象
//        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(response.body());
//        // 判断JSON对象中 unionid 是否存在
//
//        // 获取 unionId
//        String unionId = jsonObject.get("unionid").toString();
//        // 获取 openId
//        String openId = jsonObject.get("openid").toString();
//        // 获取 accessToken
//        String accessToken = jsonObject.get("access_token").toString();
//
//        log.warn("微信登录unionId{},,,openId{},,,,accessToken{}", unionId, openId, accessToken);
//
//        //获取用户信息
//        String strUri1 = StrUtil.format("https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}", accessToken, openId);
//
//        log.warn("微信登录strUri1{}", strUri1);
//
//        HttpResponse response1 = HttpRequest.get(strUri).execute();
//        // 转成JSON对象
//        cn.hutool.json.JSONObject jsonObject1 = JSONUtil.parseObj(response.body());
//        log.warn("微信登录jsonObject1{}", jsonObject1.toString());

        redisUtil.expire(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), 3600);
        redisUtil.hset(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), "city", weiXinModel.getCity());
        redisUtil.hset(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), "country", weiXinModel.getCountry());
        redisUtil.hset(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), "province", weiXinModel.getProvince());
        redisUtil.hset(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), "gender", weiXinModel.getGender());
        redisUtil.hset(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), "nickName", weiXinModel.getNickName());
        redisUtil.hset(RedisKey.WEIXIN_USER_INFO + weiXinModel.getUnionId(), "openId", weiXinModel.getOpenId());

        UserModel userModel = userModelMapper.loadUser(null, null, null, null, weiXinModel.getUnionId());
        if (ValidateTool.isNotNull(phone) && ValidateTool.isNotNull(userModel)) {
            throw new JeecgBootException("微信号已被绑定");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("weixin_id", weiXinModel.getUnionId());
        //是否第一次登录
        map.put("first", ValidateTool.isNull(userModel) ? 1 : 0);
//        map.put("needPhone", ValidateTool.isNull(userModel) ? 1 : 0);
        return map;
//        openid 用户的唯一标识
//        nickname 用户昵称
//        sex 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
//        province 用户个人资料填写的省份
//        city 普通用户个人资料填写的城市
//        country 国家，如中国为CN
//        headimgurl 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
//        privilege 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
//        unionid 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    }


    @Override
    public void removeById(String id) {

        userModelMapper.updateUserDelFlag(id, Constant.TYPE_INT_1);

    }


    @Override
    public void bindUserPhone(String unionId, String phone, String captcha) {
        UserModel userModel = userModelMapper.loadUser(null, phone, null, null, null);
        if (ValidateTool.isNotNull(userModel)) {
            throw new JeecgBootException("手机号已经被绑定");
        }
    }


    @Override
    public void addUserVerified(String userId, String userName, String idNum, String imageFirst, String imageBack) {
        if (ValidateTool.isNull(userName)) {
            throw new JeecgBootException("请输入姓名");
        }
        if (ValidateTool.isNull(idNum)) {
            throw new JeecgBootException("请输入身份证号码");
        }
        if (ValidateTool.isNull(imageFirst)) {
            throw new JeecgBootException("请上传身份证正面");
        }
        if (ValidateTool.isNull(imageBack)) {
            throw new JeecgBootException("请上传身份证反面");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("idNum", idNum);
        map.put("name", userName);
        map.put("appId", "");
        map.put("appKey", "");

        log.info("发送内容{}", map);

        String post = HttpUtil.post("https://api.253.com/open/idcard/id-card-auth", map);
        log.info("返回内容{}", post);
        if (ValidateTool.isNotNull(post)) {
            //返回内容{}{"chargeStatus":1,"message":"成功","data":{"orderNo":"011615528983331861","handleTime":"2021-03-12 14:03:03",
            // "result":"01","remark":"一致","province":"湖南省","city":"衡阳市","country":"衡阳县","birthday":"19981022","age":"23",
            // "gender":"1"},"code":"200000"}
            JSONObject jsonObject = JSONObject.parseObject(post);
            if (ValidateTool.isNotNull(jsonObject) && ValidateTool.isNotNull(jsonObject.get("code")) &&
                    Constant.SUCCESS_CODE.equals(jsonObject.get("code"))) {
                VerifiedLog verifiedLog = JSONObject.parseObject(String.valueOf(jsonObject.get("data")), VerifiedLog.class);
                verifiedLog.setId(SeqUtils.nextIdStr());
                verifiedLog.setImageFirst(imageFirst);
                verifiedLog.setImageBack(imageBack);
                verifiedLogMapper.insertSelective(verifiedLog);
            } else {
                log.warn("实名认证失败,返回信息{}", post);
                throw new JeecgBootException("认证失败，请核对信息");
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserVerified(UserModel user, String userName, String idNum, String image) {
        if (Constant.CHECKTYPE1.equals(user.getVerified())) {
            throw new JeecgBootException("已通过实名认证");
        }
        if (ValidateTool.isNull(userName)) {
            throw new JeecgBootException("请输入姓名");
        }
        if (ValidateTool.isNull(idNum)) {
            throw new JeecgBootException("请输入身份证号码");
        }
        if (ValidateTool.isNull(image)) {
            throw new JeecgBootException("请上传身份证人像照");
        }
        VerifiedConfig config = verifiedConfigMapper.getConfig();
        if (ValidateTool.isNull(config)) {
            throw new JeecgBootException("实名认证配置为空，请联系管理员");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("idNum", idNum);
        map.put("name", userName);
        map.put("appId", config.getAppId());
        map.put("appKey", config.getAppKey());
        map.put("image", image.substring(22));
        log.info("发送内容{}", map);
        String post = HttpUtil.post(config.getSendApi(), map);
        log.info("返回内容{}", post);
        if (ValidateTool.isNotNull(post)) {

            //实名认证失败,返回信息{"chargeStatus":1,"message":"成功","data":{"orderNo":"011615539719396565","handleTime":"2021-03-12 17:01:59","result":"02","idcardResult":"02",
            // "idcardMessage":"不一致","photoResult":"05","photoMessage":"身份校验未通过","photoScore":""},"code":"200000"}
            JSONObject jsonObject = JSONObject.parseObject(post);
            if (ValidateTool.isNotNull(jsonObject) && ValidateTool.isNotNull(jsonObject.get("code")) &&
                    Constant.SUCCESS_CODE.equals(jsonObject.get("code"))) {
                JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                //                result
//                最终返回结果，01--一致 02--不一致 03--库无 04--认证失败
//                  idcardResult
//                身份证核验结果 01--一致 02--不一致 03--库无 04--认证失败
                if (!"01".equals(data.get("result")) || !"01".equals(data.get("idcardResult"))) {
                    log.warn("实名认证失败,返回信息{}", post);
                    throw new JeecgBootException("认证失败，请核对信息");
                }
            } else {
                log.warn("实名认证失败,返回信息{},idNum{},userName{}", post, idNum, userName);
                throw new JeecgBootException("认证失败，请核对信息");
            }
        }
        userModelMapper.updateUserVerified(user.getId(), idNum, userName);
    }

    //
    //chargeStatus
    //是否收费
    //code
    //响应code码。200000：成功，其他失败。请对照万数状态码
    //message
    //响应code码解释
    //tradeNo
    //业务唯一流水号。例：628291418130630
    //data
    //result
    //最终返回结果，01--一致 02--不一致 03--库无 04--认证失败
    //idcardResult
    //身份证核验结果 01--一致 02--不一致 03--库无 04--认证失败
    //idcardMessage
    //身份证核验结果说明
    //photoResult
    //图像结果 01--判断为同一人 02--判断不是同一人 03--不能确定是否为同一人 04--认证失败 05--身份校验未通过 06--库中无照片 07--图片质量不合格,详情请联系管理员 08--库中无此号 09-无法验证
    //photoMessage
    //图像结果说明
    //photoScore
    //照片相似的分数(0-100)

}
