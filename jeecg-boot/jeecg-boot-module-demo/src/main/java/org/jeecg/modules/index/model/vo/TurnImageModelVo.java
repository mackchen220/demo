package org.jeecg.modules.index.model.vo;

import lombok.Data;

/**
    * 首页轮播图配置vo
    */
@Data
public class TurnImageModelVo {

    /**
    * 图片地址
    */
    private String url;

    /**
    * 跳转地址
    */
    private Long turnUrl;

    /**
    * 标题
    */
    private String title;




}
