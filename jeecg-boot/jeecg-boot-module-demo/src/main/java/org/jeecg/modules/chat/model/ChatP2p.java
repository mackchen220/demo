package org.jeecg.modules.chat.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class ChatP2p implements Serializable {
    private String id;

    /**
     * 对话id userId-userId 双方id字典级正序
     */
    private String dialogId;

    /**
    * 发送方用户
    */
    private String userId;

    /**
    * 接收方用户
    */
    private String toId;

    /**
    * 消息类型 1-文字 2-图片 3-视频 4-报价 5-查看案例 6-交换微信
    */
    private Integer type;

    /**
    * 聊天消息
    */
    private String message;

    /**
    * 已读状态 0-未读 1-已读
    */
    private Integer isRead;

    /**
    * 消息发送时间
    */
    private String sendTime;

    /**
     * 删除标识 0-正常 1-已删除
     */
    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public static ChatP2p valueOf(String id, String userId, String toId, int type, String message) {
        ChatP2p info = new ChatP2p();
        info.setId(id);
        info.setUserId(userId);
        info.setToId(toId);
        info.setType(type);
        info.setMessage(message);
        return info;
    }
}