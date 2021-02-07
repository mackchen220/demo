package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.Project;
import org.jeecg.modules.user.model.vo.ProjectVo;

import java.util.List;

@Mapper
public interface ProjectMapper {


    int insertSelective(Project record);

    Project selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);

    List<ProjectVo> laodProjectList(@Param("id") String id);

    String getId();

}
