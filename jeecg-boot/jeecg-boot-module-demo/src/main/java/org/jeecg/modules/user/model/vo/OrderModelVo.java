package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 订单表
    */
@ApiModel(value="org-jeecg-modules-user-model-OrderModel")
@Data
public class OrderModelVo implements Serializable {
    @ApiModelProperty(value="")
    private String id;

//    /**
//    * 账号
//    */
//    @ApiModelProperty(value="账号")
//    private String userId;

    /**
    * '1-医美项目,2课程 ,3提现
    */
    @ApiModelProperty(value="'1-医美项目,2课程 ,3提现")
    private Integer operationType;
//
//    /**
//    * 用户请求金额，单位：分
//    */
//    @ApiModelProperty(value="用户请求金额，单位：分")
//    private String amount;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;


    /**
    * 系统根据用户请求金额生成实际支付金额，单位：分
    */
    @ApiModelProperty(value="系统根据用户请求金额生成实际支付金额，单位：分")
    private String payMoney;

    /**
    * 操作状态0-未确认 1-已确认 2-成功 3-已取消 4-锁定 5-恢复 6-拒绝
    */
    @ApiModelProperty(value="操作状态0-未确认 1-已确认 2-成功 3-已取消 4-锁定 5-恢复 6-拒绝 ")
    private Integer optStatus;

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
    * 商品数量
    */
    @ApiModelProperty(value="商品数量")
    private Integer num;

    /**
    * 机构id
    */
    @ApiModelProperty(value="机构id")
    private String hospitalId;

//    /**
//    * 达人id
//    */
//    @ApiModelProperty(value="达人id")
//    private String talentId;


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
     * 会员卡id
     */
    @ApiModelProperty(value="会员卡id")
    private String vipId;
    /**
     * 课程封面
     */
    @ApiModelProperty(value="课程封面")
    private String courseImage;


    /**
     * 项目封面
     */
    @ApiModelProperty(value="项目封面")
    private String projectImage;


    /**
     * 会员图片
     */
    @ApiModelProperty(value="会员图片")
    private String vipImage;

    private static final long serialVersionUID = 1L;
}
