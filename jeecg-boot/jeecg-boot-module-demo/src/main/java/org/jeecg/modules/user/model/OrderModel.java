package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 订单表
    */
@ApiModel(value="org-jeecg-modules-user-model-OrderModel")
@Data
public class OrderModel implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 账号
    */
    @ApiModelProperty(value="账号")
    private String userId;

    /**
    * '1-医美项目,2课程 ,3提现,4购买会员卡
    */
    @ApiModelProperty(value="'1-医美项目,2课程 ,3提现")
    private Integer operationType;

    /**
    * 用户请求金额，单位：分
    */
    @ApiModelProperty(value="用户请求金额，单位：分")
    private String amount;

    /**
    * 充值卡号
    */
    @ApiModelProperty(value="充值卡号")
    private String insideCardNum;

    /**
    * 提现审核人
    */
    @ApiModelProperty(value="提现审核人")
    private String checker;

    /**
    * 审核时间
    */
    @ApiModelProperty(value="审核时间")
    private String checkDt;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 订单状态1：未审核 2：成功 3：失败
    */
    @ApiModelProperty(value="订单状态1：未审核 2：成功 3：失败")
    private Integer status;

    /**
    * 系统根据用户请求金额生成实际支付金额，单位：分
    */
    @ApiModelProperty(value="系统根据用户请求金额生成实际支付金额，单位：分")
    private String payMoney;

    /**
    * 提现银行卡号
    */
    @ApiModelProperty(value="提现银行卡号")
    private String outsideCardNum;

    /**
    * 操作状态0-未确认 1-已确认,未付款 2-成功 3-待评价4-已评价 5-已取消6-拒绝
    */
    @ApiModelProperty(value="操作状态0-未确认 1-已确认,未付款 2-成功 3-待评价4-已评价 5-已取消6-拒绝 ")
    private Integer optStatus;


    /**
     * 支付类型 1 银行卡 2微信 3支付宝
     */
    @ApiModelProperty(value="支付类型 1 银行卡 2微信 3支付宝")
    private Integer payType;

    /**
    * 系统收款银行-收款人
    */
    @ApiModelProperty(value="系统收款银行-收款人")
    private String sysInsName;

    /**
    * 订单简介.
    */
    @ApiModelProperty(value="订单简介.")
    private String content;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;



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

    /**
    * 商品数量
    */
    @ApiModelProperty(value="商品数量")
    private Integer num;

    /**
    * 机构id
    */
    @ApiModelProperty(value="机构id")
    private String hospitalId;

    /**
    * 达人id
    */
    @ApiModelProperty(value="达人id")
    private String talentId;
    /**
     * 会员卡id
     */
    @ApiModelProperty(value="会员卡id")
    private String vipId;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 课程id
     */
    @ApiModelProperty(value="课程id")
    private String courseId;


    /**
     * 收货地址id
     */
    @ApiModelProperty(value="收货地址id")
    private String addressId;



    /**
     * 效果评分
     */
    @ApiModelProperty(value="效果评分")
    private String effect;

    /**
     * 态度评分
     */
    @ApiModelProperty(value="态度评分")
    private String attitude;

    /**
     * 价格评分
     */
    @ApiModelProperty(value="价格评分")
    private String price;

    /**
     * 综合评分
     */
    @ApiModelProperty(value="综合评分")
    private String averageScore;

    /**
     * 是否已评价
     */
    @ApiModelProperty(value="是否已评价")
    private Integer evaluateStatus;




    private static final long serialVersionUID = 1L;
}
