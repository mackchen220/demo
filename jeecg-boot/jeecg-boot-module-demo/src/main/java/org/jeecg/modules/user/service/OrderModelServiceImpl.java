package org.jeecg.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.index.mapper.PlatformConfigurationMapper;
import org.jeecg.modules.index.model.PlatformConfiguration;
import org.jeecg.modules.pay.model.PayResponse;
import org.jeecg.modules.user.mapper.*;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserBankModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.OrderModelVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.jeecg.modules.user.model.OrderModel;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class OrderModelServiceImpl implements OrderModelService {

    @Resource
    private OrderModelMapper orderModelMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private TalentInfoModelMapper talentInfoModelMapper;
    @Resource
    private UserBankModelMapper userBankModelMapper;
    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private UserIncomeService userIncomeService;
    @Resource
    private PlatformConfigurationMapper platformConfigurationMapper;
    @Resource
    private VipModelService vipModelService;
    @Resource
    private TalentInfoModelService talentInfoModelService;

    @Override
    public int insertSelective(OrderModel record) {
        return orderModelMapper.insertSelective(record);
    }

    @Override
    public OrderModel selectByPrimaryKey(String id) {
        return orderModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderModel record) {
        return orderModelMapper.updateByPrimaryKeySelective(record);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void payProjectDeposit(String projectIds, String talentId, String userId, String amount, String num, String hospitalId) {
        if (ValidateTool.isNull(projectIds) || projectIds.split(",").length < 1) {
            throw new JeecgBootException("请选择项目");
        }
        if (ValidateTool.isNull(amount) || Long.valueOf(amount) < 1) {
            throw new JeecgBootException("定金金额异常");
        }
        OrderModel orderModel = new OrderModel();
        orderModel.setId(SeqUtils.nextIdStr());
        orderModel.setProjectId(projectIds);
        orderModel.setTalentId(talentId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setNum(Integer.valueOf(num));
        orderModel.setHospitalId(hospitalId);
        orderModel.setOperationType(1);
        String[] split = projectIds.split(",");
        String projectName = projectMapper.getProjectName(split[0]);
        orderModel.setContent(projectName + "定金");
        orderModelMapper.insertSelective(orderModel);
    }


    @Override
    public Page<OrderModelVo> loadOrderList(String userId, String optStatus, Page<OrderModelVo> page) {

        if (Constant.CHECKTYPE0.equals(optStatus)){
            optStatus=null;
        }
        List<OrderModelVo> orderModelVos = orderModelMapper.loadOrderList(userId, optStatus, page);

        return page.setRecords(orderModelVos);
    }


    //    达人评分规则
//    达人评分由达人对客户推荐的机构的产生交易后，客户可对本单交易进行评分，评分分别从三个维度进行：1服务，2价格，3效果，三项分值的平均分为达人的综合分。
//            1⃣️服务：1-10分
//    1-3分为不满意
//    4-6分为基本满意
//    7-10分为非常满意
//
//    2⃣️价格：1-10分
//    1-3分为非常贵
//    4-6分为比较合理
//    7-10分为非常便宜
//    3⃣️效果：1-10分
//    1-3分为不满意
//    4-6分为基本满意
//    7-10分为非常满意
//    打分方式：
//    1⃣️：客户可以在每项分值选择任意数，为了让客户明确几分代表满意，几分代表不满意，打分条需要做不满意阶段分和满意阶段分的区分。
//    2⃣️：客户进行评分时，打分条分值均默认10分，客户左右滑动进行真实评分，当三项基本分选好后即可提交。
//    3⃣️：客户提交评分后，会显示点评成功，会显示你评分三项得综合分数
//    达人积分规则：
//    第一单：
//    A达人，选择操作机构是B机构
//    服务分：9
//    价格分：8
//    效果分：7
//    综合分：8
//    总分值24（服务9+价格8+效果7）➗3=综合分8
//    此项评分值将同步到达人评分系统和对应的操作机构。
//    达人和对象的机构显示分为：
//    综合分：8
//    服务9分   价格8分   效果7分
//    第二单：
//    A达人，操作机构是B机构
//    服务分：6
//    价格分：3 非常贵
//    效果分：7
//    综合分：5.33
//    总分值16（服务6+价格3+效果7）➗3=综合分5.33
//    达人和机构的评分更新为
//    目前单数综合分总值➗单数=最新综合分
//   （第一单综合分8+第二单综合分5.33）➗2=最新综合分6.67分
//    第二单的达人和机构综合分为6.67分
//    服务分：单数总分➗单数=7.5
//    价格分：单数总分➗单数=5.5
//    效果分：单数总分➗单数=7.5
//    算法以此推类，当达人和机构的综合分连续3个月低于3分以下，平台有权进行解除下架达人。
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateOrderScore(String userId, String orderId, String effect, String attitude, String price) {

        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(orderModel)) {
            throw new JeecgBootException("订单不存在");
        }
        if (Constant.TYPE_INT_1 == orderModel.getEvaluateStatus()) {
            throw new JeecgBootException("订单已完成评价");
        }
        if (!userId.equals(orderModel.getUserId())) {
            throw new JeecgBootException("订单信息错误");
        }
        if (Double.valueOf(effect) < 0 || Double.valueOf(attitude) < 0 || Double.valueOf(price) < 0) {
            throw new JeecgBootException("评分不能为负");
        }
        if (Double.valueOf(effect) > 10 || Double.valueOf(attitude) > 10 || Double.valueOf(price) > 10) {
            throw new JeecgBootException("不能大于十分");
        }
        orderModel.setAttitude(attitude);
        orderModel.setEffect(effect);
        orderModel.setPrice(price);

        BigDecimal num1 = new BigDecimal(effect);
        BigDecimal num2 = new BigDecimal(attitude);
        BigDecimal num3 = new BigDecimal(price);
        BigDecimal add = num1.add(num2).add(num3);

        //计算本次平均分
        BigDecimal divide = add.divide(new BigDecimal("3"), 2, BigDecimal.ROUND_DOWN);

        orderModel.setAverageScore(String.valueOf(divide.doubleValue()));
        //更新此订单评分
        orderModel.setEvaluateStatus(1);
        orderModelMapper.updateOrderScore(orderModel);
        //查询此达人所有订单评分总和
        OrderModel scoreModel = orderModelMapper.loadScoreModel(orderModel.getTalentId());

        //计算新的平均分数
        BigDecimal averageScore = new BigDecimal(scoreModel.getAverageScore()).divide(new BigDecimal(scoreModel.getNum()), 2, BigDecimal.ROUND_DOWN);
        //计算新的服务分
        BigDecimal attitudeScore = new BigDecimal(scoreModel.getAttitude()).divide(new BigDecimal(scoreModel.getNum()), 2, BigDecimal.ROUND_DOWN);
        //计算新的价格分
        BigDecimal priceScore = new BigDecimal(scoreModel.getPrice()).divide(new BigDecimal(scoreModel.getNum()), 2, BigDecimal.ROUND_DOWN);
        //计算新的效果分
        BigDecimal effectScore = new BigDecimal(scoreModel.getEffect()).divide(new BigDecimal(scoreModel.getNum()), 2, BigDecimal.ROUND_DOWN);

        TalentInfoModel talentInfoModel = new TalentInfoModel();
        talentInfoModel.setEffect(String.valueOf(effectScore.doubleValue()));
        talentInfoModel.setAttitude(String.valueOf(attitudeScore.doubleValue()));
        talentInfoModel.setPrice(String.valueOf(priceScore.doubleValue()));
        talentInfoModel.setAverageScore(String.valueOf(averageScore.doubleValue()));

        talentInfoModel.setUserId(orderModel.getTalentId());
        talentInfoModelMapper.updateTalentScore(talentInfoModel);

        return String.valueOf(divide.doubleValue());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addWithdrawalOrder(UserModel user, String bankId, String money) {


        if (Long.valueOf(user.getMoney()) < Long.valueOf(money)) {
            throw new JeecgBootException("余额不足");
        }
        OrderModel orderModel = new OrderModel();
        if (ValidateTool.isNotNull(bankId)) {
            UserBankModel userBankModel = userBankModelMapper.loadBankInfoByUserId(null, bankId, user.getId());
            if (ValidateTool.isNull(userBankModel)) {
                throw new JeecgBootException("银行卡错误");
            }
            orderModel.setOutsideCardNum(userBankModel.getCardNumber());
        } else {
            if (ValidateTool.isNotNull(user.getWechat())) {
                orderModel.setOutsideCardNum(user.getWechat());
                orderModel.setPayType(Constant.TYPE_INT_2);
            } else {
                throw new JeecgBootException("请填写微信号或者联系客服");
            }
        }
        int i = userModelMapper.updateUserMoney(user.getId(), money, Constant.TYPE_INT_2);
        if (i < 1) {
            throw new JeecgBootException("余额不足");
        }
        //提现手续费
        String fee = "0";
        orderModel.setPayMoney(money);
        PlatformConfiguration config = platformConfigurationMapper.getConfigByKey(Constant.CONFIG_KEY_FEE);
        if (ValidateTool.isNotNull(config) && ValidateTool.isNotNull(config.getConfigValue())) {
            BigDecimal num1 = new BigDecimal(config.getConfigValue());
            BigDecimal num2 = new BigDecimal(money);
            BigDecimal multiply = num2.divide(new BigDecimal("100"),2,BigDecimal.ROUND_DOWN).multiply(num1);
            log.info("提现手续费配置{},提现金额{}，提现手续费{}", config.getConfigValue(), money, multiply.longValue());
            orderModel.setPayMoney(String.valueOf(Long.valueOf(money) - multiply.longValue()));
            fee = String.valueOf(multiply.longValue());
        }
        userIncomeService.addUserIncome(user.getId(), 1, "用户提现", Long.valueOf(money), fee);
        orderModel.setId(SeqUtils.nextIdStr());
        orderModel.setUserId(user.getId());
        orderModel.setAmount(money);
        orderModel.setOperationType(Constant.TYPE_INT_3);
        orderModel.setContent("提现");
        orderModel.setOptStatus(Constant.TYPE_INT_0);
        orderModelMapper.insertSelective(orderModel);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String orderCallBack(PayResponse payResponse) {
        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(payResponse.getOrderId());
        log.info("orderModel{},orderId{}", JSON.toJSONString(orderModel),payResponse.getOrderId());
        if (ValidateTool.isNull(orderModel)) {
            throw new JeecgBootException("订单不存在" + payResponse.getOrderId());
        }
        if (orderModel.getOptStatus()==2){
            log.warn("重复回调,订单id{}",orderModel.getId());
            return "ok";
        }
        if (Constant.TYPE_INT_1 == orderModel.getOperationType()) {
            //医美项目定金
            //TODO 达人提成

        }
        if (Constant.TYPE_INT_2 == orderModel.getOperationType()) {
            //购买课程

        }
        if (Constant.TYPE_INT_4 == orderModel.getOperationType()) {
            //购买会员 上级提成
            vipModelService.addVipCallBack(payResponse.getOrderId());
        }
        if (Constant.TYPE_INT_5 == orderModel.getOperationType()) {
            //缴纳保证金
            talentInfoModelService.talentCallBack(payResponse.getOrderId());
        }
        OrderModel orderModel1 = new OrderModel();
        orderModel1.setId(payResponse.getOrderId());
        orderModel1.setOptStatus(Constant.TYPE_INT_2);
        orderModel1.setPayType(Constant.TYPE_INT_2);
        orderModel1.setPayMoney(String.valueOf(orderModel.getAmount()));
        orderModelMapper.updateByPrimaryKeySelective(orderModel1);
        return "ok";
    }


    @Override
    public void updateOrderStatus(String orderId) {
        if (ValidateTool.isNull(orderId)) {
            throw new JeecgBootException("订单参数错误");
        }
        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(orderModel)) {
            throw new JeecgBootException("订单不存在");
        }
        OrderModel orderModel1 = new OrderModel();
        orderModel1.setId(orderId);
        orderModel1.setOptStatus(Constant.TYPE_INT_5);
        orderModelMapper.updateByPrimaryKeySelective(orderModel1);
    }

    public static void main(String[] args) {
        //尽量用字符串的形式初始化
        BigDecimal num1 = new BigDecimal("2.23");
        BigDecimal num2 = new BigDecimal("3.33");
        BigDecimal num3 = new BigDecimal("4.44");
        BigDecimal add = num1.add(num2).add(num3);
        System.out.println(add.doubleValue());
        System.out.println(add.divide(new BigDecimal("3"), 2, BigDecimal.ROUND_DOWN));
        System.out.println(add.divide(new BigDecimal("3"), 3, BigDecimal.ROUND_DOWN));

    }
}
