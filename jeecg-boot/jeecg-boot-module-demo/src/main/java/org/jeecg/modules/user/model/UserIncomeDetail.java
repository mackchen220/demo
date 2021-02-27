package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 用户收入、支出明细
    */
@ApiModel(value="org-jeecg-modules-user-model-UserIncomeDetail")
@Data
public class UserIncomeDetail implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 收入支出类型1:提现 2充值 推广奖励  4项目佣金 5购买课程 6购买项目
    */
    @ApiModelProperty(value="收入支出类型1:提现 2充值 推广奖励  4项目佣金 5购买课程 6购买项目  ")
    private Integer incomeType;

    /**
    * 内容
    */
    @ApiModelProperty(value="内容")
    private String incomeContent;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private Long payMoney;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 支付类型
    */
    @ApiModelProperty(value="支付类型")
    private String payType;

    /**
    * 操作后余额
    */
    @ApiModelProperty(value="操作后余额")
    private Long excess;

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
