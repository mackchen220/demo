package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.HospitalProjectMapper;
import org.jeecg.modules.user.model.HospitalProject;

@Service
public class HospitalProjectServiceImpl implements HospitalProjectService{

    @Resource
    private HospitalProjectMapper hospitalProjectMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return hospitalProjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HospitalProject record) {
        return hospitalProjectMapper.insert(record);
    }

    @Override
    public int insertSelective(HospitalProject record) {
        return hospitalProjectMapper.insertSelective(record);
    }

    @Override
    public HospitalProject selectByPrimaryKey(String id) {
        return hospitalProjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(HospitalProject record) {
        return hospitalProjectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(HospitalProject record) {
        return hospitalProjectMapper.updateByPrimaryKey(record);
    }

}
