package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hwpf.model.types.LSTFAbstractType;
import org.jeecg.modules.user.model.CaseModel;
import org.jeecg.modules.user.model.vo.CaseModelVo;

import java.util.List;

@Mapper
public interface CaseModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseModel record);

    int insertSelective(CaseModel record);

    CaseModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseModel record);

    int updateByPrimaryKey(CaseModel record);

    List<CaseModelVo> loadCaseList(@Param("userId") String userId, @Param("type")String type);

    List<String> loadCaseTypeList(@Param("userId") String userId);

}
