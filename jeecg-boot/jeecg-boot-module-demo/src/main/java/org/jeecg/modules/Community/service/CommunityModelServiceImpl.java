package org.jeecg.modules.Community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.Community.model.CommunityModel;
import org.jeecg.modules.Community.mapper.CommunityModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityModelServiceImpl implements CommunityModelService{

    @Resource
    private CommunityModelMapper communityModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return communityModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CommunityModel record) {
        return communityModelMapper.insert(record);
    }

    @Transactional
    @Override
    public int insertSelective(CommunityModel record) {
        record.setId(SeqUtils.nextIdStr());

        return communityModelMapper.insertSelective(record);
    }

    @Override
    public CommunityModel selectByPrimaryKey(String id) {
        return communityModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CommunityModel record) {
        return communityModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommunityModel record) {
        return communityModelMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<CommunityModel> loadCommunityListBype(Page<CommunityModel> page,int type) {
        return page.setRecords(communityModelMapper.loadCommunityListBype(page,type));
    }
}
