package org.jeecg.modules.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.user.model.vo.OrderModelVo;
import org.jeecg.modules.user.model.vo.UserProjectVo;
import org.jeecg.modules.user.service.AddressModelService;
import org.jeecg.modules.user.service.OrderModelService;
import org.jeecg.modules.user.service.UserBankModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/hy/order")
@Api(tags = "前台订单模块")
public class UserOrderController {

    @Resource
    private UserModelService userModelService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserBankModelService userBankModelService;
    @Resource
    private OrderModelService orderModelService;

    @Resource
    private AddressModelService addressModelService;


    @ApiOperation("支付项目定金，生成订单")
    @RequestMapping(value = "/payProjectDeposit", method = RequestMethod.POST)
    public Result payProjectDeposit(String projectIds, String talentId, String token, String amount, String num, String hospitalId) {
        String id = userModelService.getUserIdByToken(token);
        orderModelService.payProjectDeposit(projectIds, talentId, id, amount, num, hospitalId);
        return Result.OK();
    }



    @ApiOperation("我的订单列表")
    @RequestMapping(value = "/loadOrderList", method = RequestMethod.POST)
    public Result<Page<OrderModelVo>> loadOrderList(String token, String optStatus,@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        String id = userModelService.getUserIdByToken(token);

        Result<Page<OrderModelVo>> result = new Result<Page<OrderModelVo>>();
        Page<OrderModelVo> pageList = new Page<OrderModelVo>(pageNo, pageSize);


        Page<OrderModelVo> orderModelVoPage = orderModelService.loadOrderList(id, optStatus, pageList);
        result.setResult(orderModelVoPage);
        return result;
    }



}