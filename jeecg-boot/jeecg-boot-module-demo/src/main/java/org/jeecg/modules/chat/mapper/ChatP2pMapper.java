package org.jeecg.modules.chat.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.chat.model.ChatP2p;
import org.jeecg.modules.chat.model.vo.ChatLatestVo;

import java.util.List;

public interface ChatP2pMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatP2p record);

    int insertSelective(ChatP2p record);

    ChatP2p selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatP2p record);

    int updateByPrimaryKey(ChatP2p record);

    List<ChatP2p> getLatestList(String userId, Page<ChatLatestVo> page);

}