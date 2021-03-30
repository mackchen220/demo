package org.jeecg.modules.webAdmin.talentInfo.entity;

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
 * @Description: tb_talent_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Data
@TableName("tb_talent_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_talent_info对象", description="tb_talent_info")
public class AdminTalentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**userId*/
	@Excel(name = "userId", width = 15)
    @ApiModelProperty(value = "userId")
    private java.lang.String userId;
	/**效果评分*/
	@Excel(name = "效果评分", width = 15)
    @ApiModelProperty(value = "效果评分")
    private java.math.BigDecimal effect;
	/**态度评分*/
	@Excel(name = "态度评分", width = 15)
    @ApiModelProperty(value = "态度评分")
    private java.math.BigDecimal attitude;
	/**价格评分*/
	@Excel(name = "价格评分", width = 15)
    @ApiModelProperty(value = "价格评分")
    private java.math.BigDecimal price;
	/**综合评分*/
	@Excel(name = "综合评分", width = 15)
    @ApiModelProperty(value = "综合评分")
    private java.math.BigDecimal averageScore;
	/**保证金*/
	@Excel(name = "保证金", width = 15)
    @ApiModelProperty(value = "保证金")
    private java.lang.Integer deposit;
	/**name*/
	@Excel(name = "name", width = 15)
    @ApiModelProperty(value = "name")
    private java.lang.String name;
	/**身份证号码*/
	@Excel(name = "身份证号码", width = 15)
    @ApiModelProperty(value = "身份证号码")
    private java.lang.String idCard;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.String city;
	/**从业年限*/
	@Excel(name = "从业年限", width = 15)
    @ApiModelProperty(value = "从业年限")
    private java.lang.String time;
	/**是否身份验证0-已验证,1-未验证*/
	@Excel(name = "是否身份验证0-已验证,1-未验证", width = 15)
    @ApiModelProperty(value = "是否身份验证0-已验证,1-未验证")
    private java.lang.Integer idStatus;
	/**是否完成验证0-已完成,1-未完成*/
	@Excel(name = "是否完成验证0-已完成,1-未完成", width = 15)
    @ApiModelProperty(value = "是否完成验证0-已完成,1-未完成")
    private java.lang.Integer authenticated;
	/**是否签署达人协议 0已签署,1-未签署*/
	@Excel(name = "是否签署达人协议 0已签署,1-未签署", width = 15)
    @ApiModelProperty(value = "是否签署达人协议 0已签署,1-未签署")
    private java.lang.Integer contractStatus;
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
	/**签约机构数量*/
	@Excel(name = "签约机构数量", width = 15)
    @ApiModelProperty(value = "签约机构数量")
    private java.lang.Integer num;
	/**接单量*/
	@Excel(name = "接单量", width = 15)
    @ApiModelProperty(value = "接单量")
    private java.lang.Integer orderNum;
	/**咨询量*/
	@Excel(name = "咨询量", width = 15)
    @ApiModelProperty(value = "咨询量")
    private java.lang.Integer advisoryNum;
	/**点赞量*/
	@Excel(name = "点赞量", width = 15)
    @ApiModelProperty(value = "点赞量")
    private java.lang.Integer likeNum;
}
