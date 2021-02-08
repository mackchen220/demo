package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    *
    */
@ApiModel(value="org-jeecg-modules-user-model-ProjectInfo")
@Data
public class UserProjectVo implements Serializable {


    /**
    * 项目id
    */
    @ApiModelProperty(value="医院id")
    private String hospitalId;

    /**
     * 项目id
     */
    @ApiModelProperty(value="综合评分")
    private String averageScore;


    /**
    * 项目名称
    */
    @ApiModelProperty(value="项目名称")
    private String projectName;

    @ApiModelProperty(value="签约数量")
    private Long num;



    @ApiModelProperty(value="达人id")
    private String talentId;

    /**
    * 最低价格
    */
    @ApiModelProperty(value="最低价格")
    private Long priceLow;

    /**
    * 最高价格
    */
    @ApiModelProperty(value="最高价格")
    private Long priceHigh;


    @ApiModelProperty(value="昵称")
    private String nickName;

    @ApiModelProperty(value="")
    private String userName;


    @ApiModelProperty(value="头像")
    private String headImage;




    private static final long serialVersionUID = 1L;
}
