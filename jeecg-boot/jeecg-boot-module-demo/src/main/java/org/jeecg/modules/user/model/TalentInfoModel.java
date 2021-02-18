package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 达人信息表
    */
@Data
@ApiModel(value="org-jeecg-modules-user-model-TalentInfoModel")
public class TalentInfoModel {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

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
    * 保证金
    */
    @ApiModelProperty(value="保证金")
    private Long deposit;

    @ApiModelProperty(value="")
    private String name;

    /**
    * 身份证号码
    */
    @ApiModelProperty(value="身份证号码")
    private String idCard;

    /**
    * 城市
    */
    @ApiModelProperty(value="城市")
    private String city;

    /**
    * 从业年限
    */
    @ApiModelProperty(value="从业年限")
    private String time;

    /**
    * 是否身份验证0-已验证,1-未验证
    */
    @ApiModelProperty(value="是否身份验证0-已验证,1-未验证")
    private Integer idStatus;

    /**
    * 是否完成验证0-已完成,1-未完成
    */
    @ApiModelProperty(value="是否完成验证0-已完成,1-未完成")
    private Integer authenticated;

    /**
    * 是否签署达人协议 0已签署,1-未签署
    */
    @ApiModelProperty(value="是否签署达人协议 0已签署,1-未签署")
    private Integer contractStatus;

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
    private Boolean delFlag;

    /**
     * 签约机构数量
     */
    @ApiModelProperty(value="签约机构数量")
    private String num;

    /**
     * 接单数量
     */
    @ApiModelProperty(value="接单数量")
    private Integer orderNum;

    /**
     * 咨询数量
     */
    @ApiModelProperty(value="咨询数量")
    private String advisoryNum;

    /**
     * 点赞数量
     */
    @ApiModelProperty(value="点赞数量")
    private String likeNum;

}
