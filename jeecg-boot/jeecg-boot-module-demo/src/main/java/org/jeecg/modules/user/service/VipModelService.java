package org.jeecg.modules.user.service;

import org.jeecg.modules.user.model.VipModel;
import org.jeecg.modules.user.model.vo.VipModelVo;

import java.util.List;
import java.util.Map;

public interface VipModelService{


    int deleteByPrimaryKey(String id);

    int insert(VipModel record);

    int insertSelective(VipModel record);

    VipModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VipModel record);

    int updateByPrimaryKey(VipModel record);

    List<VipModelVo> getVipList();


    Map addVipOrder(String adressId,String vipId, String userId);

    Map getVipOrder(String addressId,String vipId, String orderId,String userId);


    String addVipCallBack(String orderId);
}
