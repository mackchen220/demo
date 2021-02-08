package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.HospitalProject;
import org.jeecg.modules.user.model.vo.ProjectInfoVo;

import java.util.List;

@Mapper
public interface HospitalProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(HospitalProject record);

    int insertSelective(HospitalProject record);

    HospitalProject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalProject record);

    int updateByPrimaryKey(HospitalProject record);

    List<ProjectInfoVo> loadProjectByHospitalId(@Param("hospitalId") String hospitalId);
}
