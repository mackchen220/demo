package org.jeecg.modules.user.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.DateHelper;
import org.jeecg.modules.commons.util.RandomUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserAgencyModelMapper;
import org.jeecg.modules.user.mapper.UserIncomeDetailMapper;
import org.jeecg.modules.user.mapper.UserIncomeMapper;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.model.UserAgencyModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
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

    @Override
    public UserModel getUserById(String id) {
        UserModel userModel = userModelMapper.loadUser(id, null, null, null);
        return userModel;
    }

    @Override
    public UserModel getUserModelByToken(String token) {
        String userId = (String)redisUtil.get(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token);
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);
        if (!ValidateTool.checkIsNull(userModel)){
            throw new JeecgBootException("登录信息过期，请重新登录");
        }
        return userModel;
    }

    @Override
    public String getUserIdByToken(String token) {
        String userId = (String)redisUtil.get(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token);
        if (!ValidateTool.checkIsNull(userId)){
            throw new JeecgBootException("登录信息过期，请重新登录");
        }
        return userId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject userLogin(String inviteCode, String captcha, String phone) {
        UserModel userModel = userModelMapper.loadUser(null, phone, null, null);
         // 第一次
        int first =0;
        if (!ValidateTool.checkIsNull(userModel)) {
            first=1;
            userModel = new UserModel();
            //手机号未注册的新用户自动注册
            userModel.setId(SeqUtils.nextIdStr());
            userModel.setPhone(phone);
            //五位数邀请码
            //todo
            userModel.setInviteCode(nextInviteCode());
            userModel.setUserName(phone);
            userModel.setPassword(phone);
            userModel.setNickName(phone);
            userModelMapper.insertSelective(userModel);
        }else {
            UserAgencyModel userAgencyModel = userAgencyModelMapper.loadUserAgency(userModel.getId(), null);
            if (ValidateTool.isNull(userAgencyModel)){
                throw new JeecgBootException(ErrorInfoCode.NO_INVITE_CODE_ERROR.getCode(),"请填写邀请码");
            }
        }
        JSONObject object = new JSONObject();
        // 生成前台token
        String token;
        String encrypt = MD5Util.MD5Encode(userModel.getId(), "utf-8");

        String timeMillis =MD5Util.MD5Encode(String.valueOf(System.currentTimeMillis()), "utf-8");
        // 设置token缓存有效时间
        redisUtil.set(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + encrypt, timeMillis);
        redisUtil.expire(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + encrypt, JwtUtil.EXPIRE_TIME * 336 );
//        redisUtil.hset(RedisKey.USER_LOGIN_TOKEN +RedisKey.KEY_SPLIT+token, userModel.getId(), JSONObject.toJSONString(userModel));
        token = encrypt + "," + timeMillis;
        redisUtil.set(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token, userModel.getId());
        redisUtil.expire(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + token, JwtUtil.EXPIRE_TIME * 336);
        object.put("token", token);
        object.put("headImage", userModel.getHeadImage());
        object.put("nickname", userModel.getNickName());
        object.put("first", first);
        return object;
    }

    public String nextInviteCode(){
        //生成一个不重复的邀请码
        String code = RandomUtil.nextInviteCode(5, 5);
        log.info("生成邀请码1{}",code);
        do {
            UserModel userModel = userModelMapper.loadUser(null, null, null, code);
            if (ValidateTool.isNull(userModel)){
                log.warn("生成邀请码未重复{}",code);
                break;
            }else {
                log.warn("生成邀请码重复{}",code);
                code = RandomUtil.nextInviteCode(5, 5);
                log.warn("再次生成邀请码{}",code);
            }
        }while (true);
        log.info("最终生成邀请码{}",code);
        return code;
    }

    @Override
    public int checkfirst(String phone) {
        UserModel userModel = userModelMapper.loadUser(null, phone, null, null);
        // 第一次
        int first =0;
        if (ValidateTool.isNull(userModel)) {
            first=1;
        }
        return first;
    }

    //添加上下级关系
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserAgencyModel(String inviteCode, String userId) {
        if (ValidateTool.isNull(inviteCode)){
            throw  new JeecgBootException("请填写邀请码");
        }
        UserModel userModel2 = userModelMapper.loadUser(null, null, null, inviteCode.toUpperCase());
        if (!ValidateTool.checkIsNull(userModel2)) {
            throw new JeecgBootException("邀请码不存在");
        }
        UserAgencyModel agency = userAgencyModelMapper.loadUserAgency(userId, userModel2.getId());
        if (ValidateTool.isNotNull(agency)){
            log.warn("邀请码重复填写");
            return;
        }
        //添加上下级关系
        UserAgencyModel userAgencyModel = new UserAgencyModel();
        userAgencyModel.setNum(0);
        //邀请码不为空，建立上下级关系
//        if (ValidateTool.checkIsNull(inviteCode)) {

            userAgencyModel.setId(SeqUtils.nextIdStr());
            userAgencyModel.setpUserId(userModel2.getId());
            userAgencyModel.setUserId(userId);
            userAgencyModel.setNum(0);
            userAgencyModelMapper.updateNum(userModel2.getId());
//        } else {
//            userAgencyModel.setId(SeqUtils.nextIdStr());
//            userAgencyModel.setpUserId("-1");
//            userAgencyModel.setUserId(userId);
//        }
        userAgencyModelMapper.insertSelective(userAgencyModel);
    }

    @Override
    public Map loadUserInfo(String token) {
        UserModel model = getUserModelByToken(token);
        Map<Object, Object> map = new HashMap<>();
        map.put("headImage",model.getHeadImage());
        map.put("nickName",model.getNickName());
        map.put("sige",model.getSign());

        String phone="";
        if (ValidateTool.isNotNull(model.getPhone())){
            phone=model.getPhone().replace(model.getPhone().substring(3,7),"****");
        }
        map.put("phone",phone);
        map.put("id",model.getId());
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserModel userModel, String nickName, String headImage, String content) {
        UserModel userModel1 = new UserModel();
        userModel1.setId(userModel.getId());
        if (ValidateTool.isNotNull(headImage)){
            userModel1.setHeadImage(headImage);
        }
        if (ValidateTool.isNotNull(nickName)){
            userModel1.setHeadImage(headImage);
        }
        if (ValidateTool.isNotNull(content)){
            userModel1.setSign(content);
        }
        if (ValidateTool.isNull(content)&&ValidateTool.isNull(content)&&ValidateTool.isNull(content)){
            throw new JeecgBootException("修改失败");
        }
        userModelMapper.updateByPrimaryKeySelective(userModel1);
    }

    @Override
    public Map loadMyWalletInfo(String userId) {

        Map map =new HashMap<>();
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);

        map.put("headImage",userModel.getHeadImage());
        map.put("nickName",userModel.getNickName());

        //可提现收入
        map.put("excess",userModel.getMoney());
        //审核中 todo
        map.put("Review",0);

        //手续费 todo
        map.put("excess","0.1");

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

        return page.setRecords(detailVos);
    }


    @Override
    public void weixinLogin(String code) {

        if (ValidateTool.isNull(code)) {
            throw new JeecgBootException("未授权成功");
        }

        String strUri = StrUtil.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code",
                accessKeyId, secret, code);
        // 使用 Hutool开发工具包，执行请求接口，获取相关信息
        HttpResponse response = HttpRequest.get(strUri).execute();
        // 转成JSON对象
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(response.body());
        // 判断JSON对象中 unionid 是否存在

        // 获取 unionId
        String unionId = jsonObject.get("unionid").toString();
        // 获取 openId
        String openId = jsonObject.get("openid").toString();
        // 获取 accessToken
        String accessToken = jsonObject.get("access_token").toString();

        //获取用户信息
        String strUri1 = StrUtil.format("https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}", accessToken, openId);
        HttpResponse response1 = HttpRequest.get(strUri).execute();
        // 转成JSON对象
        cn.hutool.json.JSONObject jsonObject1 = JSONUtil.parseObj(response.body());

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
}
