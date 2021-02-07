package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.HospitalModel;
import org.jeecg.modules.user.model.TalentHospital;
import org.jeecg.modules.user.model.vo.HospitalVo;
import org.jeecg.modules.user.model.vo.UserModelVo;

import java.util.List;

@Mapper
public interface TalentHospitalMapper {


    int insertSelective(TalentHospital record);

    TalentHospital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentHospital record);


    List<HospitalVo> loadTalentHospitalLst(@Param("id") String userId);

    List<UserModelVo> loadAllTalent(@Param("hospitalId") String hospitalId);
}
