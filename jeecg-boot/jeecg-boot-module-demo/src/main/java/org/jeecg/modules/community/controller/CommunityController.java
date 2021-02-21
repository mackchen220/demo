package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;
import org.jeecg.modules.community.service.CommunityModelService;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hy/community")
@Api(tags = "前台朋友圈模块")
public class CommunityController {


    @Resource
    private CommunityModelService communityModelService;
    @Resource
    private UserModelService userModelService;

    @ApiOperation("朋友圈列表")
    @RequestMapping(value = "/loadCommunityListByType", method = RequestMethod.POST)
    public Result<Page<CommunityModelVo>> loadCommunityListByType(int type, int pageNo, int pageSize, String token) {

        String userId = userModelService.getUserIdByToken(token);
        Result<Page<CommunityModelVo>> result = new Result<Page<CommunityModelVo>>();
        Page<CommunityModelVo> pageList = new Page<CommunityModelVo>(pageNo, pageSize);

        pageList = communityModelService.loadCommunityListByType(pageList, type , userId);
        result.setResult(pageList);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return result;
    }










    @ApiOperation("发朋友圈")
    @RequestMapping(value = "/addMoments", method = RequestMethod.POST)
    public Result addMoments(CommunityModel communityModel, String token) {
        Result result = new Result<>();
        if (!ValidateTool.checkIsNull(communityModel.getTitle())) {
            result.error500("请输入标题");
            return result;
        }
        if (!ValidateTool.checkIsNull(communityModel.getContent())) {
            result.error500("请输入内容");
            return result;
        }
        String userId = userModelService.getUserIdByToken(token);
        communityModel.setUserId(userId);
        communityModelService.insertSelective(communityModel);

        return result;
    }


    @ApiOperation("朋友圈详情接口")
    @RequestMapping(value = "/loadMomentsInfo", method = RequestMethod.POST)
    public Result loadMomentsInfo(String id) {
        Result result = new Result<>();
        Map map = communityModelService.loadMomentsInfo(id);
        result.setResult(map);
        return result;
    }


    @ApiOperation("朋友圈点赞接口")
    @RequestMapping(value = "/addCommunityStar", method = RequestMethod.POST)
    public Result addCommunityStar(String id,String token,String type) {
        String userId = userModelService.getUserIdByToken(token);
        Result result = new Result<>();
       communityModelService.addCommunityStar(id,userId,type);
        return Result.OK();
    }


    @ApiOperation("我的点赞我的收藏朋友圈列表")
    @RequestMapping(value = "/loadGoodCommunityList", method = RequestMethod.POST)
    public Result<Page<CommunityModelVo>> loadGoodCommunityList(int type, int pageNo, int pageSize, String token) {

        String userId = userModelService.getUserIdByToken(token);
        Result<Page<CommunityModelVo>> result = new Result<Page<CommunityModelVo>>();
        Page<CommunityModelVo> pageList = new Page<CommunityModelVo>(pageNo, pageSize);

        pageList = communityModelService.loadGoodCommunityList(pageList, userId , type);
        result.setResult(pageList);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return result;
    }



}
