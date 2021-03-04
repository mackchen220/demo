package org.jeecg.modules.pay.model;

import lombok.Data;
import org.jeecg.modules.pay.enums.BestPayTypeEnum;

/**
 * 支付时请求参数
 */
@Data
public class PayRequest {

    /**
     * 支付方式.
     */
    private BestPayTypeEnum payTypeEnum;

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 订单金额.
     */
    private Integer orderAmount;

    /**
     * 订单名字.
     */
    private String orderName;

    /**
     * 微信openid, 仅微信支付时需要
     */
    private String openid;

    /**
     * 客户端访问Ip  外部H5支付时必传，需要真实Ip
     */
    private String spbillCreateIp;
    private String body;
}
