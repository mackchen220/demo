package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 用户地址表
    */
@ApiModel(value="org-jeecg-modules-user-model-AddressModel")
@Data
public class AddressModel implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 收获详细地址
    */
    @ApiModelProperty(value="收获详细地址")
    private String address;

    /**
    * 禁用 1不禁用 0禁用
    */
    @ApiModelProperty(value="禁用 0不禁用 1禁用")
    private Integer isDisable;

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

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    private String userId;

    /**
    * 收货手机号
    */
    @ApiModelProperty(value="收货手机号")
    private String phone;

    /**
    * 城市
    */
    @ApiModelProperty(value="城市")
    private String city;

    /**
    * 是否默认地址 0非默认 1默认
    */
    @ApiModelProperty(value="是否默认地址 0非默认 1默认")
    private Integer defaultFlag;

    private static final long serialVersionUID = 1L;
}
