package org.jeecg.modules.index.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.index.mapper.SmsConfigMapper;
import org.jeecg.modules.index.model.SmsConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class CaptchaCodeServiceImpl implements CaptchaCodeService {

    @Resource
    private SmsConfigMapper smsConfigMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public String getCaptchaCode(int num) {
        String numbers = RandomUtil.randomNumbers(num);

        return numbers;
    }


    @Override
    public String getPhoneCaptchaCode(String phone, String ip) {

        ValidateTool.checkParamIsMobile(phone);
        SmsConfig smsConfig = null;
        Object config = redisUtil.get(RedisKey.SMS_CONFIG);
        if (ValidateTool.isNotNull(config)) {
            smsConfig = JSONObject.parseObject(String.valueOf(config), SmsConfig.class);
        } else {
            smsConfig = smsConfigMapper.getSmsConfig();
            if (ValidateTool.isNull(smsConfig)) {
                throw new JeecgBootException("短信发送失败，请联系客服");
            } else {
                redisUtil.set(RedisKey.SMS_CONFIG, JSONObject.toJSONString(smsConfig));
            }
        }
        log.info("短信配置{}", JSONObject.toJSONString(smsConfig));

        checkTimes(phone, ip);
//        您好，您的验证码是：XX，请勿告诉他人，以防泄露！
        String numbers = RandomUtil.randomNumbers(6);
        String content = smsConfig.getSendContent().replace("XX", numbers);
        Map<String, Object> map = new HashMap<>();
        map.put("LoginName", smsConfig.getLoginName());
        map.put("Pwd", smsConfig.getPassword());
        map.put("FeeType", smsConfig.getFeeType());
        map.put("Mobile", phone);
        map.put("Content", content);
        log.info("短信发送内容{}", map);

        String post = HttpUtil.post(smsConfig.getSendApi(), map);
        log.info("短信发送返回内容{}", post);
        if (!post.contains("OK")) {
            log.error("短信发送失败返回内容{}", post);
        }
        //过期时间五分钟
        redisUtil.set(RedisKey.SMS_CODE + phone, numbers, 300);
        long expire1 = redisUtil.getExpire(RedisKey.SMS_SEND_TIMES + phone);
        long expire2 = redisUtil.getExpire(RedisKey.SMS_SEND_TIMES + ip);
        redisUtil.incr(RedisKey.SMS_SEND_TIMES + phone, 1);
        redisUtil.incr(RedisKey.SMS_SEND_ip_TIMES + ip, 1);
        //设置五分钟过期时间
        redisUtil.expire(RedisKey.SMS_SEND_TIMES + phone, expire1 > 0 ? expire1 : 300);
        redisUtil.expire(RedisKey.SMS_SEND_ip_TIMES + ip, expire2 > 0 ? expire2 : 3600);

        return null;
    }


    public void checkTimes(String phone, String ip) {
        Object times = redisUtil.get(RedisKey.SMS_SEND_TIMES + phone);
        Object ipTimes = redisUtil.get(RedisKey.SMS_SEND_ip_TIMES + ip);
        log.warn("ip=={}发送次数{} phone==={}号码发送次数{}",ip, ipTimes, phone,times);

        if (ValidateTool.isNotNull(times) && Long.valueOf(String.valueOf(times)) >= 5) {
            log.warn("同一手机号短信发送频繁 ip{} phone{}", ip, phone);
            throw new JeecgBootException("短信发送频繁，请联系客服");
        }
        if (ValidateTool.isNotNull(ipTimes) && Long.valueOf(String.valueOf(times)) >= 10) {
            log.warn("同一ip短信发送频繁 ip{} phone{}", ip, phone);
            throw new JeecgBootException("短信发送频繁，请联系客服");
        }
    }
}
