package org.jeecg.modules.index.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.index.model.HotSearchModel;
import org.jeecg.modules.index.model.vo.HotSearchVo;

import java.util.List;

@Mapper
public interface HotSearchModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotSearchModel record);

    int insertSelective(HotSearchModel record);

    HotSearchModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotSearchModel record);

    int updateByPrimaryKey(HotSearchModel record);

    List<HotSearchVo> loadHotSearchList(@Param("type") String type);


}
