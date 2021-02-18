package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value="org-jeecg-modules-user-model-AddressModel")
@Data
public class AddressModelVo implements Serializable {
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
