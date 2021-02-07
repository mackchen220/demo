package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * 项目（项目大类）表
    */
@ApiModel(value="org-jeecg-modules-user-model-Project")
@Data
public class ProjectVo implements Serializable {
    @ApiModelProperty(value="")
    private String id;
    /**
    * 项目名称
    */
    @ApiModelProperty(value="项目名称")
    private String name;


    private static final long serialVersionUID = 1L;
}
