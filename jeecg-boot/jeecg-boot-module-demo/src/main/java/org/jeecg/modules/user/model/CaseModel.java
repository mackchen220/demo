package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 达人机构案例表
    */
@ApiModel(value="org-jeecg-modules-user-model-CaseModel")
@Data
public class CaseModel implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 项目描述内容
    */
    @ApiModelProperty(value="项目描述内容")
    private String content;

    /**
    * 案例类别，标题
    */
    @ApiModelProperty(value="案例类别标题")
    private Integer type;

    /**
    * 案例时间
    */
    @ApiModelProperty(value="案例时间")
    private String time;

    /**
    * 项目描述内容
    */
    @ApiModelProperty(value="项目描述内容")
    private String source;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 更新日期
    */
    @ApiModelProperty(value="更新日期")
    private Date updateTime;

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

    private static final long serialVersionUID = 1L;
}
