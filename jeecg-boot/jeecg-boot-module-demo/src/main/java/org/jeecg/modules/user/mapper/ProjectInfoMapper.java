package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.ProjectInfo;
import org.jeecg.modules.user.model.vo.ProjectInfoVo;

import java.util.List;

@Mapper
public interface ProjectInfoMapper {


    int insertSelective(ProjectInfo record);

    ProjectInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectInfo record);


    List<ProjectInfoVo> loadProjectInfoList(@Param("projectId") String projectId, @Param("hospitalId") String hospitalId);

    List<ProjectInfoVo> loadProjectPrice(@Param("hospitalId") String hospitalId, @Param("ids") String[] ids);


}
