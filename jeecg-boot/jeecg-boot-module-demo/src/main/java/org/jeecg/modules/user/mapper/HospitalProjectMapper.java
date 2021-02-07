package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.HospitalProject;

@Mapper
public interface HospitalProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(HospitalProject record);

    int insertSelective(HospitalProject record);

    HospitalProject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalProject record);

    int updateByPrimaryKey(HospitalProject record);
}