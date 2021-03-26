package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;

import java.util.Map;

public interface CommunityModelService {

    int insertSelective(CommunityModel record);

    CommunityModel selectByPrimaryKey(String id);

    Page<CommunityModelVo> loadCommunityListByType(Page<CommunityModelVo> page, int type, String userId);

    Map loadMomentsInfo(String id,String userId);

    IPage<CommunityModel> getListByUserId(Page<CommunityModel> objectPage, String userId);

    void addCommunityStar(String id, String userId, String type,String pageType);

    Page<CommunityModelVo> loadGoodCommunityList(Page<CommunityModelVo> page, String userId, int type);

    Page<CommunityModelVo> loadCommunityBySearch(Page<CommunityModelVo> page, String search, Integer type, Integer sortModel, String userId);

}
