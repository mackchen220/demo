package org.jeecg.modules.Community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.Community.model.CommunityModel;
import org.jeecg.modules.user.model.UserModel;

import java.util.List;
import java.util.Map;

public interface CommunityModelService{


    int deleteByPrimaryKey(String id);

    int insert(CommunityModel record);

    int insertSelective(CommunityModel record);

    CommunityModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommunityModel record);

    int updateByPrimaryKey(CommunityModel record);

    Page<CommunityModel> loadCommunityListByType(Page<CommunityModel> page ,int type);

    Map loadMomentsInfo(String id);
}
