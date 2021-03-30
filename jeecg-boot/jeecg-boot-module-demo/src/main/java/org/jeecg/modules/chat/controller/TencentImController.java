package org.jeecg.modules.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.chat.model.vo.ChatLatestVo;
import org.jeecg.modules.chat.service.TencentImService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: TencentImController
 * @description: 腾讯云IM
 * @data: 2021-03-15 11:51
 * @version: V1.0
 */
@RestController
@RequestMapping("/hy/chat")
@Api(tags = "前台IM通讯模块")
public class TencentImController {

    @Resource
    private UserModelService userModelService;
    @Resource
    private TencentImService tencentImService;

    @ApiOperation("私聊 (已弃用)")
    @PostMapping("chatP2P")
    private Result chatP2P(String token, String json) {
        String userId = userModelService.getUserIdByToken(token);
        boolean rst = tencentImService.chatP2P(userId, json);
        return Result.OK(rst);
    }

    @ApiOperation("获取聊天列表 (已弃用)")
    @PostMapping("latestList")
    private Result latestList(String token, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<ChatLatestVo> page = new Page<>(pageNo, pageSize);
        String userId = userModelService.getUserIdByToken(token);
        IPage<ChatLatestVo> list = tencentImService.latestList(userId, page);
        return Result.OK(list);
    }

}
