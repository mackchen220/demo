package org.jeecg.modules.Community.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 社区朋友圈表
    */
@Data
@ApiModel(value="org-jeecg-modules-Community-model-CommunityModel")
public class CommunityModel {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 标题
    */
    @ApiModelProperty(value="标题")
    private String title;

    /**
    * 内容
    */
    @ApiModelProperty(value="内容")
    private String content;

    /**
    * 链接
    */
    @ApiModelProperty(value="链接")
    private String url;

    /**
    * 图片或者视频地址
    */
    @ApiModelProperty(value="图片或者视频地址")
    private String imageurl;

    /**
    * 图片地址
    */
    @ApiModelProperty(value="图片地址")
    private String city;

    /**
    * 朋友圈类型:1 照片 2视频 3课程  4其他
    */
    @ApiModelProperty(value="朋友圈类型:1 照片 2视频 3课程  4其他  ")
    private Integer type;

    /**
    * 1-用户，2-达人，3-机构，4-平台
    */
    @ApiModelProperty(value="1-用户，2-达人，3-机构，4-平台")
    private Integer userType;

    /**
    * 收藏数量
    */
    @ApiModelProperty(value="收藏数量")
    private Long starnum;

    /**
    * 点赞数量
    */
    @ApiModelProperty(value="点赞数量")
    private Long goodnum;

    /**
    * 转发数量
    */
    @ApiModelProperty(value="转发数量")
    private Long forwardnum;

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
    private Boolean delFlag;


}
