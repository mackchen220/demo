package org.jeecg.modules.webAdmin.vip.entity;

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
 * @Description: 会员设置
 * @Author: jeecg-boot
 * @Date:   2021-03-17
 * @Version: V1.0
 */
@Data
@TableName("tb_vip")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_vip对象", description="会员设置")
public class AdminVip implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**会员名称*/
	@Excel(name = "会员名称", width = 15)
    @ApiModelProperty(value = "会员名称")
    private java.lang.String vipName;
	/**限时价格*/
	@Excel(name = "限时价格", width = 15)
    @ApiModelProperty(value = "限时价格")
    private java.lang.Integer priceLow;
	/**原价格*/
	@Excel(name = "原价格", width = 15)
    @ApiModelProperty(value = "原价格")
    private java.lang.Integer priceHigh;
	/**佣金百分比，例：百分之五十，数据为50*/
	@Excel(name = "佣金百分比，例：百分之五十，数据为50", width = 15)
    @ApiModelProperty(value = "佣金百分比，例：百分之五十，数据为50")
    private java.lang.Integer commission;
	/**奖金区间小*/
	@Excel(name = "奖金区间小", width = 15)
    @ApiModelProperty(value = "奖金区间小")
    private java.lang.Integer bonusHigh;
	/**奖金区间大*/
	@Excel(name = "奖金区间大", width = 15)
    @ApiModelProperty(value = "奖金区间大")
    private java.lang.Integer bonusLow;
	/**整形折扣百分比 例：五折，数据为50*/
	@Excel(name = "整形折扣百分比 例：五折，数据为50", width = 15)
    @ApiModelProperty(value = "整形折扣百分比 例：五折，数据为50")
    private java.lang.Integer discount;
	/**旅游次数*/
	@Excel(name = "旅游次数", width = 15)
    @ApiModelProperty(value = "旅游次数")
    private java.lang.Integer times;
	/**区域分红百分比*/
	@Excel(name = "区域分红百分比", width = 15)
    @ApiModelProperty(value = "区域分红百分比")
    private java.lang.Integer areaCommission;
	/**限时名额数量*/
	@Excel(name = "限时名额数量", width = 15)
    @ApiModelProperty(value = "限时名额数量")
    private java.lang.Integer quotaNum;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date beginTime;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private java.util.Date endTime;
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
	/**vip卡图标*/
	@Excel(name = "vip卡图标", width = 15)
    @ApiModelProperty(value = "vip卡图标")
    private java.lang.String image;
}
