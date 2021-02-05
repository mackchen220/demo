package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.TalentInfoModel;

import java.util.List;

public interface TalentInfoModelService{


    int deleteByPrimaryKey(String id);

    int insert(TalentInfoModel record);

    int insertSelective(TalentInfoModel record);

    TalentInfoModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentInfoModel record);

    int updateByPrimaryKey(TalentInfoModel record);

//    //首页达人推荐
//    List loadIndexTalentList(String limit);

}
