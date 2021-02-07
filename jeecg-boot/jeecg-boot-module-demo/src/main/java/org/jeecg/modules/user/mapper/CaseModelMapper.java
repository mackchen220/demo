package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.CaseModel;

@Mapper
public interface CaseModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseModel record);

    int insertSelective(CaseModel record);

    CaseModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseModel record);

    int updateByPrimaryKey(CaseModel record);
}