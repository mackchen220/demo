package org.jeecg.modules.community.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 社区朋友圈表
    */
@Data
@ApiModel(value="org-jeecg-modules-community-model-CommunityModel")
public class CommunityModelVo {
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
    private String imageUrl;

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
    private Long starNum;

    /**
    * 点赞数量
    */
    @ApiModelProperty(value="点赞数量")
    private Long goodNum;

    /**
    * 转发数量
    */
    @ApiModelProperty(value="转发数量")
    private Long forwardNum;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;


    /**
     * 观看人数
     */
    @ApiModelProperty(value="观看人数")
    private Long watchNum;


    /**
     * 用户头像
     */
    @ApiModelProperty(value="用户头像")
    private String headImage;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value="用户昵称")
    private String nickName;


    /**
     * 是否收藏 1是0否
     */
    @ApiModelProperty(value="是否收藏 1是0否")
    private String starStatus ;

    /**
     * 是否点赞 1是0否
     */
    @ApiModelProperty(value="是否点赞 1是0否")
    private String goodStatus;

    /**
     * 省
     */
    @ApiModelProperty(value="省")
    private String province;

    /**
     * '详细地址'
     */
    @ApiModelProperty(value="'详细地址'")
    private String address;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private String latitude;

}
