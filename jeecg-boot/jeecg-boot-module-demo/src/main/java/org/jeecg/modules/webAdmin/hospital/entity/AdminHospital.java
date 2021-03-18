package org.jeecg.modules.webAdmin.hospital.entity;

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
 * @Description: 机构管理
 * @Author: jeecg-boot
 * @Date:   2021-03-17
 * @Version: V1.0
 */
@Data
@TableName("tb_hospital")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_hospital对象", description="机构管理")
public class AdminHospital implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**医院名称*/
	@Excel(name = "医院名称", width = 15)
    @ApiModelProperty(value = "医院名称")
    private java.lang.String name;
	/**医院简介*/
	@Excel(name = "医院简介", width = 15)
    @ApiModelProperty(value = "医院简介")
    private java.lang.String content;
	/**医院图标图片*/
	@Excel(name = "医院图标图片", width = 15)
    @ApiModelProperty(value = "医院图标图片")
    private java.lang.String imageUrl;
	/**医院营业执照*/
	@Excel(name = "医院营业执照", width = 15)
    @ApiModelProperty(value = "医院营业执照")
    private java.lang.String businessLicense;
	/**医院执业许可证*/
	@Excel(name = "医院执业许可证", width = 15)
    @ApiModelProperty(value = "医院执业许可证")
    private java.lang.String licence;
	/**视频地址*/
	@Excel(name = "视频地址", width = 15)
    @ApiModelProperty(value = "视频地址")
    private java.lang.String videoUrl;
	/**签署的协议的超链接*/
	@Excel(name = "签署的协议的超链接", width = 15)
    @ApiModelProperty(value = "签署的协议的超链接")
    private java.lang.String contractUrl;
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
	/**机构认证上传人*/
	@Excel(name = "机构认证上传人", width = 15)
    @ApiModelProperty(value = "机构认证上传人")
    private java.lang.String userId;
	/**是否完成验证 0未验证 1已验证*/
	@Excel(name = "是否完成验证 0未验证 1已验证", width = 15)
    @ApiModelProperty(value = "是否完成验证 0未验证 1已验证")
    private java.lang.Integer authenticated;
}
