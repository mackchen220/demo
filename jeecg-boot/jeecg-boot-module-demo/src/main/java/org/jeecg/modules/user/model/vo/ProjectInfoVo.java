package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 子类项目，材料，杂项详细表
    */
@ApiModel(value="org-jeecg-modules-user-model-ProjectInfo")
@Data
public class ProjectInfoVo implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 项目id
    */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
    * 项目名称
    */
    @ApiModelProperty(value="项目名称")
    private String projectName;


    /**
    * 子项目类型 ，1具体小项目 2 假体材料 3 杂项
    */
    @ApiModelProperty(value="子项目类型 ，1具体小项目 2 假体材料 3 杂项")
    private Integer type;

    /**
    * 最低价格
    */
    @ApiModelProperty(value="最低价格")
    private Long priceLow;

    /**
    * 最高价格
    */
    @ApiModelProperty(value="最高价格")
    private Long priceHigh;




    private static final long serialVersionUID = 1L;
}
