package org.jeecg.modules.chat.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: ChatLatestVo
 * @description: 最新聊天消息
 * @author: LongXiang
 * @data: 2021-03-15 14:29
 * @version: V1.0
 */
@Data
public class ChatLatestVo implements Serializable {

    private String userId;

    private Integer userType;

    private String nickName;

    private String headImage;

    private String latestMsg;

    private String sendTime;

    public static ChatLatestVo valueOf(String userId, Integer userType, String nickName, String headImage,
                                       String latestMsg, String sendTime) {
        ChatLatestVo info = new ChatLatestVo();
        info.setUserId(userId);
        info.setUserType(userType);
        info.setNickName(nickName);
        info.setHeadImage(headImage);
        info.setLatestMsg(latestMsg);
        info.setSendTime(sendTime);
        return info;
    }
}
