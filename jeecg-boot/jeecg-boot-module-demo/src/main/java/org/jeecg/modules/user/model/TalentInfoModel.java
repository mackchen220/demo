package org.jeecg.modules.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
    * 达人信息表
    */
@ApiModel(value="org-jeecg-modules-user-model-TalentInfoModel")
public class TalentInfoModel {
    @ApiModelProperty(value="")
    private String id;

    @ApiModelProperty(value="")
    private String userId;

    /**
    * 效果评分
    */
    @ApiModelProperty(value="效果评分")
    private String effect;

    /**
    * 态度评分
    */
    @ApiModelProperty(value="态度评分")
    private String attitude;

    /**
    * 价格评分
    */
    @ApiModelProperty(value="价格评分")
    private String price;

    /**
    * 综合评分
    */
    @ApiModelProperty(value="综合评分")
    private String averageScore;

    /**
    * 保证金
    */
    @ApiModelProperty(value="保证金")
    private Long deposit;

    @ApiModelProperty(value="")
    private String name;

    /**
    * 身份证号码
    */
    @ApiModelProperty(value="身份证号码")
    private String idCard;

    /**
    * 城市
    */
    @ApiModelProperty(value="城市")
    private String city;

    /**
    * 从业年限
    */
    @ApiModelProperty(value="从业年限")
    private String time;

    /**
    * 是否身份验证0-已验证,1-未验证
    */
    @ApiModelProperty(value="是否身份验证0-已验证,1-未验证")
    private Integer idStatus;

    /**
    * 是否完成验证0-已完成,1-未完成
    */
    @ApiModelProperty(value="是否完成验证0-已完成,1-未完成")
    private Integer authenticated;

    /**
    * 是否签署达人协议 0已签署,1-未签署
    */
    @ApiModelProperty(value="是否签署达人协议 0已签署,1-未签署")
    private Integer contractStatus;

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
    private Boolean delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Integer authenticated) {
        this.authenticated = authenticated;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
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

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}