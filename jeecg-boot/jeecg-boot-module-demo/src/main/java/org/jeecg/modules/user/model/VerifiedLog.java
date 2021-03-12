package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 实名认证记录
    */
@ApiModel(value="org-jeecg-modules-user-model-VerifiedLog")
@Data
public class VerifiedLog implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 标题
    */
    @ApiModelProperty(value="标题")
    private String name;

    /**
    * 内容
    */
    @ApiModelProperty(value="内容")
    private String idNum;

    /**
    * 省份
    */
    @ApiModelProperty(value="省份")
    private String province;

    /**
    * 市区
    */
    @ApiModelProperty(value="市区")
    private String city;

    /**
    * 县区
    */
    @ApiModelProperty(value="县区")
    private String country;

    /**
    * 生日，格式是yyyyMMdd
    */
    @ApiModelProperty(value="生日，格式是yyyyMMdd")
    private String birthday;

    /**
    * 性别：1：男、2：女
    */
    @ApiModelProperty(value="性别：1：男、2：女")
    private String gender;

    /**
    * 年龄
    */
    @ApiModelProperty(value="年龄")
    private String age;

    /**
    * 实名接口返回的备注，例：一致
    */
    @ApiModelProperty(value="实名接口返回的备注，例：一致")
    private String remark;

    /**
    * 身份证正面
    */
    @ApiModelProperty(value="身份证正面")
    private String imageFirst;

    /**
    * 身份证反面"
    */
    @ApiModelProperty(value="身份证反面'")
    private String imageBack;

    /**
    * 审核状态 0待审核 1 审核未通过2审核通过
    */
    @ApiModelProperty(value="审核状态 0待审核 1 审核未通过2审核通过")
    private Integer checkStatus;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
    * 更新日期
    */
    @ApiModelProperty(value="更新日期")
    private String updateTime;

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