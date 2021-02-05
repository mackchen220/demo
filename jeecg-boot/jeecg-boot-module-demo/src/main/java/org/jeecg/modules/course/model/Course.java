package org.jeecg.modules.course.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable {
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
    private Long goodnum;

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

    private static final long serialVersionUID = 1L;
}