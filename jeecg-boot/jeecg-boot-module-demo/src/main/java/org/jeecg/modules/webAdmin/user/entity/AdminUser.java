package org.jeecg.modules.webAdmin.user.entity;

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
 * @Description: tb_user
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Data
@TableName("tb_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_user对象", description="tb_user")
public class AdminUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private java.lang.String userName;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
    private java.lang.String nickName;
	/**头像*/
	@Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
    private java.lang.String headImage;
	/**VIP等级id*/
	@Excel(name = "VIP等级id", width = 15)
    @ApiModelProperty(value = "VIP等级id")
    private java.lang.Integer vipId;
    /**VIP等级*/
    @Excel(name = "VIP等级", width = 15)
    @ApiModelProperty(value = "VIP等级")
    private java.lang.String vipName;
	/**个性签名*/
	@Excel(name = "个性签名", width = 15)
    @ApiModelProperty(value = "个性签名")
    private java.lang.String sign;
	/**可提现余额度*/
	@Excel(name = "可提现余额度", width = 15)
    @ApiModelProperty(value = "可提现余额度")
    private java.lang.Integer money;
	/**删除标识0-正常,1-已删除*/
	@Excel(name = "删除标识0-正常,1-已删除", width = 15)
    @ApiModelProperty(value = "删除标识0-正常,1-已删除")
    private java.lang.Integer delFlag;
	/**禁用 1不禁用 0禁用*/
	@Excel(name = "禁用 1不禁用 0禁用", width = 15)
    @ApiModelProperty(value = "禁用 1不禁用 0禁用")
    private java.lang.Integer isDisable;
	/**密码*/
	@Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "密码")
    private java.lang.String password;
	/**最后一次登录时间*/
	@Excel(name = "最后一次登录时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后一次登录时间")
    private java.util.Date lastLoginTime;
	/**此次登录时间*/
	@Excel(name = "此次登录时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "此次登录时间")
    private java.util.Date loginTime;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**最后一次登陆IP*/
	@Excel(name = "最后一次登陆IP", width = 15)
    @ApiModelProperty(value = "最后一次登陆IP")
    private java.lang.String lastLoginIp;
	/**当前登录ip*/
	@Excel(name = "当前登录ip", width = 15)
    @ApiModelProperty(value = "当前登录ip")
    private java.lang.String loginIp;
	/**微信号码*/
	@Excel(name = "微信号码", width = 15)
    @ApiModelProperty(value = "微信号码")
    private java.lang.String wechat;
	/**1-用户，2-达人，3-机构，4-平台*/
	@Excel(name = "1-用户，2-达人，3-机构，4-平台", width = 15)
    @ApiModelProperty(value = "1-用户，2-达人，3-机构，4-平台")
    private java.lang.Integer userType;
	/**代理id*/
	@Excel(name = "代理id", width = 15)
    @ApiModelProperty(value = "代理id")
    private java.lang.Integer agencyId;
	/**登录次数*/
	@Excel(name = "登录次数", width = 15)
    @ApiModelProperty(value = "登录次数")
    private java.lang.Integer loginTimes;
	/**1-男,0-女*/
	@Excel(name = "1-男,0-女", width = 15)
    @ApiModelProperty(value = "1-男,0-女")
    private java.lang.Integer gender;
	/**出生日期*/
	@Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private java.util.Date birthday;
	/**电话.*/
	@Excel(name = "电话.", width = 15)
    @ApiModelProperty(value = "电话.")
    private java.lang.String phone;
	/**最后修改密码时间*/
	@Excel(name = "最后修改密码时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后修改密码时间")
    private java.util.Date updPwdTime;
	/**用户注册域名*/
	@Excel(name = "用户注册域名", width = 15)
    @ApiModelProperty(value = "用户注册域名")
    private java.lang.String registerIp;
	/**禁用说明*/
	@Excel(name = "禁用说明", width = 15)
    @ApiModelProperty(value = "禁用说明")
    private java.lang.String disableRemark;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**邀请码*/
	@Excel(name = "邀请码", width = 15)
    @ApiModelProperty(value = "邀请码")
    private java.lang.String inviteCode;
	/**是否达人，0不是 1是*/
	@Excel(name = "是否达人，0不是 1是", width = 15)
    @ApiModelProperty(value = "是否达人，0不是 1是")
    private java.lang.Integer isTalent;
    /**是否已实名，0不是 1是*/
    @Excel(name = "是否已实名，0不是 1是", width = 15)
    @ApiModelProperty(value = "是否已实名，0不是 1是")
    private java.lang.Integer verified;
	/**省份*/
	@Excel(name = "省份", width = 15)
    @ApiModelProperty(value = "省份")
    private java.lang.String province;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.String city;
	/**绑定微信的id*/
	@Excel(name = "绑定微信的id", width = 15)
    @ApiModelProperty(value = "绑定微信的id")
    private java.lang.String weixinId;
}
