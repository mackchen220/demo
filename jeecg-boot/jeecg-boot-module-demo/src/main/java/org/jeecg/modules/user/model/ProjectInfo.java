package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 子类项目，材料，杂项详细表
    */
@ApiModel(value="org-jeecg-modules-user-model-ProjectInfo")
@Data
public class ProjectInfo implements Serializable {
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

    private static final long serialVersionUID = 1L;
}
