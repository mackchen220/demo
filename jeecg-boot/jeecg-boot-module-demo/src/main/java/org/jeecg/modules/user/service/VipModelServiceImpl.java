package org.jeecg.modules.user.service;

import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.index.mapper.PlatformConfigurationMapper;
import org.jeecg.modules.index.model.PlatformConfiguration;
import org.jeecg.modules.user.mapper.*;
import org.jeecg.modules.user.model.*;
import org.jeecg.modules.user.model.vo.VipModelVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
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
    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private UserAgencyModelMapper userAgencyModelMapper;
    @Resource
    private UserIncomeService userIncomeService;
    @Resource
    private PlatformConfigurationMapper platformConfigurationMapper;

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




    @Override
    public Map loadVipInfo(String vipId, String userId) {
        Map map = new HashMap();
        VipModel vipModel = vipModelMapper.selectByPrimaryKey(vipId);
        if (ValidateTool.isNull(vipModel)) {
            throw new JeecgBootException("产品已下架");
        }
        Object sumNum = redisUtil.get(RedisKey.VIP_NUM + RedisKey.KEY_SPLIT + vipId);
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
        map.put("vipId", vipId);
        return map;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map addVipOrder(String adressId, String vipId, String userId) {
        Map map = new HashMap();
        VipModel vipModel = vipModelMapper.selectByPrimaryKey(vipId);
        if (ValidateTool.isNull(vipModel)) {
            throw new JeecgBootException("产品已下架");
        }
        OrderModel vipOrder = orderModelMapper.getVipOrder(userId, Constant.CHECKTYPE1, Constant.CHECKTYPE4);
        if (ValidateTool.isNotNull(vipOrder)) {
            throw new JeecgBootException("已确认名额，请勿重复购买");
        }
        AddressModel addressModel = addressModelMapper.selectByPrimaryKey(adressId);
        if (ValidateTool.isNull(addressModel)) {
            throw new JeecgBootException("请填写地址");
        }
        if (!userId.equals(addressModel.getUserId())) {
            throw new JeecgBootException("地址错误");
        }
        long incr = redisUtil.decr(RedisKey.VIP_NUM + RedisKey.KEY_SPLIT + vipId, 1);
        if (incr <= 0) {
            throw new JeecgBootException(ErrorInfoCode.NUM_ERROR.getCode(), ErrorInfoCode.NUM_ERROR.getMsg());
        }
        //物流配送费
        PlatformConfiguration configByKey = platformConfigurationMapper.getConfigByKey(Constant.DELIVERY_FEE);
        String delivery = ValidateTool.isNull(configByKey) ? "1500" : configByKey.getConfigValue();
        log.info("物流配送费{}", delivery);
        vipModelMapper.updateNum(vipId);
        OrderModel orderModel = new OrderModel();
        orderModel.setId(SeqUtils.nextIdStr());
        orderModel.setUserId(userId);
        orderModel.setAddressId(adressId);
        orderModel.setOperationType(Constant.TYPE_INT_4);
        orderModel.setContent("亨氧" + vipModel.getVipName() + "会员卡");
        orderModel.setOperationType(Constant.TYPE_INT_4);
        orderModel.setNum(Constant.TYPE_INT_1);
        orderModel.setOptStatus(Constant.TYPE_INT_1);
        orderModel.setVipId(vipId);
        orderModel.setAmount(String.valueOf(vipModel.getPriceLow() + Long.valueOf(delivery)));
        orderModelMapper.insertSelective(orderModel);
        map.put("orderId", orderModel.getId());
        map.put("adressId", adressId);
        map.put("vipId", vipId);
        return map;
    }


    @Override
    public Map getVipOrder(String addressId, String vipId, String orderId, String userId) {
        VipModel vipModel = vipModelMapper.selectByPrimaryKey(vipId);
        if (ValidateTool.isNull(vipModel)) {
            throw new JeecgBootException("产品已下架");
        }
        AddressModel addressModel = addressModelMapper.selectByPrimaryKey(addressId);
        if (ValidateTool.isNull(addressModel)) {
            throw new JeecgBootException("请填写地址");
        }
        if (!userId.equals(addressModel.getUserId())) {
            throw new JeecgBootException("地址错误");
        }
        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(orderModel)) {
            throw new JeecgBootException("订单错误");
        }
        if (!userId.equals(orderModel.getUserId())) {
            throw new JeecgBootException("非法订单");
        }
        PlatformConfiguration configByKey = platformConfigurationMapper.getConfigByKey(Constant.DELIVERY_FEE);
        //物流配送费
        String delivery = ValidateTool.isNull(configByKey) ? "1500" : configByKey.getConfigValue();
        log.info("物流配送费{}", delivery);
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
        map.put("priceHigh", vipModel.getPriceHigh());
        map.put("orderTime", orderModel.getCreateTime());
        map.put("orderId", orderId);
        map.put("vipName", vipModel.getVipName() + "会员卡");
        map.put("delivery", delivery);
        return map;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map updateOrder(String addressId, String vipId, String orderId) {
        Map map = new HashMap();
        VipModel vipModel = vipModelMapper.selectByPrimaryKey(vipId);
        if (ValidateTool.isNull(vipModel)) {
            throw new JeecgBootException("产品已下架");
        }
        OrderModel order = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(order)) {
            throw new JeecgBootException("订单错误");
        }

        PlatformConfiguration configByKey = platformConfigurationMapper.getConfigByKey(Constant.DELIVERY_FEE);
//        AddressModel addressModel = addressModelMapper.selectByPrimaryKey(adressId);
//        if (ValidateTool.isNull(addressModel)) {
//            throw new JeecgBootException("请填写地址");
//        }
//        if (!userId.equals(addressModel.getUserId())) {
//            throw new JeecgBootException("地址错误");
//        }
        //物流配送费
        String delivery = ValidateTool.isNull(configByKey) ? "1500" : configByKey.getConfigValue();
        log.info("物流配送费{}", delivery);
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setOptStatus(Constant.TYPE_INT_1);
        orderModel.setAddressId(addressId);
        //没有填地址就是选择自提，不计算快递费
        if (ValidateTool.isNull(addressId)) {
            orderModel.setAmount(String.valueOf(vipModel.getPriceLow()));
        } else {
            orderModel.setAmount(String.valueOf(vipModel.getPriceLow() + Long.valueOf(delivery)));
        }
        orderModelMapper.updateByPrimaryKeySelective(orderModel);
        //订单id
        map.put("orderId", order.getId());

        return map;
    }



    @Override
    public String addVipCallBack(String orderId) {
        log.info("会员推广奖励{}", orderId);
        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(orderModel)) {
            log.warn("会员推广奖励,查询不到订单{}", orderId);
            return null;
        }
        UserModel userModel = userModelMapper.loadUser(orderModel.getUserId(), null, null, null, null);
        if (ValidateTool.isNull(userModel)) {
            log.warn("会员推广奖励,查询不到用户{} orderId{}", orderModel.getUserId(), orderId);
            return null;
        }
        //修改用户vip
        UserModel model = new UserModel();
        model.setId(orderModel.getUserId());
        model.setVipId(orderModel.getVipId());
        userModelMapper.updateByPrimaryKeySelective(model);
        String money1 = "2000";
        String money2 = "1000";
        //查询上级用户 增加推广奖励
        UserAgencyModel userAgencyModel1 = userAgencyModelMapper.loadUserAgency(orderModel.getUserId(), null);
        if (ValidateTool.isNotNull(userAgencyModel1)) {
            log.warn("会员推广奖励，返现上级id{}", userAgencyModel1.getpUserId());
            userModelMapper.updateUserMoney(userAgencyModel1.getpUserId(), money1, Constant.TYPE_INT_1);
            userIncomeService.addUserIncome(userAgencyModel1.getpUserId(), Constant.TYPE_INT_3, "推广奖励", Long.valueOf(money1), orderModel.getUserId());
        }
        //查询上级的上级用户，增加推广奖励
        UserAgencyModel userAgencyModel2 = userAgencyModelMapper.loadUserAgency(userAgencyModel1.getpUserId(), null);
        if (ValidateTool.isNotNull(userAgencyModel2) && "-1".equals(userAgencyModel2.getpUserId())) {
            log.warn("会员推广奖励，返现上上级id{}", userAgencyModel2.getpUserId());
            userModelMapper.updateUserMoney(userAgencyModel2.getpUserId(), money2, Constant.TYPE_INT_1);
            userIncomeService.addUserIncome(userAgencyModel2.getpUserId(), Constant.TYPE_INT_3, "推广奖励", Long.valueOf(money2), orderModel.getUserId());
        }
        return "ok";
    }
}
