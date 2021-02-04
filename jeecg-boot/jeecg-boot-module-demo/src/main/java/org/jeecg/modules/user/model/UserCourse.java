package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 用户课程表
    */
@Data
@ApiModel(value="org-jeecg-modules-index-model-TbUserCourse")
public class UserCourse {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 课程id
    */
    @ApiModelProperty(value="课程id")
    private String courseId;

    /**
    * 开始时间
    */
    @ApiModelProperty(value="开始时间")
    private String beginTime;

    /**
    * 结束时间
    */
    @ApiModelProperty(value="结束时间")
    private String endTime;

    /**
    * f价格
    */
    @ApiModelProperty(value="f价格")
    private Long price;

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
