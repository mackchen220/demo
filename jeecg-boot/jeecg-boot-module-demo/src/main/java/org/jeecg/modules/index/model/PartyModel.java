package org.jeecg.modules.index.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@ApiModel(value="org-jeecg-modules-user-model-PartyModel")
@Data
public class PartyModel implements Serializable {
    @ApiModelProperty(value="")
    private String id;

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
    * 派对标题
    */
    @ApiModelProperty(value="派对标题")
    private String title;

    /**
    * 封面图
    */
    @ApiModelProperty(value="封面图")
    private String image;

    /**
    * 简介
    */
    @ApiModelProperty(value="简介")
    private String profiles;

    /**
    * 观看数量
    */
    @ApiModelProperty(value="观看数量")
    private Long watchNum;

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
     * 城市
     */
    @ApiModelProperty(value="城市")
    private String city;

    /**
    * 价格
    */
    @ApiModelProperty(value="价格")
    private String price;


    /**
     * 原价格
     */
    @ApiModelProperty(value="原价格")
    private String oldPrice;


    /**
     * 剩余名额
     */
    @ApiModelProperty(value="剩余名额")
    private Integer nextNum;


    /**
    * 名额
    */
    @ApiModelProperty(value="名额")
    private Integer num;

    /**
    * 发起人id、
    */
    @ApiModelProperty(value="发起人id、")
    private String userId;

    /**
    * 微信群二维码
    */
    @ApiModelProperty(value="微信群二维码")
    private String qrCode;

    /**
    * 开始时间
    */
    @ApiModelProperty(value="开始时间")
    private String startTime;

    /**
    * 结束时间
    */
    @ApiModelProperty(value="结束时间")
    private String endTime;

    /**
    * 活动地址
    */
    @ApiModelProperty(value="活动地址")
    private String address;

    /**
    * 活动详情图片
    */
    @ApiModelProperty(value="活动详情图片")
    private String detailImgae;

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
