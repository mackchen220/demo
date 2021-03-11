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
    public String getPhoneCaptchaCode(String phone) {

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

        return null;
    }
}
