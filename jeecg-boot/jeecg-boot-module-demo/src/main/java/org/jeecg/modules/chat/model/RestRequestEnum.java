package org.jeecg.modules.chat.model;

/**
 * @className: RestRequestEnum
 * @description: 腾讯IM请求接口
 * @data: 2021-03-22 13:50
 * @version: V1.0
 */
public enum RestRequestEnum {

    /**
     * Request 请求 URL前缀
     */
    TIM_REGISTER("/v4/im_open_login_svc/account_import", "导入单个帐号"),
    TIM_SEND_P2P_MSG("/v4/openim/sendmsg", "单发单聊消息"),
    TIM_BATCH_SEND_P2P_MSG("/v4/openim/batchsendmsg", "批量发单聊消息"),
    TIM_SET_MSG_READ("/v4/openim/admin_set_msg_read", "设置单聊消息已读"),
    TIM_ALL_USER_PUSH("/v4/all_member_push/im_push", "全员推送"),
    TIM_GET_OPERATION_DATA("/v4/openconfigsvr/getappinfo", "拉取运营数据"),
    ;

    private String url;

    private String des;

    RestRequestEnum(String url, String des) {
        this.url = url;
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public String getDes() {
        return des;
    }

}
