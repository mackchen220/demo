package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 关注表
    */
@Data
@ApiModel(value="org-jeecg-modules-user-model-UserFocusModel")
public class UserFocusModel {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 关注人
    */
    @ApiModelProperty(value="关注人")
    private String focusUserId;

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


}
