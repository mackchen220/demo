package org.jeecg.modules.pay.enums;

import org.jeecg.modules.pay.exception.BestPayException;

public enum BestPayTypeEnum {

    ALIPAY_APP("alipay_app", "支付宝app"),

    ALIPAY_PC("alipay_pc", "支付宝pc"),

    ALIPAY_WAP("alipay_wap", "支付宝wap"),

    WXPAY_H5("wxpay_h5", "微信公众账号支付"),

    WXPAY_MWEB("MWEB", "微信公众账号支付"),
    WXPAY_APP("APP", "微信APP支付"),
    ;

    private String code;

    private String name;

    BestPayTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BestPayTypeEnum getByCode(String code) {
        for (BestPayTypeEnum bestPayTypeEnum : BestPayTypeEnum.values()) {
            if (bestPayTypeEnum.getCode().equals(code)) {
                return bestPayTypeEnum;
            }
        }
        throw new BestPayException(BestPayResultEnum.PAY_TYPE_ERROR);
    }
}
