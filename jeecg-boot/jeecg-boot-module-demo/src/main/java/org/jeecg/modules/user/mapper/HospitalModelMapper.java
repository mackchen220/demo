package org.jeecg.modules.user.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.HospitalModel;

import java.util.List;

@Mapper
public interface HospitalModelMapper {


    int insertSelective(HospitalModel record);

    HospitalModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HospitalModel record);


    List<HospitalModel> loadHospitallist(Page<HospitalModel> page,@Param("search") String search);

//    List<HospitalModel> loadOtherHospitlist(Page<HospitalModel> page,@Param("limit") String limit);




}
