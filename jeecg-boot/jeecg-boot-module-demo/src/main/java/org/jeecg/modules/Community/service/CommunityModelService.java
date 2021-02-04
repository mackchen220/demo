package org.jeecg.modules.Community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.Community.model.CommunityModel;

import java.util.List;

public interface CommunityModelService{


    int deleteByPrimaryKey(String id);

    int insert(CommunityModel record);

    int insertSelective(CommunityModel record);

    CommunityModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommunityModel record);

    int updateByPrimaryKey(CommunityModel record);

    Page<CommunityModel> loadCommunityListBype(Page<CommunityModel> page ,int type);
}
