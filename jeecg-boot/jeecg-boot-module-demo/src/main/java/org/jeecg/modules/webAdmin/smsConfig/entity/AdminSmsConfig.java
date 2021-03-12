package org.jeecg.modules.webAdmin.smsConfig.entity;

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
 * @Description: 短信配置
 * @Author: jeecg-boot
 * @Date:   2021-03-12
 * @Version: V1.0
 */
@Data
@TableName("tb_sms_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_sms_config对象", description="短信配置")
public class AdminSmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**短信接口地址*/
	@Excel(name = "短信接口地址", width = 15)
    @ApiModelProperty(value = "短信接口地址")
    private java.lang.String sendApi;
	/**接口账号 (必填)（企业登录名）*/
	@Excel(name = "接口账号 (必填)（企业登录名）", width = 15)
    @ApiModelProperty(value = "接口账号 (必填)（企业登录名）")
    private java.lang.String loginName;
	/**短信内容模板*/
	@Excel(name = "短信内容模板", width = 15)
    @ApiModelProperty(value = "短信内容模板")
    private java.lang.String sendContent;
	/**密码 (必填)（企业账号对应密码）*/
	@Excel(name = "密码 (必填)（企业账号对应密码）", width = 15)
    @ApiModelProperty(value = "密码 (必填)（企业账号对应密码）")
    private java.lang.String password;
	/**签名（可为空）*/
	@Excel(name = "签名（可为空）", width = 15)
    @ApiModelProperty(value = "签名（可为空）")
    private java.lang.String signName;
	/**计费套餐类型 (必填)2 行业套餐 3 为政务套餐*/
	@Excel(name = "计费套餐类型 (必填)2 行业套餐 3 为政务套餐", width = 15)
    @ApiModelProperty(value = "计费套餐类型 (必填)2 行业套餐 3 为政务套餐")
    private java.lang.String feeType;
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
}
