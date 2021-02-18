package org.jeecg.modules.index.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.index.service.IndexService;
import org.jeecg.modules.index.service.PartyModelService;
import org.jeecg.modules.user.model.vo.OrderModelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hy/party")
@Api(tags = "社群活动模块")
public class PartyController {


    @Resource
    private PartyModelService partyModelService;
    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation("社群派对活动列表")
    @RequestMapping(value = "/loadPartyList", method = RequestMethod.POST)
    public Result<Page<PartyModel>> loadPartyList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Result<Page<PartyModel>> result = new Result<Page<PartyModel>>();
        Page<PartyModel> pageList = new Page<PartyModel>(pageNo, pageSize);

        Page<PartyModel> orderModelVoPage = partyModelService.loadPartyList(pageList);
        result.setResult(orderModelVoPage);
        return result;
    }


    @ApiOperation("社群派对活动详情")
    @RequestMapping(value = "/loadPartyInfo", method = RequestMethod.POST)
    public Result<Map> loadPartyInfo(String partyId) {
        Map map = partyModelService.loadPartyInfo(partyId);
        return Result.OK(map);
    }



}
