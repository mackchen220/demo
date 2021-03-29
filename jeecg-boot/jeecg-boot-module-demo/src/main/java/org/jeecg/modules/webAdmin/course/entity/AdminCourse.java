package org.jeecg.modules.webAdmin.course.entity;

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
 * @Description: 课程管理
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
@TableName("tb_course")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_course对象", description="课程管理")
public class AdminCourse implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**title*/
	@Excel(name = "title", width = 15)
    @ApiModelProperty(value = "title")
    private java.lang.String title;
	/**课程地址*/
	@Excel(name = "课程地址", width = 15)
    @ApiModelProperty(value = "课程地址")
    private java.lang.String url;
	/**封面图片*/
	@Excel(name = "封面图片", width = 15)
    @ApiModelProperty(value = "封面图片")
    private java.lang.String image;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
    private java.lang.Integer price;
	/**类型1 -收费,2 免费， 3限时免费*/
	@Excel(name = "类型1 -收费,2 免费， 3限时免费", width = 15)
    @ApiModelProperty(value = "类型1 -收费,2 免费， 3限时免费")
    private java.lang.Integer type;
	/**观看数量*/
	@Excel(name = "观看数量", width = 15)
    @ApiModelProperty(value = "观看数量")
    private java.lang.Integer watchNum;
	/**点赞数量*/
	@Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private java.lang.Integer goodNum;
	/**标识0-完结,1-正在更新*/
	@Excel(name = "标识0-完结,1-正在更新", width = 15)
    @ApiModelProperty(value = "标识0-完结,1-正在更新")
    private java.lang.Integer state;
	/**排序字段*/
	@Excel(name = "排序字段", width = 15)
    @ApiModelProperty(value = "排序字段")
    private java.lang.Integer sort;
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
	/**课程类型 1-文章 2-视频*/
	@Excel(name = "课程类型 1-文章 2-视频", width = 15)
    @ApiModelProperty(value = "课程类型 1-文章 2-视频")
    private java.lang.Integer courseType;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.String city;
	/**收藏数量*/
	@Excel(name = "收藏数量", width = 15)
    @ApiModelProperty(value = "收藏数量")
    private java.lang.Integer starNum;
	/**转发数量*/
	@Excel(name = "转发数量", width = 15)
    @ApiModelProperty(value = "转发数量")
    private java.lang.Integer forwardNum;
	/**内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目*/
	@Excel(name = "内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目", width = 15)
    @ApiModelProperty(value = "内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目")
    private java.lang.Integer contentType;
	/**设为推荐 0 不推荐 1推荐*/
	@Excel(name = "设为推荐 0 不推荐 1推荐", width = 15)
    @ApiModelProperty(value = "设为推荐 0 不推荐 1推荐")
    private java.lang.Integer recommend;
	/**设为封面课程 0 不推荐 1推荐*/
	@Excel(name = "设为封面课程 0 不推荐 1推荐", width = 15)
    @ApiModelProperty(value = "设为封面课程 0 不推荐 1推荐")
    private java.lang.Integer banner;
    /**是否对会员免费 1会员免费 0无优惠*/
    @Excel(name = "是否对会员免费 1会员免费 0无优惠", width = 15)
    @ApiModelProperty(value = "是否对会员免费 1会员免费 0无优惠")
    private java.lang.Integer vipFree;
}
