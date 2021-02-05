package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserModel;

import java.util.List;
import java.util.Map;

public interface TalentInfoModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(TalentInfoModel record);

    int insertSelective(TalentInfoModel record);

    TalentInfoModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentInfoModel record);

    int updateByPrimaryKey(TalentInfoModel record);

    List<UserModel> loadIndexTalentList(@Param("limit") String limit);

}
