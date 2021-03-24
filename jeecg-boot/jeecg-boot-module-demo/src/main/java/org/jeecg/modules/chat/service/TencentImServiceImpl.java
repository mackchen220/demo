package org.jeecg.modules.chat.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.chat.api.GenerateUserSig;
import org.jeecg.modules.chat.api.Result;
import org.jeecg.modules.chat.mapper.ChatP2pMapper;
import org.jeecg.modules.chat.model.ChatP2p;
import org.jeecg.modules.chat.model.RestRequestEnum;
import org.jeecg.modules.chat.model.vo.ChatLatestVo;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.HttpUtil;
import org.jeecg.modules.commons.util.RandomUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @className: TencentImServiceImpl
 * @description: im聊天业务处理
 * @author: LongXiang
 * @data: 2021-03-15 14:21
 * @version: V1.0
 */
@Slf4j
@Service("tencentImService")
public class TencentImServiceImpl implements TencentImService {

    @Value("${im.tencent.host}")
    private String host;

    @Value("${im.tencent.appID}")
    private String appID;

    @Value("${im.tencent.admin}")
    private String admin;

    @Resource
    private UserModelService userModelService;

    @Resource
    private ChatP2pMapper chatP2pMapper;

    @Override
    public boolean chatP2P(String userId, String json) {
        if (ValidateTool.isNotBlank(json)) {
            JSONObject object = JSONObject.parseObject(json);
            if (ValidateTool.isNotNull(object)) {
                Integer type = object.getInteger("type");
                String toId = object.getString("toId");
                if (ValidateTool.isNull(type) || ValidateTool.isBlank(toId)) {
                    throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
                }
                ChatP2p info = ChatP2p.valueOf(SeqUtils.nextIdStr(), userId, toId, type, object.getString("msg"));
                int row = chatP2pMapper.insertSelective(info);
                return row > 0;
            }
        }
        return false;
    }

    @Override
    public IPage<ChatLatestVo> latestList(String userId, Page<ChatLatestVo> page) {
        List<ChatP2p> list = chatP2pMapper.getLatestList(userId, page);
        List<ChatLatestVo> chatVos = this.packChatLatestVo(userId, list);
        return page.setRecords(chatVos);
    }

    @Override
    public void register(String userId, int tryCount) {
        if (++tryCount > 3) {
            log.error("tencent register fail! userId={}", userId);
            return;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("Identifier", userId);
        String url =  this.getFullUrl(userId,RestRequestEnum.TIM_REGISTER.getUrl());
        String json = HttpUtil.httpPost(url, param);
        Result result = JSONObject.parseObject(json, Result.class);
        if (ValidateTool.isNotNull(result)) {
            if (result.getErrorCode() == 0) {
                log.info("tencent registered userId={}", userId);
            } else {
                try {
                    Thread.sleep(30_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.warn("re-registration from register fail! userId={} url={} response={}", userId, url, json);
                this.register(userId, tryCount);
            }
        } else {
            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.warn("re-registration from response black! userId={} url={}", userId, url);
            this.register(userId, tryCount);
        }
    }

    /**
     * 拼装腾讯云完整的请求地址
     * @param userId
     * @param url
     * @return
     */
    private String getFullUrl(String userId, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(host.concat(url).concat("?"));
        sb.append("sdkappid=".concat(appID));
        sb.append("identifier=".concat(admin));
        sb.append("usersig=".concat(GenerateUserSig.genUserSig(userId)));

        //请输入随机的32位无符号整数，取值范围0 - 4294967295
        long random = RandomUtil.nextInt() & 0xFFFFFFFFL;
        sb.append("random=").append(random);

        return sb.toString();
    }

    /**
     * 封装聊天列表消息
     * @param userId
     * @param list
     * @return
     */
    private List<ChatLatestVo> packChatLatestVo(String userId, List<ChatP2p> list) {
        List<ChatLatestVo> chatVos = new LinkedList<>();
        list.forEach(chatP2p -> {
            String friendId;
            if (userId.equals(chatP2p.getUserId())) {
                friendId = chatP2p.getToId();
            } else {
                friendId = chatP2p.getUserId();
            }
            UserModel user = userModelService.getUserById(friendId);
            chatVos.add(ChatLatestVo.valueOf(friendId, user.getUserType(), user.getNickName(), user.getHeadImage(),
                                             chatP2p.getMessage(), chatP2p.getSendTime()));
        });
        return chatVos;
    }

}
