package org.jeecg.modules.index.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 平台配置表
    */
@ApiModel(value="org-jeecg-modules-user-model-PlatformConfiguration")
@Data
public class PlatformConfiguration implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 配置名称
    */
    @ApiModelProperty(value="配置名称")
    private String configKey;

    /**
    * 配置指
    */
    @ApiModelProperty(value="配置指")
    private String configValue;

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
