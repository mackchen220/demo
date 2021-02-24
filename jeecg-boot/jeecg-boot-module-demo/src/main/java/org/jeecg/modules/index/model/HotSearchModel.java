package org.jeecg.modules.index.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 热搜表
    */
@ApiModel(value="org-jeecg-modules-user-model-HotSearchModel")
@Data
public class HotSearchModel implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 热搜标题
    */
    @ApiModelProperty(value="热搜标题")
    private String title;

    /**
    * 热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜
    */
    @ApiModelProperty(value="热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜")
    private Integer contentType;

    /**
    * 搜索次数
    */
    @ApiModelProperty(value="搜索次数")
    private Long searchNum;

    /**
    * 推荐标识0-正常,1-推荐
    */
    @ApiModelProperty(value="推荐标识0-正常,1-推荐")
    private Integer recommend;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Integer createTime;

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
