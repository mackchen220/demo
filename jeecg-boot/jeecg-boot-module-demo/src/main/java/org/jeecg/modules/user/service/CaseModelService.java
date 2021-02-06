package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.CaseModel;
public interface CaseModelService{


    int deleteByPrimaryKey(String id);

    int insert(CaseModel record,String id);


    CaseModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseModel record);

    int updateByPrimaryKey(CaseModel record);

}
