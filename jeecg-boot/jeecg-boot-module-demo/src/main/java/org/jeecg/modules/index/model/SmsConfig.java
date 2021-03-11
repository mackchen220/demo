package org.jeecg.modules.index.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 短信配置表
    */
@ApiModel(value="org-jeecg-modules-user-model-SmsConfig")
@Data
public class SmsConfig implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 接口账号 (必填)（企业登录名）
    */
    @ApiModelProperty(value="接口账号 (必填)（企业登录名）")
    private String loginName;

    /**
    * 密码 (必填)（企业账号对应密码）
    */
    @ApiModelProperty(value="密码 (必填)（企业账号对应密码）")
    private String password;

    /**
    * 签名（可为空）
    */
    @ApiModelProperty(value="签名（可为空）")
    private String signName;
    /**
     * 短信接口地址
     */
    @ApiModelProperty(value="短信接口地址）")
    private String sendApi;

    /**
     * 短信内容模板
     */
    @ApiModelProperty(value="短信内容模板）")
    private String sendContent;

    /**
    * 计费套餐类型 (必填)2 行业套餐 3 为政务套餐
    */
    @ApiModelProperty(value="计费套餐类型 (必填)2 行业套餐 3 为政务套餐")
    private String feeType;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 更新日期
    */
    @ApiModelProperty(value="更新日期")
    private Date updateTime;

    /**
    * 创建人
    */
    @ApiModelProperty(value="创建人")
    private String createBy;

    /**
    * 更新人
    */
    @ApiModelProperty(value="更新人")
    private String updateBy;

    /**
    * 删除标识0-正常,1-已删除
    */
    @ApiModelProperty(value="删除标识0-正常,1-已删除")
    private Boolean delFlag;

    private static final long serialVersionUID = 1L;
}
