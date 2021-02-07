package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 机构(医院)表
    */
@ApiModel(value="org-jeecg-modules-user-model-HospitalModel")
@Data
public class HospitalVo implements Serializable {
    /**
     * 医院id
     */
    @ApiModelProperty(value="")
    private String hospitalId;
    /**
    * 医院名称
    */
    @ApiModelProperty(value="医院名称")
    private String name;

    /**
     * 医院图标图片
     */
    @ApiModelProperty(value="医院图标图片")
    private String imageUrl;

    private static final long serialVersionUID = 1L;
}
