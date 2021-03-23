package org.jeecg.modules.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.chat.model.vo.ChatLatestVo;

/**
 * @className: TencentImService
 * @description: im聊天业务处理
 * @author: LongXiang
 * @data: 2021-03-15 14:20
 * @version: V1.0
 */
public interface TencentImService {

    /**
     * 单人聊天
     *
     * @param json
     * @return
     */
    boolean chatP2P(String userId, String json);

    IPage<ChatLatestVo> latestList(String userId, Page<ChatLatestVo> page);

    void register(String userId);

}
