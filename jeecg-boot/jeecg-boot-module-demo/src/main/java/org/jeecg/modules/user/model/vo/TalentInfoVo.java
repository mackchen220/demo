package org.jeecg.modules.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 达人信息表
    */
@Data
public class TalentInfoVo {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 综合评分
    */
    @ApiModelProperty(value="综合评分")
    private String averageScore;

//    /**
//    * 城市
//    */
//    @ApiModelProperty(value="城市")
//    private String city;


    /**
     * 昵称
     */
    @ApiModelProperty(value="昵称")
    private String nickName;


    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String headImage;


    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * 签约机构数量
     */
    @ApiModelProperty(value="签约机构数量")
    private String num;




}
