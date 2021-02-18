package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.AddressModel;
import org.jeecg.modules.user.model.vo.AddressModelVo;

import java.util.List;

public interface AddressModelService {


    int deleteByPrimaryKey(String id);


    int insertSelective(AddressModelVo record);

    AddressModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AddressModel record);

    int updateByPrimaryKey(AddressModel record);


    List loadUserAddressList(String userId);
}
