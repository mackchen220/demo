package org.jeecg.modules.index.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.index.model.TurnImageModel;

import java.util.List;

public interface TurnImageModelMapper {


    int insertSelective(TurnImageModel record);

    TurnImageModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TurnImageModel record);

    List<TurnImageModel> loadTurnImageList(@Param("nowTime")String nowTime);

}
