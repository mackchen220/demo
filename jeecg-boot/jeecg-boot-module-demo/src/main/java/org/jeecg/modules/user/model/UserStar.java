package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 用户点赞收藏朋友圈表
    */
@ApiModel(value="org-jeecg-modules-user-model-UserStar")
@Data
public class UserStar implements Serializable {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    @ApiModelProperty(value="")
    private String communityId;

    /**
    *  是否点赞
    */
    @ApiModelProperty(value="是否点赞")
    private String good;

    /**
     *  内容分类 1 朋友圈 2 活动 3课程
     */
    @ApiModelProperty(value="内容分类 1 朋友圈 2 活动 3课程")
    private String pageType;
    /**
     *  是否收藏
     */
    @ApiModelProperty(value="是否收藏")
    private String star;


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
