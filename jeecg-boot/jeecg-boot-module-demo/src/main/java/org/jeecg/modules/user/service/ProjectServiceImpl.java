package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.ProjectMapper;
import org.jeecg.modules.user.model.Project;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Resource
    private ProjectMapper projectMapper;



    @Override
    public int insertSelective(Project record) {
        return projectMapper.insertSelective(record);
    }

    @Override
    public Project selectByPrimaryKey(String id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Project record) {
        return projectMapper.updateByPrimaryKeySelective(record);
    }


}
