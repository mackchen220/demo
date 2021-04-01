package org.jeecg.modules.webAdmin.talentHospital.entity;

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
 * @Description: tb_talent_hospital
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Data
@TableName("tb_talent_hospital")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_talent_hospital对象", description="tb_talent_hospital")
public class WebTalentHospital implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**机构医院id*/
	@Excel(name = "机构医院id", width = 15)
    @ApiModelProperty(value = "机构医院id")
    private java.lang.String hospitalId;
    /**机构医院名称*/
    @Excel(name = "机构医院名称", width = 15)
    @ApiModelProperty(value = "机构医院名称")
    private java.lang.String hospitalName;
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
	/**达人id*/
	@Excel(name = "达人id", width = 15)
    @ApiModelProperty(value = "达人id")
    private java.lang.String talentId;
    /**达人昵称*/
    @Excel(name = "达人昵称", width = 15)
    @ApiModelProperty(value = "达人昵称")
    private java.lang.String nickName;
}
