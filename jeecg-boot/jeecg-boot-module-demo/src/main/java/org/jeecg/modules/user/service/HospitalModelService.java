package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.HospitalModel;

import java.util.List;
import java.util.Map;

public interface HospitalModelService{

    int insertSelective(HospitalModel record);

    HospitalModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalModel record);



    Map loadHospitallist();


}
