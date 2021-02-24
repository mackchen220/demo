package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.user.model.OrderModel;
import org.jeecg.modules.user.model.vo.OrderModelVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface OrderModelService{




    int insertSelective(OrderModel record);

    OrderModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderModel record);

    void payProjectDeposit(String projectIds,String talentId,String userId ,String amount,String num,String hospitalId);

    Page<OrderModelVo> loadOrderList(String userId, String optStatus, Page<OrderModelVo> page);

    String updateOrderScore(String userId, String orderId,String effect ,String attitude,String price);


}
