package org.jeecg.modules.index.service;

import org.jeecg.modules.index.model.HotSearchModel;
import org.jeecg.modules.index.model.vo.HotSearchVo;

import java.util.List;

public interface HotSearchModelService{


    int deleteByPrimaryKey(String id);

    int insert(HotSearchModel record);

    int insertSelective(HotSearchModel record);

    HotSearchModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotSearchModel record);

    int updateByPrimaryKey(HotSearchModel record);

    List<HotSearchVo> loadHotSearchList(String type);

}
