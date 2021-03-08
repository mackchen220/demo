package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeiXinModel {

    /**
     * openId
     */
    @ApiModelProperty(value="openId")
    private String openId;

    /**
     * nickName
     */
    @ApiModelProperty(value="nickName")
    private String nickName;

    /**
     * gender
     */
    @ApiModelProperty(value="gender")
    private String gender;

    /**
     * city
     */
    @ApiModelProperty(value="city")
    private String city;

    /**
     * province
     */
    @ApiModelProperty(value="province")
    private String province;

    /**
     * country
     */
    @ApiModelProperty(value="country")
    private String country;



    /**
     *unionId
     */
    @ApiModelProperty(value="unionId")
    private String unionId;


    private static final long serialVersionUID = 1L;
}
