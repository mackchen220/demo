package org.jeecg.modules.pay.model.request;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Data
@Root(name = "xml", strict = false)
public class WxPayUnifiedorderRequest {

    @Element(name = "appid")
    private String appid;

    @Element(name = "mch_id")
    private String mchId;

    @Element(name = "nonce_str")
    private String nonceStr;

    @Element(name = "sign")
    private String sign;

    @Element(name = "attach", required = false)
    private String attach;

    @Element(name = "body", required = false)
    private String body;

    @Element(name = "detail", required = false)
    private String detail;

    @Element(name = "notify_url")
    private String notifyUrl;

    @Element(name = "openid", required=false)
    private String openid;

    @Element(name = "out_trade_no")
    private String outTradeNo;

    @Element(name = "spbill_create_ip")
    private String spbillCreateIp;

    @Element(name = "total_fee")
    private Integer totalFee;

    @Element(name = "trade_type")
    private String tradeType;
}
