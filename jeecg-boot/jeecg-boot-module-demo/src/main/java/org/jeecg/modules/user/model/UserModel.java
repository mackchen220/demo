package org.jeecg.modules.user.model;

import lombok.Data;

@Data
public class UserModel {
    /**
    * 主键
    */
    private String Id;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 昵称
    */
    private String nickName;

    /**
     * 是否实名认证 1是 0否
     */
    private String verified;

    /**
    * 头像
    */
    private String headImage;

    /**
    * VIP等级id
    */
    private String vipId;


    /**
    * 个性签名
    */
    private String sign;

    /**
    * 可提现余额度
    */
    private Long money;

    /**
    * 做删除用，0为有效数据 1无效
    */
    private Integer delFlag;

    /**
    * 禁用 1不禁用 0禁用
    */
    private Integer isDisable;

    /**
    * 密码
    */
    private String password;

    /**
    * 最后一次登录时间
    */
    private String lastLoginTime;

    /**
    * 此次登录时间
    */
    private String loginTime;

    /**
    * 创建时间
    */
    private String createTime;

    private String updateTime;

    /**
    * 最后一次登陆IP
    */
    private String lastLoginIp;

    /**
    * 当前登录ip
    */
    private String loginIp;

    /**
    * 微信号码
    */
    private String wechat;

    /**
    * 1-用户，2-达人，3-机构，4-平台
    */
    private Integer userType;

    /**
    * 代理id
    */
    private String agencyId;

    /**
    * 登录次数
    */
    private String loginTimes;

    /**
    * 1-男,0-女
    */
    private Integer gender;

    /**
    * 出生日期
    */
    private String birthday;

    /**
    * 电话.
    */
    private String phone;

    /**
    * 最后修改密码时间
    */
    private String updPwdTime;

    /**
    * 用户注册域名
    */
    private String registerIp;

    /**
    * 禁用说明
    */
    private String disableRemark;

    /**
     * 创建人
     */
    private String createBy;


    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 是否达人 1是 0不是
     */
    private String isTalent;

    private String province;
    private String city;
    /**
     * 绑定微信的id
     */
    private String weixinId;




}
