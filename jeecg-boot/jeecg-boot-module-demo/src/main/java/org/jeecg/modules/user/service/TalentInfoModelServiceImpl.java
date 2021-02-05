package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.TalentInfoModelMapper;
import org.jeecg.modules.user.model.TalentInfoModel;

import java.util.List;

@Service
public class TalentInfoModelServiceImpl implements TalentInfoModelService{

    @Resource
    private TalentInfoModelMapper talentInfoModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return talentInfoModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TalentInfoModel record) {
        return talentInfoModelMapper.insert(record);
    }

    @Override
    public int insertSelective(TalentInfoModel record) {
        return talentInfoModelMapper.insertSelective(record);
    }

    @Override
    public TalentInfoModel selectByPrimaryKey(String id) {
        return talentInfoModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TalentInfoModel record) {
        return talentInfoModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TalentInfoModel record) {
        return talentInfoModelMapper.updateByPrimaryKey(record);
    }

//    @Override
//    public List loadIndexTalentList(String limit) {
//        return talentInfoModelMapper.loadIndexTalentList(limit);
//    }
}
