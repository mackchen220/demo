package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.user.model.HospitalModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;

import java.util.List;
import java.util.Map;

public interface HospitalModelService{

    int insertSelective(HospitalModel record);

    HospitalModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalModel record);



    Page<HospitalModel> loadAllHospitlist(Page<HospitalModel> page,String search);

//    Page<HospitalModel> loadOtherHospitlist(Page<HospitalModel> page);


    Page<HospitalModel> loadMyHospitList(Page<HospitalModel> page,String talentId);


    Map getHospitalInfo(String hospitalId);


    void updateTalentHospit(String hospitalId,String talentId);


    void addHospitalInfo(String userId,HospitalModel hospitalModel);
}
