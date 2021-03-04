package org.jeecg.modules.pay.servcie;

import okhttp3.RequestBody;

import org.jeecg.modules.pay.model.request.WxOrderQueryResponse;
import org.jeecg.modules.pay.model.request.WxPaySandboxKeyResponse;
import org.jeecg.modules.pay.model.request.WxPaySyncResponse;
import org.jeecg.modules.pay.model.request.WxRefundResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by 廖师兄
 * 2017-07-02 13:36
 */
public interface WxPayApi {

    /**
     * 统一下单
     * @param body
     * @return
     */
    @POST("/pay/unifiedorder")
    Call<WxPaySyncResponse> unifiedorder(@Body RequestBody body);

    /**
     * 申请退款
     * @param body
     * @return
     */
    @POST("/secapi/pay/refund")
    Call<WxRefundResponse> refund(@Body RequestBody body);

    /**
     * 申请沙箱密钥
     * @param body
     * @return
     */
    @POST("/sandboxnew/pay/getsignkey")
    Call<WxPaySandboxKeyResponse> getsignkey(@Body RequestBody body);

    /**
     * 订单查询
     * @param body
     * @return
     */
    @POST("/pay/orderquery")
    Call<WxOrderQueryResponse> orderquery(@Body RequestBody body);
}
