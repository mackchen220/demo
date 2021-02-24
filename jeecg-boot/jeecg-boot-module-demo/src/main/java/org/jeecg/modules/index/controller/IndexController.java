package org.jeecg.modules.index.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.index.model.vo.HotSearchVo;
import org.jeecg.modules.index.service.HotSearchModelService;
import org.jeecg.modules.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hy/index")
@Api(tags = "前台首页模块")
public class IndexController {


    @Resource
    private IndexService indexService;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private HotSearchModelService hotSearchModelService;


    @DynamicResponseParameters(name = "indexResult",properties = {
            @DynamicParameter(name = "turnImage",value = "轮播图集合"),
            @DynamicParameter(name = "courses",value = "课程推荐" ),
            @DynamicParameter(name = "talents",value = "达人推荐" ,example = "121"),
    })
    @ApiOperation("刷新首页接口")
    @RequestMapping(value = "/loadIndexlist", method = RequestMethod.POST)
    public Result<Map> loadIndexlist() {

        Result<Map> result =new Result();
        Map map = indexService.loadIndexlist();
        result.setResult(map);
        return result;
    }


    @ApiOperation("热搜列表接口")
    @RequestMapping(value = "/loadHotSearchList", method = RequestMethod.POST)
    public Result<List> loadHotSearchList(String type,String token) {
        List<HotSearchVo> list = hotSearchModelService.loadHotSearchList(type);
        return Result.oKWithToken(token,list);
    }


}
