package org.jeecg.modules.index.service;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;

@Service
public class CaptchaCodeServiceImpl implements CaptchaCodeService{

    @Override
    public String getCaptchaCode(int num) {
        String numbers = RandomUtil.randomNumbers(num);

        return numbers;
    }


    @Override
    public String getPhoneCaptchaCode(String phone) {

        return null;
    }
}
