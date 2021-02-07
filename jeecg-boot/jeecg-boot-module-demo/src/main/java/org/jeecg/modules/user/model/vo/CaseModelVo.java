package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 达人机构案例表
    */
@ApiModel(value="org-jeecg-modules-user-model-CaseModel")
@Data
public class CaseModelVo implements Serializable {
    @ApiModelProperty(value="")
    private String id;


    /**
    * 项目描述内容
    */
    @ApiModelProperty(value="项目描述内容",required = true)
    private String content;

    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址",required = true)
    private String imgaeUrl;

    /**
    * 案例类别，标题
    */
    @ApiModelProperty(value="案例类别标题")
    private String type;

    /**
    * 案例时间
    */
    @ApiModelProperty(value="案例时间")
    private String time;

    /**
    * 项目来源
    */
    @ApiModelProperty(value="来源")
    private String source;



    private static final long serialVersionUID = 1L;
}
