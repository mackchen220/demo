package org.jeecg.modules.index.service;

public interface CaptchaCodeService {

    String getCaptchaCode(int num);

    String getPhoneCaptchaCode (String phone,String ip);
}
