package org.jeecg.modules.webAdmin.community.entity;

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
 * @Description: 朋友圈
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
@Data
@TableName("tb_community")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_community对象", description="朋友圈")
public class AdminCommunity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    @ApiModelProperty(value = "userId")
    private java.lang.String userId;
    /**用户昵称*/
    @Excel(name = "用户昵称", width = 15)
    @ApiModelProperty(value = "用户昵称")
    private java.lang.String nickName;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private java.lang.String title;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private java.lang.String content;
	/**链接*/
	@Excel(name = "链接", width = 15)
    @ApiModelProperty(value = "链接")
    private java.lang.String url;
	/**图片或者视频地址*/
	@Excel(name = "图片或者视频地址", width = 15)
    @ApiModelProperty(value = "图片或者视频地址")
    private java.lang.String imageUrl;
	/**定位城市*/
	@Excel(name = "定位城市", width = 15)
    @ApiModelProperty(value = "定位城市")
    private java.lang.String city;
    /**省*/
    @Excel(name = "省", width = 15)
    @ApiModelProperty(value = "省")
    private java.lang.String province;
    /**详细地址*/
    @Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
    private java.lang.String address;
	/**朋友圈类型:1 照片 2视频 3课程  4其他  */
	@Excel(name = "朋友圈类型:1 照片 2视频 3课程  4其他  ", width = 15)
    @ApiModelProperty(value = "朋友圈类型:1 照片 2视频 3课程  4其他  ")
    private java.lang.Integer type;
	/**1-用户，2-达人，3-机构，4-平台*/
	@Excel(name = "1-用户，2-达人，3-机构，4-平台", width = 15)
    @ApiModelProperty(value = "1-用户，2-达人，3-机构，4-平台")
    private java.lang.Integer userType;
	/**收藏数量*/
	@Excel(name = "收藏数量", width = 15)
    @ApiModelProperty(value = "收藏数量")
    private java.lang.Integer starNum;
	/**点赞数量*/
	@Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private java.lang.Integer goodNum;
	/**转发数量*/
	@Excel(name = "转发数量", width = 15)
    @ApiModelProperty(value = "转发数量")
    private java.lang.Integer forwardNum;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
    private java.lang.Integer watchNum;
	/**审核状态 0待审核 1 审核未通过2审核通过*/
	@Excel(name = "审核状态 0待审核 1 审核未通过2审核通过", width = 15)
    @ApiModelProperty(value = "审核状态 0待审核 1 审核未通过2审核通过")
    private java.lang.Integer checkStatus;
}
