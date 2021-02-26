package org.jeecg.modules.user.service;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.AddressModelMapper;
import org.jeecg.modules.user.mapper.OrderModelMapper;
import org.jeecg.modules.user.mapper.VipModelMapper;
import org.jeecg.modules.user.model.AddressModel;
import org.jeecg.modules.user.model.OrderModel;
import org.jeecg.modules.user.model.VipModel;
import org.jeecg.modules.user.model.vo.VipModelVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VipModelServiceImpl implements VipModelService {

    @Resource
    private VipModelMapper vipModelMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private AddressModelMapper addressModelMapper;
    @Resource
    private OrderModelMapper orderModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return vipModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(VipModel record) {
        return vipModelMapper.insert(record);
    }

    @Override
    public int insertSelective(VipModel record) {
        return vipModelMapper.insertSelective(record);
    }

    @Override
    public VipModel selectByPrimaryKey(String id) {
        return vipModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(VipModel record) {
        return vipModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(VipModel record) {
        return vipModelMapper.updateByPrimaryKey(record);
    }


    @Override
    public List<VipModelVo> getVipList() {
        List<VipModelVo> vipList = vipModelMapper.getVipList();
        return vipList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map addVipOrder(String adressId, String vipId, String userId) {
        Map map = new HashMap();
        VipModel vipModel = vipModelMapper.selectByPrimaryKey(vipId);
        if (ValidateTool.isNull(vipModel)) {
            throw new JeecgBootException("产品已下架");
        }
        AddressModel addressModel = addressModelMapper.selectByPrimaryKey(adressId);
        if (ValidateTool.isNull(addressModel)) {
            throw new JeecgBootException("请填写地址");
        }
        if (!userId.equals(addressModel.getUserId())) {
            throw new JeecgBootException("地址错误");
        }
        Object sumNum = redisUtil.get(RedisKey.VIP_NUM + RedisKey.KEY_SPLIT + vipId);
        long incr = redisUtil.decr(RedisKey.VIP_NUM + RedisKey.KEY_SPLIT + vipId, 1);
        if (incr <= 0) {
            throw new JeecgBootException("来晚了，优惠名额不足");
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setId(SeqUtils.nextIdStr());
        orderModel.setUserId(userId);
        orderModel.setOperationType(Constant.TYPE_INT_4);
        orderModel.setContent("亨氧" + vipModel.getVipName() + "会员卡");
        orderModel.setOperationType(Constant.TYPE_INT_4);
        orderModel.setNum(Constant.TYPE_INT_1);
        orderModel.setOptStatus(Constant.TYPE_INT_0);
        orderModelMapper.insertSelective(orderModel);
        BigDecimal divide = new BigDecimal(String.valueOf(sumNum)).divide(new BigDecimal(vipModel.getQuotaNum()), 2, BigDecimal.ROUND_DOWN);
        BigDecimal subtract = new BigDecimal("1").subtract(divide);
        //剩余名额
        map.put("nextQuota", String.valueOf(divide));
        //已购买
        map.put("quota", String.valueOf(subtract));
        //封面图片
        map.put("image", vipModel.getImage());
        //限时价
        map.put("priceLow", vipModel.getPriceLow());
        //原价
        map.put("priceHigh", vipModel.getPriceHigh());
        //订单id
        map.put("orderId", orderModel.getId());

        return map;
    }


    @Override
    public Map getVipOrder(String addressId, String vipId, String orderId) {
        VipModel vipModel = vipModelMapper.selectByPrimaryKey(vipId);
        if (ValidateTool.isNull(vipModel)) {
            throw new JeecgBootException("产品已下架");
        }
        AddressModel addressModel = addressModelMapper.selectByPrimaryKey(addressId);
        if (ValidateTool.isNull(addressModel)) {
            throw new JeecgBootException("地址参数错误");
        }
        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(orderModel)) {
            throw new JeecgBootException("订单错误");
        }

        //地址信息
        Map<String, Object> map = new HashMap<>();
        map.put("address", addressModel.getAddress());
        map.put("name", addressModel.getName());
        map.put("phone", addressModel.getPhone());
        map.put("city", addressModel.getCity());

        //vip卡图片
        map.put("image", vipModel.getImage());
        //订单信息
        map.put("price", vipModel.getPriceLow());
        map.put("orderTime", orderModel.getCreateTime());

        return map;
    }
}
