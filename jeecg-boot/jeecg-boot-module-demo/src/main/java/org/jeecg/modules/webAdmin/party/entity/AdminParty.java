package org.jeecg.modules.webAdmin.party.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 社群活动
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
@TableName("tb_party")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_party对象", description="社群活动")
public class AdminParty implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**派对标题*/
	@Excel(name = "派对标题", width = 15)
    @ApiModelProperty(value = "派对标题")
    private java.lang.String title;
	/**封面图*/
	@Excel(name = "封面图", width = 15)
    @ApiModelProperty(value = "封面图")
    private java.lang.String image;
	/**简介*/
	@Excel(name = "简介", width = 15)
    @ApiModelProperty(value = "简介")
    private java.lang.String profiles;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
    private java.lang.String price;
	/**名额*/
	@Excel(name = "名额", width = 15)
    @ApiModelProperty(value = "名额")
    private java.lang.Integer num;
	/**发起人id、*/
	@Excel(name = "发起人id、", width = 15)
    @ApiModelProperty(value = "发起人id、")
    private java.lang.String userId;
	/**微信群二维码*/
	@Excel(name = "微信群二维码", width = 15)
    @ApiModelProperty(value = "微信群二维码")
    private java.lang.String qrCode;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
	/**活动地址*/
	@Excel(name = "活动地址", width = 15)
    @ApiModelProperty(value = "活动地址")
    private java.lang.String address;
	/**活动详情图片*/
	@Excel(name = "活动详情图片", width = 15)
    @ApiModelProperty(value = "活动详情图片")
    private java.lang.String detailImgae;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
    private java.lang.Integer delFlag;
	/**观看数量*/
	@Excel(name = "观看数量", width = 15)
    @ApiModelProperty(value = "观看数量")
    private java.lang.String watchNum;
	/**点赞数量*/
	@Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private java.lang.String goodNum;
	/**收藏数量*/
	@Excel(name = "收藏数量", width = 15)
    @ApiModelProperty(value = "收藏数量")
    private java.lang.Integer starNum;
	/**转发数量*/
	@Excel(name = "转发数量", width = 15)
    @ApiModelProperty(value = "转发数量")
    private java.lang.Integer forwardNum;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.String city;
}
