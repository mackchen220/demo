package org.jeecg.modules.pay.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.index.service.IndexService;
import org.jeecg.modules.pay.model.PayModel;
import org.jeecg.modules.pay.servcie.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/hy/pay")
@Api(tags = "支付模块")
public class PayController {


    @Resource
    private PayService payService;
    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation("支付")
    @PostMapping("/wxPay")
    public Result<?> orderPay(@RequestBody PayModel payModel){
        return payService.wxOrderPay(payModel);
    }


    @ApiOperation("回调")
    @PostMapping("/wxNotify")
    public JSONArray notify(@RequestBody String notifyDate){
        return payService.notify(notifyDate);
    }






}
