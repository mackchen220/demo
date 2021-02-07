package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.CaseModel;

import java.util.Map;

public interface CaseModelService{


    int deleteByPrimaryKey(String id);

    int insert(CaseModel record,String id);


    CaseModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseModel record);

    int updateByPrimaryKey(CaseModel record);

    Map loadCaseList(String userId,String type);
}
