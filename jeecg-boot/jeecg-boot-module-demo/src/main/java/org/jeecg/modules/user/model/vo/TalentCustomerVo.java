package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 达人客户表
    */
@ApiModel(value="org-jeecg-modules-user-model-TalentCustomer")
@Data
public class TalentCustomerVo implements Serializable {

    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String headImage;

    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nickName;

    /**
     * 电话号码
     */
    @ApiModelProperty(value="电话号码")
    private String phone;

    /**
     * 用户类型
     */
    @ApiModelProperty(value="用户类型")
    private String userType;

    /**
    * 达人id
    */
    @ApiModelProperty(value="达人id")
    private String talentId;

    /**
    * 成交总额
    */
    @ApiModelProperty(value="成交总额")
    private Long orderMoney;

    /**
    * 成交订单数量
    */
    @ApiModelProperty(value="成交订单数量")
    private Long orderNum;
//
//    /**
//    * 总提成
//    */
//    @ApiModelProperty(value="总提成")
//    private Long commissionMoney;



    private static final long serialVersionUID = 1L;
}
