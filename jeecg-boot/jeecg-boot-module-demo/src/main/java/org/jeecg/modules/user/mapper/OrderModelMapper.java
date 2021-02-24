package org.jeecg.modules.user.mapper;

import cn.hutool.db.sql.Order;
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

    int updateOrderScore(OrderModel record);


    List<OrderModelVo> loadOrderList(@Param("userId") String userId, @Param("type")String type, Page<OrderModelVo> page);

    OrderModel loadScoreModel(@Param("userId") String userId);
}
