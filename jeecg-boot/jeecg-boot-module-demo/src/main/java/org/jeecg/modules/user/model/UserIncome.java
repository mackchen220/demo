package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户收入、支出日表
 */
@ApiModel(value = "org-jeecg-modules-user-model-UserIncome")
@Data
public class UserIncome implements Serializable {
    @ApiModelProperty(value = "")
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 充值金额，单位：分
     */
    @ApiModelProperty(value = "充值金额，单位：分")
    private Long rechargeMoney;

    /**
     * 推广收入，单位：分
     */
    @ApiModelProperty(value = "推广收入，单位：分")
    private Long extensionMoney;

    /**
     * 医美项目支出，单位：分
     */
    @ApiModelProperty(value = "医美项目支出，单位：分")
    private Long projectMoney;

    /**
     * 课程支出，单位：分
     */
    @ApiModelProperty(value = "课程支出，单位：分")
    private Long courseMoney;

    /**
     * 佣金收入，单位：分
     */
    @ApiModelProperty(value = "佣金收入，单位：分")
    private Long commissionMoney;

    /**
     * 其他收入
     */
    @ApiModelProperty(value = "其他收入")
    private Long otherMoney;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private Long getOutMoney;

    /**
     * 其它支出
     */
    @ApiModelProperty(value = "其它支出")
    private Long otherOutMoney;

    /**
     * 防止数据重复添加唯一索引
     */
    @ApiModelProperty(value = "防止数据重复添加唯一索引")
    private String seqUnique;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 删除标识0-正常,1-已删除
     */
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}