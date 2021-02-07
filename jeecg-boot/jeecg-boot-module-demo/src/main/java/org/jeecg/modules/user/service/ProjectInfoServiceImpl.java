package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.model.ProjectInfo;
import org.jeecg.modules.user.mapper.ProjectInfoMapper;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService{

    @Resource
    private ProjectInfoMapper projectInfoMapper;





    @Override
    public int insertSelective(ProjectInfo record) {
        return projectInfoMapper.insertSelective(record);
    }

    @Override
    public ProjectInfo selectByPrimaryKey(String id) {
        return projectInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProjectInfo record) {
        return projectInfoMapper.updateByPrimaryKeySelective(record);
    }

}
