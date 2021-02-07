package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.ProjectInfo;
public interface ProjectInfoService{




    int insertSelective(ProjectInfo record);

    ProjectInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectInfo record);


}
