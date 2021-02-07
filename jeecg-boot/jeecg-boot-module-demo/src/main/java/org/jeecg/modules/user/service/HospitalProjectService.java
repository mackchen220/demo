package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.HospitalProject;
public interface HospitalProjectService{


    int deleteByPrimaryKey(String id);

    int insert(HospitalProject record);

    int insertSelective(HospitalProject record);

    HospitalProject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalProject record);

    int updateByPrimaryKey(HospitalProject record);

}
