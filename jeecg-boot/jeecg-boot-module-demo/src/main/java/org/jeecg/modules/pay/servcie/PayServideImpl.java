package org.jeecg.modules.pay.servcie;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONArray;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.commons.util.XmlUtil;
import org.jeecg.modules.pay.config.WeChatAccountConfig;
import org.jeecg.modules.pay.config.WxPaySignature;
import org.jeecg.modules.pay.constants.WxPayConstants;
import org.jeecg.modules.pay.enums.BestPayTypeEnum;
import org.jeecg.modules.pay.model.PayBean;
import org.jeecg.modules.pay.model.PayModel;
import org.jeecg.modules.pay.model.PayRequest;
import org.jeecg.modules.pay.model.PayResponse;
import org.jeecg.modules.pay.model.request.WxPayAsyncResponse;
import org.jeecg.modules.user.service.OrderModelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Log4j2
@Service
public class PayServideImpl implements PayService{

    @Value("${wechat.notifyUrl}")
    private String notifyUrl;

    @Resource
    private BestPayService bestPayService;

    @Resource
    private OrderModelService orderModelService;

    @Override
    public Result<?> wxOrderPay(PayModel payModel) {
        if(payModel == null){
            return Result.error("系统参数错误");
        }
        if(StringUtils.isEmpty(payModel.getOrderId())){
            return Result.error("订单错误");
        }
        PayBean pay = new PayBean();
        pay.setOrderId(payModel.getOrderId());
        pay.setOrderName("购买商品");
//        pay.setOpenId(syMember.getOpenid());
        pay.setMoney(1);
        pay.setReturnUrl(notifyUrl);
        PayResponse payResponse = this.create(pay);
        log.warn("支付数据{}" , payResponse);
        return Result.OK(payResponse);
    }


    /**
     * 创建支付签名
     * @param pay
     * @return
     */
    public PayResponse create(PayBean pay) {
        PayRequest payService = new PayRequest();
//        payService.setOpenid("1");
//        payService.setBody("uid=1");
        payService.setOrderAmount(pay.getMoney());
        payService.setOrderId(pay.getOrderId());
        payService.setOrderName(pay.getOrderName());

        payService.setPayTypeEnum(BestPayTypeEnum.WXPAY_APP);
        return bestPayService.pay(payService);
    }





    /**
     * 支付回调
     * @param notifyDate
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONArray notify(String notifyDate) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyDate);

        log.info("支付回调内容{}", JSON.toJSONString(payResponse));
        orderModelService.orderCallBack(payResponse);

        return null;
    }






}
