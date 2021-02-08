package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.HospitalModel;
import org.jeecg.modules.user.model.TalentHospital;
import org.jeecg.modules.user.model.vo.HospitalVo;
import org.jeecg.modules.user.model.vo.UserModelVo;
import org.jeecg.modules.user.model.vo.UserProjectVo;

import java.util.List;

@Mapper
public interface TalentHospitalMapper {


    int insertSelective(TalentHospital record);

    TalentHospital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentHospital record);


    List<HospitalVo> loadTalentHospitalLst(@Param("id") String userId);

    List<UserModelVo> loadAllTalent(@Param("hospitalId") String hospitalId);

    List<UserProjectVo> loadAllTalentId(Page<UserProjectVo> pageList);

    List<String> getHospitalIdByTalentId(@Param("talentId")String talentId);

}
