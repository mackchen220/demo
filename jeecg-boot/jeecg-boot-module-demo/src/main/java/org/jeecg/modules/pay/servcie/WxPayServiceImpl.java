package org.jeecg.modules.pay.servcie;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.util.MapUtil;
import org.jeecg.modules.commons.util.RandomUtil;
import org.jeecg.modules.commons.util.XmlUtil;
import org.jeecg.modules.pay.config.WxPayH5Config;
import org.jeecg.modules.pay.config.WxPaySignature;
import org.jeecg.modules.pay.constants.WxPayConstants;
import org.jeecg.modules.pay.enums.BestPayTypeEnum;
import org.jeecg.modules.pay.model.*;
import org.jeecg.modules.pay.model.request.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-07-02 13:40
 */
@Slf4j
public class WxPayServiceImpl implements BestPayService {

    private WxPayH5Config wxPayH5Config;

    private Map<String, String> stringStringMap;

    public void setWxPayH5Config(WxPayH5Config wxPayH5Config) {
        this.wxPayH5Config = wxPayH5Config;
    }

    @Override
    public PayResponse pay(PayRequest request) {
        WxPayUnifiedorderRequest wxRequest = new WxPayUnifiedorderRequest();
        wxRequest.setOutTradeNo(request.getOrderId());
        wxRequest.setTotalFee(request.getOrderAmount());
        wxRequest.setBody(request.getOrderName());
        wxRequest.setOpenid(request.getOpenid());
//        wxRequest.setDetail("uid=1");

        wxRequest.setTradeType(switchH5TradeType(request.getPayTypeEnum()));
        wxRequest.setAppid(wxPayH5Config.getAppId());
        wxRequest.setMchId(wxPayH5Config.getMchId());
        wxRequest.setNotifyUrl(wxPayH5Config.getNotifyUrl());
        wxRequest.setNonceStr(RandomUtil.getRandomStr());
        wxRequest.setSpbillCreateIp(request.getSpbillCreateIp() == null || request.getSpbillCreateIp().isEmpty() ? "8.8.8.8" : request.getSpbillCreateIp());
        stringStringMap = MapUtil.buildMap(wxRequest);
        wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), wxPayH5Config.getMchKey()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WxPayConstants.WXPAY_GATEWAY)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor((new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)))
                        .build()
                )
                .build();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), XmlUtil.toString(wxRequest));
        Call<WxPaySyncResponse> call = retrofit.create(WxPayApi.class).unifiedorder(body);
        Response<WxPaySyncResponse> retrofitResponse  = null;
        try{
            retrofitResponse = call.execute();
        }catch (IOException e) {
            e.printStackTrace();
        }
        if (!retrofitResponse.isSuccessful()) {
            throw new JeecgBootException("微信支付, 网络异常");
        }
        WxPaySyncResponse response = retrofitResponse.body();

        if(!response.getReturnCode().equals(WxPayConstants.SUCCESS)) {
            log.warn("微信支付失败, returnCode != SUCCESS, returnMsg = " + response.getReturnMsg());
            throw new JeecgBootException("微信支付失败= " + response.getReturnMsg());
        }
        if (!response.getResultCode().equals(WxPayConstants.SUCCESS)) {
            log.warn("微信支付失败, resultCode != SUCCESS, err_code = " + response.getErrCode() + " err_code_des=" + response.getErrCodeDes());
            throw new JeecgBootException("支付失败"+ response.getErrCodeDes());
        }

        return buildPayResponse(response);
    }

    @Override
    public boolean verify(Map map, SignType signType, String sign) {
        return WxPaySignature.verify(map, wxPayH5Config.getMchKey());
    }

    @Override
    public PayResponse syncNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * 异步通知
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse asyncNotify(String notifyData) {
        //签名校验
        if (!WxPaySignature.verify(XmlUtil.toMap(notifyData), wxPayH5Config.getMchKey())) {
            log.error("【微信支付异步通知】签名验证失败, response={}", notifyData);
            throw new RuntimeException("【微信支付异步通知】签名验证失败");
        }

        //xml解析为对象
        WxPayAsyncResponse asyncResponse = (WxPayAsyncResponse) XmlUtil.toObject(notifyData, WxPayAsyncResponse.class);

        if(!asyncResponse.getReturnCode().equals(WxPayConstants.SUCCESS)) {
            throw new RuntimeException("【微信支付异步通知】发起支付, returnCode != SUCCESS, returnMsg = " + asyncResponse.getReturnMsg());
        }
        //该订单已支付直接返回
        if (!asyncResponse.getResultCode().equals(WxPayConstants.SUCCESS)
                && asyncResponse.getErrCode().equals("ORDERPAID")) {
            return buildPayResponse(asyncResponse);
        }

        if (!asyncResponse.getResultCode().equals(WxPayConstants.SUCCESS)) {
            throw new RuntimeException("【微信支付异步通知】发起支付, resultCode != SUCCESS, err_code = " + asyncResponse.getErrCode() + " err_code_des=" + asyncResponse.getErrCodeDes());
        }

        return buildPayResponse(asyncResponse);
    }

    /**
     * 微信退款
     * @param request
     * @return
     */
    public RefundResponse refund(RefundRequest request) {
        WxPayRefundRequest wxRequest = new WxPayRefundRequest();
        wxRequest.setOutTradeNo(request.getOrderId());
        wxRequest.setOutRefundNo(request.getOrderId());
        wxRequest.setTotalFee(request.getOrderAmount());
        wxRequest.setRefundFee(request.getOrderAmount());

        wxRequest.setAppid(wxPayH5Config.getAppId());
        wxRequest.setMchId(wxPayH5Config.getMchId());
        wxRequest.setNonceStr(RandomUtil.getRandomStr());
        wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), wxPayH5Config.getMchKey()));

        //初始化证书
        if (wxPayH5Config.getSslContext() == null) {
            wxPayH5Config.initSSLContext();
        }
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .sslSocketFactory(wxPayH5Config.getSslContext().getSocketFactory())
                .addInterceptor((new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WxPayConstants.WXPAY_GATEWAY)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build();
        String xml = XmlUtil.toString(wxRequest);
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),xml);
        Call<WxRefundResponse> call = retrofit.create(WxPayApi.class).refund(body);
        Response<WxRefundResponse> retrofitResponse  = null;
        try{
            retrofitResponse = call.execute();
        }catch (IOException e) {
            e.printStackTrace();
        }
        if (!retrofitResponse.isSuccessful()) {
            throw new RuntimeException("【微信退款】发起退款, 网络异常");
        }
        WxRefundResponse response = retrofitResponse.body();

        if(!response.getReturnCode().equals(WxPayConstants.SUCCESS)) {
            throw new RuntimeException("【微信退款】发起退款, returnCode != SUCCESS, returnMsg = " + response.getReturnMsg());
        }
        if (!response.getResultCode().equals(WxPayConstants.SUCCESS)) {
            throw new RuntimeException("【微信退款】发起退款, resultCode != SUCCESS, err_code = " + response.getErrCode() + " err_code_des=" + response.getErrCodeDes());
        }

        return buildRefundResponse(response);
    }

    private RefundResponse buildRefundResponse(WxRefundResponse response) {
        RefundResponse refundResponse = new RefundResponse();
        refundResponse.setOrderId(response.getOutTradeNo());
        refundResponse.setOrderAmount(response.getTotalFee());
        refundResponse.setOutTradeNo(response.getTransactionId());
        refundResponse.setRefundId(response.getOutRefundNo());
        refundResponse.setOutRefundNo(response.getRefundId());
        return refundResponse;
    }

    private PayResponse buildPayResponse(WxPayAsyncResponse response) {
        PayResponse payResponse = new PayResponse();
        payResponse.setOrderAmount(response.getTotalFee());
        payResponse.setOrderId(response.getOutTradeNo());
        payResponse.setOutTradeNo(response.getTransactionId());
        payResponse.setMwebUrl(response.getMwebUrl());
        return payResponse;
    }

    /**
     * 返回给h5的参数
     * @param response
     * @return
     */
    private PayResponse buildPayResponse(WxPaySyncResponse response) {
        log.info("返回值："+response);
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomUtil.getRandomStr();
        String packAge = "prepay_id=" + response.getPrepayId();
        String signType = "MD5";

        //先构造要签名的map
        Map<String, String> map = new HashMap<>();
        map.put("appId", response.getAppid());
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", response.getNonceStr());
        map.put("package", packAge);
        map.put("signType", signType);

        PayResponse payResponse = new PayResponse();
        payResponse.setAppId(response.getAppid());
        payResponse.setTimeStamp(timeStamp);
        payResponse.setNonceStr(nonceStr);
        payResponse.setPackAge("Sign=WXPay");
        payResponse.setSignType(signType);
        payResponse.setPrepayId(response.getPrepayId());
        payResponse.setPartnerId(wxPayH5Config.getMchId());
        payResponse.setPaySign(WxPaySignature.sign(stringStringMap, wxPayH5Config.getMchKey()));
//        Boolean verify = WxPaySignature.verify(stringStringMap, wxPayH5Config.getMchKey());
        payResponse.setMwebUrl(response.getMwebUrl());

        return payResponse;
    }


    /**
     * H5支付交易类型选择
     */
    public String switchH5TradeType(BestPayTypeEnum payTypeEnum){
        String tradeType = "JSAPI";
        switch (payTypeEnum){
            case WXPAY_H5:
                tradeType = "JSAPI";
                break;
            case WXPAY_MWEB:
                tradeType = "MWEB";
                break;
            case WXPAY_APP:
                tradeType = "APP";
                break;
        }
        return tradeType;
    }

}
