package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.TalentHospital;

import java.util.List;
import java.util.Map;

public interface TalentHospitalService {




    int insertSelective(TalentHospital record);

    TalentHospital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentHospital record);


    List loadHospitalistByUserId(String id);


    Map  loadProjectList(String id,String projectId,String hospitalId);

    Map getQuotation(String projectId, String materialId, String hospitalId);


}
