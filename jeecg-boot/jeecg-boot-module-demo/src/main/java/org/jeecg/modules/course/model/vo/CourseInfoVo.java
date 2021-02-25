package org.jeecg.modules.course.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseInfoVo implements Serializable {
    private String id;

    private String courseId;

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
     * 创建时间
     */
    private String createTime;


    private static final long serialVersionUID = 1L;
}
