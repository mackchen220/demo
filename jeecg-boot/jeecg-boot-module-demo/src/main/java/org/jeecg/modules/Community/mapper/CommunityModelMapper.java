package org.jeecg.modules.Community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.Community.model.CommunityModel;
import org.jeecg.modules.base.mapper.BaseCommonMapper;

import java.util.List;

public interface CommunityModelMapper{
    int deleteByPrimaryKey(String id);

    int insert(CommunityModel record);

    int insertSelective(CommunityModel record);

    CommunityModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommunityModel record);

    int updateByPrimaryKey(CommunityModel record);

    List<CommunityModel> loadCommunityListBype(Page<CommunityModel> page, int type);
}
