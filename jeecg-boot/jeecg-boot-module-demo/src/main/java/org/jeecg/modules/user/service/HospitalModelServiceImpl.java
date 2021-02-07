package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.sl.draw.geom.TanExpression;
import org.jeecg.modules.user.mapper.TalentHospitalMapper;
import org.jeecg.modules.user.mapper.TalentInfoModelMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.HospitalModelMapper;
import org.jeecg.modules.user.model.HospitalModel;

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
    public Map loadHospitallist() {
        List<HospitalModel> hospitalModels = hospitalModelMapper.loadHospitallist();
        for (HospitalModel hospitalModel : hospitalModels) {
//            talentHospitalMapper.()
        }
        return null;
    }
}
