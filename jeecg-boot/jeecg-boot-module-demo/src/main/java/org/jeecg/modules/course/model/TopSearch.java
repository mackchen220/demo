package org.jeecg.modules.course.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TopSearch implements Serializable {
    private String id;

    /**
    * 热搜关键词
    */
    private String topSearch;

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