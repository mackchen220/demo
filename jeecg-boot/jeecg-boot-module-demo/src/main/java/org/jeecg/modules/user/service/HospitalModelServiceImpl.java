package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.user.mapper.HospitalModelMapper;
import org.jeecg.modules.user.mapper.TalentHospitalMapper;
import org.jeecg.modules.user.model.HospitalModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;
import org.jeecg.modules.user.model.vo.UserModelVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HospitalModelServiceImpl implements HospitalModelService{

    @Resource
    private HospitalModelMapper hospitalModelMapper;
    @Resource
    private TalentHospitalMapper talentHospitalMapper;



    @Override
    public int insertSelective(HospitalModel record) {
        return hospitalModelMapper.insertSelective(record);
    }

    @Override
    public HospitalModel selectByPrimaryKey(String id) {
        return hospitalModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(HospitalModel record) {
        return hospitalModelMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public Page<HospitalModel> loadAllHospitlist(Page<HospitalModel> page,String search)  {
        List<HospitalModel> hospitalModels = hospitalModelMapper.loadHospitallist(page,search);
        List list = new ArrayList<>();
        for (HospitalModel hospitalModel : hospitalModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hospitalId",hospitalModel.getId());
            jsonObject.put("hospitalImage",hospitalModel.getImageUrl());
            jsonObject.put("name",hospitalModel.getName());
            jsonObject.put("content",hospitalModel.getContent());
            List<UserModelVo> userModelVos = talentHospitalMapper.loadAllTalent(hospitalModel.getId());
            jsonObject.put("talents",userModelVos);
            list.add(jsonObject);
        }
        return page.setRecords(list);
    }

//    @Override
//    public Page<HospitalModel> loadOtherHospitlist(Page<HospitalModel> page) {
//        List<HospitalModel> hospitalModels = hospitalModelMapper.l(page,"4");
//        List list = new ArrayList<>();
//        for (HospitalModel hospitalModel : hospitalModels) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("hospitalId",hospitalModel.getId());
//            jsonObject.put("hospitalImage",hospitalModel.getImageUrl());
//            jsonObject.put("name",hospitalModel.getName());
//            jsonObject.put("content",hospitalModel.getContent());
//            List<UserModelVo> userModelVos = talentHospitalMapper.loadAllTalent(hospitalModel.getId());
//            jsonObject.put("talents",userModelVos);
//            list.add(jsonObject);
//        }
//        return page.setRecords(list);
//    }
}
