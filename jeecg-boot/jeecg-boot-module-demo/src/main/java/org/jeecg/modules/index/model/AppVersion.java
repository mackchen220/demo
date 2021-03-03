package org.jeecg.modules.index.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * app升级版本号
    */
@ApiModel(value="org-jeecg-modules-user-model-AppVersion")
@Data
public class AppVersion implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 展示版本号
    */
    @ApiModelProperty(value="展示版本号")
    private String showVersion;

    /**
    * 更新版本号
    */
    @ApiModelProperty(value="更新版本号")
    private String sysVersion;

    /**
    * 更新内容简介
    */
    @ApiModelProperty(value="更新内容简介")
    private String content;

    /**
    * 下载地址
    */
    @ApiModelProperty(value="下载地址")
    private String downloadUrl;

    /**
    * 是否强制更新 0-不用,1-强制更新
    */
    @ApiModelProperty(value="是否强制更新 0-不用,1-强制更新")
    private Integer updateFlag;

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
