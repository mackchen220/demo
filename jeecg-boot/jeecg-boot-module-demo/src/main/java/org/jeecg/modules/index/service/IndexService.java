package org.jeecg.modules.index.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserModel;

import java.util.Map;

public interface IndexService {

    Map loadIndexlist();


    Map loadAppVersion(String versionNum);

    Map addInviteImage(UserModel user);



}
