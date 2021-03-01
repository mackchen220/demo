package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 达人客户表
    */
@ApiModel(value="org-jeecg-modules-user-model-TalentCustomer")
@Data
public class TalentCustomer implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

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

    /**
    * 总提成
    */
    @ApiModelProperty(value="总提成")
    private Long commissionMoney;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
    * 更新日期
    */
    @ApiModelProperty(value="更新日期")
    private String updateTime;

    /**
    * 创建人
    */
    @ApiModelProperty(value="创建人")
    private String createBy;

    /**
    * 更新人
    */
    @ApiModelProperty(value="更新人")
    private String updateBy;

    /**
    * 删除标识0-正常,1-已删除
    */
    @ApiModelProperty(value="删除标识0-正常,1-已删除")
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}