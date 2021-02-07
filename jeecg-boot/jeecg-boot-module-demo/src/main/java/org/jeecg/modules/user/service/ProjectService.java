package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.Project;
public interface ProjectService{





    int insertSelective(Project record);

    Project selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);


}
