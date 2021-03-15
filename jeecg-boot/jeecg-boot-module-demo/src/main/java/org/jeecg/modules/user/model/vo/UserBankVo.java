package org.jeecg.modules.user.model.vo;

import lombok.Data;

/**
    * 用户银行卡管理vo
    */
@Data
public class UserBankVo {

    /**
     * 主键id
     */
    private String id;


    /**
    * 开户银行
    */
    private String bank;

    /**
    * 开户支行名称
    */
    private String subBankName;


    /**
    * 银行卡号
    */
    private String cardNumber;


    /**
     * 图标
     */
    private String icon;

}
