package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.WeiXinModel;
import org.jeecg.modules.user.model.vo.ExtensionVo;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserModelService {

    UserModel getUserById(String id);

    JSONObject userLogin(String inviteCode, String captcha, String phone, String unionId, String ip);

    UserModel getUserModelByToken(String token);

    String getUserIdByToken(String token);

    void updateUserInfo(UserModel userModel, String nickName, String headImage, String content, String wechat);

    void updateUserPhone(String phone, String captcha, UserModel userModel);

    Map loadUserInfo(String token);

    void addUserAgencyModel(String inviteCode,String userId);

    int checkfirst(String phone);

    Map loadMyWalletInfo(String userId);

    Page<UserIncomeDetailVo> loadIncomeDetail(String userId, Page<UserIncomeDetailVo> page, Integer type, String startTime, String endTime);


    Map<String, Object> weixinLogin(WeiXinModel weiXinModel, String phone);

    void removeById(String id);


    void bindUserPhone(String unionId, String phone, String captcha);

    void addUserVerified(String userId, String userName, String idNum, String imageFirst, String imageBack);

    void addUserVerified(UserModel user, String userName, String idNum, String image);

    Map<String, Object> loadProxyCenter(UserModel user);

    Page<ExtensionVo> loadProxyIncome(String userId, Page<ExtensionVo> page);

}
