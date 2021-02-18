package org.jeecg.modules.course.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Activity implements Serializable {
    private String id;

    /**
    * 活动标题
    */
    private String title;

    /**
    * 活动地址
    */
    private String url;

    /**
    * 封面图片
    */
    private String image;

    /**
    * 参与数量
    */
    private Long joinNum;

    /**
    * 点赞数量
    */
    private Long goodNum;

    /**
    * 标识0-完结,1-正在更新
    */
    private Integer state;

    /**
    * 排序字段
    */
    private Long sort;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 更新日期
    */
    private String updateTime;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 更新人
    */
    private String updateBy;

    /**
    * 删除标识0-正常,1-已删除
    */
    private Integer delFlag;

    /**
     * 城市
     */
    private String city;

    /**
     * 收藏数量
     */
    private Long starNum;

    /**
     * 观看数量
     */
    private Long watchNum;

    /**
     * 转发数量
     */
    private Long forwardNum;

    private static final long serialVersionUID = 1L;
}