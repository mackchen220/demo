package org.jeecg.modules.index.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.index.model.PartyModel;

import java.util.Map;

public interface PartyModelService{




    int insertSelective(PartyModel record);

    PartyModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PartyModel record);


    Page<PartyModel> loadPartyList(Page<PartyModel> page, String userId );

    Map loadPartyInfo(String partyId, String userId);
}
