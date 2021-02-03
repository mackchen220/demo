package org.jeecg.modules.index.model;

import lombok.Data;

/**
    * 首页轮播图配置
    */
@Data
public class TurnImageModel {
    private String id;

    /**
    * 图片地址
    */
    private String url;

    /**
    * 跳转地址
    */
    private String turnUrl;

    /**
    * 标题
    */
    private String title;

    /**
    * 排序字段
    */
    private String sort;

    /**
    * 开始时间
    */
    private String beginTime;

    /**
    * 结束时间
    */
    private String endTime;

    /**
    * 是否禁用：0，禁用，1，有效
    */
    private int isDisable;

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


}
