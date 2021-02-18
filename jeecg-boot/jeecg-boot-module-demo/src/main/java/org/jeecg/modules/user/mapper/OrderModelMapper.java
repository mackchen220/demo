package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.OrderModel;
import org.jeecg.modules.user.model.vo.OrderModelVo;

import java.util.List;

@Mapper
public interface OrderModelMapper {


    int insertSelective(OrderModel record);

    OrderModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderModel record);


    List<OrderModelVo> loadOrderList(@Param("userId") String userId, @Param("type")String type, Page<OrderModelVo> page);
}
