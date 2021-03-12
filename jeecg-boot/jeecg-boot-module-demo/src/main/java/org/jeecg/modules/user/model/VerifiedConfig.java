package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 实名认证配置表
    */
@ApiModel(value="org-jeecg-modules-user-model-VerifiedConfig")
@Data
public class VerifiedConfig implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 接口地址
    */
    @ApiModelProperty(value="接口地址")
    private String sendApi;

    /**
    * appId
    */
    @ApiModelProperty(value="appId")
    private String appId;

    /**
    * appKey
    */
    @ApiModelProperty(value="appKey")
    private String appKey;

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