package org.jeecg.modules.pay.model;

import lombok.Data;
import org.jeecg.modules.pay.enums.BestPayTypeEnum;

/**
 * 支付时请求参数
 */
@Data
public class RefundRequest {

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
}
