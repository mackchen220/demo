package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;

import java.util.Map;

public interface UserModelService {

    UserModel getUserById(String id);

    JSONObject userLogin(String inviteCode, String captcha, String phone);

    UserModel getUserModelByToken(String token);

    String getUserIdByToken(String token);

    void updateUserInfo(UserModel userModel, String nickName, String headImage, String content);

    Map loadUserInfo(String token);

    void addUserAgencyModel(String inviteCode,String userId);

    int checkfirst(String phone);

    Map loadMyWalletInfo(String userId);

    Page<UserIncomeDetailVo> loadIncomeDetail(String userId, Page<UserIncomeDetailVo> page, Integer type, String startTime, String endTime);


    void weixinLogin(String code);

    void removeById(String id);


}
