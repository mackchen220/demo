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
public class ExtensionVo implements Serializable {


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
     * 时间
     */
    @ApiModelProperty(value="时间")
    private String createTime;

    /**
     * vip名称
     */
    @ApiModelProperty(value="vip名称")
    private String vipName;


    /**
    * 日收入
    */
    @ApiModelProperty(value="日收入")
    private Long dayMoney;

    /**
    * 月收入
    */
    @ApiModelProperty(value="月收入")
    private Long monthMoney;

    /**
     * 月收入
     */
    @ApiModelProperty(value="总收入")
    private Long totelMoney;


    private static final long serialVersionUID = 1L;
}
