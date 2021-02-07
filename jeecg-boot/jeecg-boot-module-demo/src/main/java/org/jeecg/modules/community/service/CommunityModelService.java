package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.community.model.CommunityModel;

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
