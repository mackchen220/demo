package org.jeecg.modules.pay.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.pay.model.PayModel;
import org.jeecg.modules.pay.servcie.PayService;
import org.jeecg.modules.user.model.OrderModel;
import org.jeecg.modules.user.service.OrderModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.jeecg.modules.pay.config.AlipayConfig.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/hy/pay")
@Api(tags = "支付模块")
public class PayController {


    @Resource
    private PayService payService;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private OrderModelService orderModelService;

    private final static AlipayClient alipayClient =
            new DefaultAlipayClient( GATEWAY, APP_ID_2, APP_PRIVATE_KEY_2, "json", CHARSET, ALIPAY_PUBLIC_KEY_2, "RSA2");

    //todo 注意更改回调域名
    private final static String domin = "http://test/callback";


    @ApiOperation("支付")
    @PostMapping("/wxPay")
    public Result<?> orderPay(PayModel payModel){
        return payService.wxOrderPay(payModel);
    }


    @ApiOperation("回调")
    @PostMapping("/wxNotify")
    public JSONArray notify(String notifyDate){
        return payService.notify(notifyDate);
    }



    @PostMapping("/aliPay")
    public Result pay(PayModel payModel){
        OrderModel orderModel = orderModelService.selectByPrimaryKey(payModel.getOrderId());
        if (Objects.isNull(orderModel)){
            return Result.error("用户为空");
        }
        if(StringUtils.isEmpty(payModel.getOrderId())){
            return Result.error("订单错误");
        }
//        String aliPayNtotity = qxConfService.getValue("aliPayNtotity").getValue();
//        String authService = qxConfService.getValue("authService").getValue();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(IdWorker.getIdStr());
        model.setBody(payModel.getOrderId());
        model.setSubject("购买商品");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(domin);
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return Result.OK(response.getBody());
        } catch (AlipayApiException alipayae) {
            alipayae.printStackTrace();
            return Result.error(alipayae.getErrMsg());
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/callback")
    public Object callback(HttpServletRequest request){
        Map<String, String> params = convertRequestParamsToMap(request);
        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调，{}", paramsJson);
        // todo 调用SDK验证签名 注意验签
        if (true) {
            log.info("支付宝回调签名认证成功");
//            new Thread(() -> {
            String param = JSON.toJSONString(params);
            JSONObject obj = JSONUtil.parseObj(param);
            String trade_status = obj.getStr("trade_status");
            log.info(trade_status);
            String body = obj.getStr("body"); //格式： uid:{uid}
            log.info(body);
            //{"gmt_create":"2021-01-29 10:25:55","charset":"utf-8","seller_email":"dai124318@qq.com","subject":"实名认证回调","sign":"DeH/+/jFQuwToxglRT3NlWEn+gy6zHxzMm3DLyZ5pPnJbwUVE4ncbNixg9RTE/sWy4NFU89ng2yZyobAzPVpO+ktR6g++w6hpBqNoIc2t0YjpHhEQPqLlLB4xiIkcnFjcT6CCCyUUF+KxcRIyt0q3iIuxO0CYKKoYMhmHdxbRqJwBxD9blxy6KBoQBcyuK3mcyePvh5I83RHWrPzwb3+mI9BuU3yDAiJZ742B9PJneUWGOW4uyolk6kyi2pMfQF6jeK58JiffHkGGEmUl9ddyGnneiq/ouepvav+SZJHKwkEqTzkxhsK7xeX3kC5Nv6lVzzwas59rSH9FjAT6iBCAw==","body":"value","buyer_id":"2088722267984584","invoice_amount":"0.01","notify_id":"2021012900222102556084581422837292","fund_bill_list":"[{\"amount\":\"0.01\",\"fundChannel\":\"PCREDIT\"}]","notify_type":"trade_status_sync","trade_status":"TRADE_SUCCESS","receipt_amount":"0.01","app_id":"2021002124687522","buyer_pay_amount":"0.01","sign_type":"RSA2","seller_id":"2088041373647234","gmt_payment":"2021-01-29 10:25:55","notify_time":"2021-01-29 10:25:56","version":"1.0","out_trade_no":"1354979002236829697","total_amount":"0.01","trade_no":"2021012922001484581423281620","auth_app_id":"2021002124687522","buyer_logon_id":"172***@qq.com","point_amount":"0.00"}
            if (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")) {
                try {
                    // todo 支付成功 处理支付成功逻辑
//                    MenOrder order = menOrderService.getById(body);
//                    if(order == null){
//                        return null;
//                    }
//                    if(order.getPayStatus() >0){
//                        return null;
//                    }
//                    order.setPayStatus(1);
//                    boolean b = menOrderService.updateById(order);
//                    if (!b){
//                        throw new RuntimeException();
//                    }

                } catch (Exception e) {
                    log.error("支付宝回调业务处理出现异常, params:" + paramsJson, e);
                }
            } else {
                log.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}", trade_status, paramsJson);
            }
//            }).start();
            // todo 如果签名验证正确，立即返回success，后续业务另起线程单独处理
            // todo 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
            return "success";
        } else {
            log.info("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
            return "failure";
        }
    }


    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;
            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }








}
