package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.HospitalModel;

@Mapper
public interface HospitalModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(HospitalModel record);

    int insertSelective(HospitalModel record);

    HospitalModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalModel record);

    int updateByPrimaryKey(HospitalModel record);

}
