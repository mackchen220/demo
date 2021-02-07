package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 机构材料项目表
    */
@ApiModel(value="org-jeecg-modules-user-model-HospitalProject")
@Data
public class HospitalProject implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 项目id
    */
    @ApiModelProperty(value="项目id")
    private String projectId;

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
    private Date createTime;

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