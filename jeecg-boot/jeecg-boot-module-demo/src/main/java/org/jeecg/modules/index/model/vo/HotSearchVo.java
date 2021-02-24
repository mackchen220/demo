package org.jeecg.modules.index.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 热搜表
    */
@ApiModel(value="org-jeecg-modules-user-model-HotSearchModel")
@Data
public class HotSearchVo implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 热搜标题
    */
    @ApiModelProperty(value="热搜标题")
    private String title;
//
//    /**
//    * 热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜
//    */
//    @ApiModelProperty(value="热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜")
//    private Integer contentType;

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



    private static final long serialVersionUID = 1L;
}
