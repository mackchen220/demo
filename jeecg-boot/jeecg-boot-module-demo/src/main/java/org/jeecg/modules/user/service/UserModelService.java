package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserModel;

import java.util.Map;

public interface UserModelService {

    UserModel getUserById(String id);

    JSONObject userLogin(String inviteCode, String captcha, String phone, String s);

    UserModel getUserModelByToken(String token);

    String getUserIdByToken(String token);

    void updateUserInfo(UserModel userModel, String nickName, String headImage, String content);

    Map loadUserInfo(String token);

}
