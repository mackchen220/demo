package org.jeecg.modules.user.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.UserFocusModel;
public interface UserFocusModelService{


    Result addUserFocus(String id, String userId);

    Result delUserFocus(String id, String userId);

    int updateByPrimaryKeySelective(UserFocusModel record);

    int updateByPrimaryKey(UserFocusModel record);

    int getFansNum(String userId);

    boolean isFans(String userId, String mineId);

}
