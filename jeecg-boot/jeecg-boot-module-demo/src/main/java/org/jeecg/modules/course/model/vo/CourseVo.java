package org.jeecg.modules.course.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseVo implements Serializable {
    private String id;

    private String title;

    /**
    * 课程地址
    */
    private String url;

    /**
    * 封面图片
    */
    private String image;

    /**
    * 价格
    */
    private Long price;

    /**
    * 类型1 -收费,2 免费， 3限时免费
    */
    private Integer type;

    /**
    * 观看数量
    */
    private Long watchNum;

    /**
    * 点赞数量
    */
    private Long goodNum;

    /**
    * 标识0-完结,1-正在更新
    */
    private Integer state;

    /**
     * 创建时间
     */
    private String createTime;
//    /**
//     * 课程类型 1-文章 2-视频
//     */
//    private Integer courseType;

    /**
     * 用户是否已购买
     */
    private Integer byState;



    /**
     * 城市
     */
    private String city;

    /**
     *
     */
    private String headImage;

    /**
     *
     */
    private String nickName;

    /**
     * 个性签名
     */
    private String userSign;


    /**
     * 作者
     */
    private String userId;


    /**
     * 课程简介
     */
    private String content;


    /**
     * 课程详细信息
     */
    private String courseInfo;



    /**
     * 收藏数量
     */
    private Long starNum;
    /**
     * 转发数量
     */
    private Long forwardNum;


    /**
     * 是否收藏 1是0否
     */
    @ApiModelProperty(value="是否收藏 1是0否")
    private String starStatus ;

    /**
     * 是否点赞 1是0否
     */
    @ApiModelProperty(value="是否点赞 1是0否")
    private String goodStatus;




//    /**
//     * 内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目
//     */
//    @ApiModelProperty(value="内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目")
//    private Integer contentType;
//
//    /**
//     * 设为封面课程 0 不推荐 1推荐
//     */
//    @ApiModelProperty(value="设为封面课程 0 不推荐 1推荐")
//    private Integer banner;
//
//    /**
//     * 设为推荐 0 不推荐 1推荐
//     */
//    @ApiModelProperty(value="设为推荐 0 不推荐 1推荐")
//    private Integer recommend;





    private static final long serialVersionUID = 1L;
}
