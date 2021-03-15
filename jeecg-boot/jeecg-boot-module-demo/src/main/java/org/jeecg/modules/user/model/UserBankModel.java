package org.jeecg.modules.user.model;

import lombok.Data;

/**
    * 用户银行卡管理
    */
@Data
public class UserBankModel {
    /**
    * 主键id
    */
    private String id;

    /**
    * 用户id
    */
    private String userId;

    /**
    * 开户银行
    */
    private String bank;

    /**
    * 开户支行名称
    */
    private String subBankName;

    /**
    * 开户姓名
    */
    private String realName;

    /**
    * 银行卡号
    */
    private String cardNumber;

    /**
    * 备注
    */
    private String remark;

    /**
    * 是否审核 null-未审核，1-通过，0-不通过
    */
    private Integer isCheck;

    /**
    * 是否是黑名单 0-否，1-是
    */
    private Integer isBlacklist;

    private Boolean isDefault;

    /**
    * 省份
    */
    private String province;

    /**
    * 城市
    */
    private String city;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 更新日期
    */
    private String updateTime;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 更新人
    */
    private String updateBy;

    /**
    * 删除标识0-正常,1-已删除
    */
    private Integer delFlag;
    /**
     * 图标
     */
    private String icon;

}
