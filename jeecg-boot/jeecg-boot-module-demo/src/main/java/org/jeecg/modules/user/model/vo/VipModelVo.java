package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 会员表
    */
@ApiModel(value="org-jeecg-modules-user-model-VipModel")
@Data
public class VipModelVo implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String vipName;

    @ApiModelProperty(value="vip卡图片")
    private String image;


    /**
    * 限时价格
    */
    @ApiModelProperty(value="限时价格")
    private Long priceLow;

    /**
    * 原价格
    */
    @ApiModelProperty(value="原价格")
    private Long priceHigh;

    /**
    * 佣金百分比，例：百分之五十，数据为50
    */
    @ApiModelProperty(value="佣金百分比，例：百分之五十，数据为50")
    private Long commission;

    /**
    * 奖金区间小
    */
    @ApiModelProperty(value="奖金区间小")
    private Long bonusHigh;

    /**
    * 奖金区间大
    */
    @ApiModelProperty(value="奖金区间大")
    private Long bonusLow;

    /**
    * 整形折扣百分比 例：五折，数据为50
    */
    @ApiModelProperty(value="整形折扣百分比 例：五折，数据为50")
    private Long discount;

    /**
    * 旅游次数
    */
    @ApiModelProperty(value="旅游次数")
    private Long times;

    /**
    * 区域分红百分比
    */
    @ApiModelProperty(value="区域分红百分比")
    private Long areaCommission;

    /**
    * 限时名额数量
    */
    @ApiModelProperty(value="限时名额数量")
    private Long quotaNum;

    /**
    * 开始时间
    */
    @ApiModelProperty(value="开始时间")
    private String beginTime;

    /**
    * 结束日期
    */
    @ApiModelProperty(value="结束日期")
    private String endTime;



    private static final long serialVersionUID = 1L;
}
