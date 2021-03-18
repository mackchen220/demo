package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserModelVo {
    /**
    * 主键
    */
    private String Id;

    @ApiModelProperty(value="签约数量")
    private Long num;


    /**
     * 项目id
     */
    @ApiModelProperty(value="综合评分")
    private String averageScore;

    @ApiModelProperty(value="")
    private String userName;
    /**
    * 昵称
    */
    private String nickName;

    /**
    * 头像
    */
    private String headImage;


}
