package org.jeecg.modules.index.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
    * 课程表
    */
@ApiModel(value="org-jeecg-modules-index-model-CourseModel")
public class CourseModel {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String title;

    /**
    * 课程地址
    */
    @ApiModelProperty(value="课程地址")
    private String url;

    /**
    * 封面图片
    */
    @ApiModelProperty(value="封面图片")
    private String image;

    /**
    * 价格
    */
    @ApiModelProperty(value="价格")
    private Long price;

    /**
    * 类型1 -收费,2 免费， 3限时免费
    */
    @ApiModelProperty(value="类型1 -收费,2 免费， 3限时免费")
    private Boolean type;

    /**
    * 观看数量
    */
    @ApiModelProperty(value="观看数量")
    private Long watchNum;

    /**
    * 点赞数量
    */
    @ApiModelProperty(value="点赞数量")
    private Long goodnum;

    /**
    * 标识0-完结,1-正在更新
    */
    @ApiModelProperty(value="标识0-完结,1-正在更新")
    private Integer state;

    /**
    * 排序字段
    */
    @ApiModelProperty(value="排序字段")
    private Long sort;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Long getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(Long watchNum) {
        this.watchNum = watchNum;
    }

    public Long getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(Long goodnum) {
        this.goodnum = goodnum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}