package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.RandomUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserAgencyModelMapper;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.model.UserAgencyModel;
import org.jeecg.modules.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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
    public JSONObject userLogin(String inviteCode, String captcha, String phone, String s) {
        UserModel userModel = userModelMapper.loadUser(null, phone, null, null);

        if (!ValidateTool.checkIsNull(userModel)) {
            userModel = new UserModel();
            //手机号未注册的新用户自动注册
            userModel.setId(SeqUtils.nextIdStr());
            userModel.setPhone(phone);
            userModel.setInviteCode(RandomUtil.nextInviteCode(1, 6));
            userModel.setUserName(phone);
            userModel.setPassword(phone);
            userModel.setNickName(phone);
            userModelMapper.insertSelective(userModel);
            //添加上下级关系
            UserAgencyModel userAgencyModel = new UserAgencyModel();
            userAgencyModel.setNum(0);
            //邀请码不为空，建立上下级关系
            if (ValidateTool.checkIsNull(inviteCode)) {
                UserModel userModel2 = userModelMapper.loadUser(null, null, null, inviteCode.toUpperCase());
                if (!ValidateTool.checkIsNull(userModel2)) {
                    throw new JeecgBootException("邀请码不存在");
                }
                userAgencyModel.setId(SeqUtils.nextIdStr());
                userAgencyModel.setpUserId(userModel2.getId());
                userAgencyModel.setUserId(userModel.getId());
                userAgencyModel.setNum(0);
                userAgencyModelMapper.updateNum(userModel2.getId());
            } else {
                userAgencyModel.setId(SeqUtils.nextIdStr());
                userAgencyModel.setpUserId("-1");
                userAgencyModel.setUserId(userModel.getId());
            }
            userAgencyModelMapper.insertSelective(userAgencyModel);
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
        return object;
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


}
