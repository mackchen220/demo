package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.HospitalModel;

import java.util.List;

public interface HospitalModelService{


    int deleteByPrimaryKey(String id);

    int insert(HospitalModel record);

    int insertSelective(HospitalModel record);

    HospitalModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalModel record);

    int updateByPrimaryKey(HospitalModel record);


}
