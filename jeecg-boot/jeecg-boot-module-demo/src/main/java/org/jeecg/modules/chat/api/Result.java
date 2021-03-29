package org.jeecg.modules.chat.api;

import lombok.Data;

/**
 * @className: Result
 * @description: 腾讯云返回实体
 * @author: LongXiang
 * @data: 2021-03-23 10:59
 * @version: V1.0
 */
@Data
public class Result {

    private String ActionStatus;

    private Integer ErrorCode;

    private String ErrorInfo;

}
