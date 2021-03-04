package org.jeecg.modules.pay.model;

import lombok.Data;

/**
 * 支付类
 */
@Data
public class PayBean {

    //支付人小程序openId
    private String openId;
    //支付人订单号码
    private String orderId;
    //支付人的支付名称
    private String orderName;
    //支付者回掉地址
    private String returnUrl;
    //支付者支付价格
    private Integer money;
}
