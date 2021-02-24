package org.jeecg.modules.index.service;

import org.jeecg.modules.index.model.vo.HotSearchVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.index.model.HotSearchModel;
import org.jeecg.modules.index.mapper.HotSearchModelMapper;

import java.util.List;

@Service
public class HotSearchModelServiceImpl implements HotSearchModelService{

    @Resource
    private HotSearchModelMapper hotSearchModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return hotSearchModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HotSearchModel record) {
        return hotSearchModelMapper.insert(record);
    }

    @Override
    public int insertSelective(HotSearchModel record) {
        return hotSearchModelMapper.insertSelective(record);
    }

    @Override
    public HotSearchModel selectByPrimaryKey(String id) {
        return hotSearchModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(HotSearchModel record) {
        return hotSearchModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HotSearchModel record) {
        return hotSearchModelMapper.updateByPrimaryKey(record);
    }


    @Override
    public List<HotSearchVo> loadHotSearchList(String type) {

        return hotSearchModelMapper.loadHotSearchList(type);
    }
}
