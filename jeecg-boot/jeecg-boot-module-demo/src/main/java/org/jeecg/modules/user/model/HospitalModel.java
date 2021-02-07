package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 机构(医院)表
    */
@ApiModel(value="org-jeecg-modules-user-model-HospitalModel")
@Data
public class HospitalModel implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    /**
    * 医院名称
    */
    @ApiModelProperty(value="医院名称")
    private String name;

    /**
    * 医院简介
    */
    @ApiModelProperty(value="医院简介")
    private String content;

    /**
    * 医院图标图片
    */
    @ApiModelProperty(value="医院图标图片")
    private String imageUrl;

    /**
    * 医院营业执照
    */
    @ApiModelProperty(value="医院营业执照")
    private String businessLicense;

    /**
    * 医院执业许可证
    */
    @ApiModelProperty(value="医院执业许可证")
    private String licence;

    /**
    * 视频地址
    */
    @ApiModelProperty(value="视频地址")
    private String videoUrl;

    /**
    * 签署的协议的超链接
    */
    @ApiModelProperty(value="签署的协议的超链接")
    private String contractUrl;

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
    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}