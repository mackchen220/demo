package org.jeecg.modules.webAdmin.search.entity;

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
 * @Description: 热搜词管理
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
@TableName("tb_hot_search")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_hot_search对象", description="热搜词管理")
public class AdminHotSearch implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**热搜标题*/
	@Excel(name = "热搜标题", width = 15)
    @ApiModelProperty(value = "热搜标题")
    private java.lang.String title;
	/**热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜*/
	@Excel(name = "热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜", width = 15)
    @ApiModelProperty(value = "热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜")
    private java.lang.Integer contentType;
	/**搜索次数*/
	@Excel(name = "搜索次数", width = 15)
    @ApiModelProperty(value = "搜索次数")
    private java.lang.Integer searchNum;
	/**推荐标识0-正常,1-推荐*/
	@Excel(name = "推荐标识0-正常,1-推荐", width = 15)
    @ApiModelProperty(value = "推荐标识0-正常,1-推荐")
    private java.lang.Integer recommend;
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
