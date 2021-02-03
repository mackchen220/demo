package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;

import org.jeecg.common.util.encryption.AesEncryptUtil;
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

@Log4j2
@Service
public class UserModelServiceImpl implements UserModelService{


    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private UserAgencyModelMapper userAgencyModelMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserModel getUserById(String id) {

        UserModel userModel = userModelMapper.loadUser("12",null,null,null);
        return userModel;
    }

    @Override
    public UserModel getUserModelByToken(String token) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject userLogin(String inviteCode, String captcha, String phone, String s) {
        UserModel userModel = userModelMapper.loadUser(null,phone,null,null);

        if (!ValidateTool.checkIsNull(userModel)){
            userModel = new UserModel();
            //手机号未注册的新用户自动注册
            userModel.setId(SeqUtils.nextIdStr());
            userModel.setPhone(phone);
            userModel.setInviteCode(RandomUtil.nextInviteCode(1,6));
            userModel.setUserName(phone);
            userModel.setPassword(phone);
            userModel.setNikeName(phone);
            userModelMapper.insertSelective(userModel);
            //添加上下级关系
            UserAgencyModel userAgencyModel = new UserAgencyModel();
            userAgencyModel.setNum(0);
            //邀请码不为空，建立上下级关系
            if (ValidateTool.checkIsNull(inviteCode)){
                UserModel userModel2 = userModelMapper.loadUser(null,null,null,inviteCode.toUpperCase());
                if (!ValidateTool.checkIsNull(userModel2)){
                    throw new JeecgBootException("邀请码不存在");
                }
                userAgencyModel.setId(SeqUtils.nextIdStr());
                userAgencyModel.setpUserId(userModel2.getId());
                userAgencyModel.setUserId(userModel.getId());
                userAgencyModel.setNum(0);
                userAgencyModelMapper.updateNum(userModel2.getId());
            }else {
                userAgencyModel.setId(SeqUtils.nextIdStr());
                userAgencyModel.setpUserId("-1");
                userAgencyModel.setUserId(userModel.getId());
            }
            userAgencyModelMapper.insertSelective(userAgencyModel);
        }

        JSONObject object = new JSONObject();
        // 生成前台token
        String token;
        String encrypt;
        try {
            encrypt = AesEncryptUtil.desEncrypt(userModel.getId());
        } catch (Exception e) {
            log.error("登录加密token错误", e, e.getMessage());
            throw new JeecgBootException("网络异常，请稍后重试");
        }
        long timeMillis = System.currentTimeMillis();
        token=encrypt+"."+String.valueOf(timeMillis);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
        object.put("token",token);
        object.put("headImage",userModel.getHeadImage());
        object.put("nickname",userModel.getNikeName());
        return object;
    }


}
