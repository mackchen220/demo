package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;
import org.jeecg.modules.community.service.CommunityModelService;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Result<Page<CommunityModelVo>> loadCommunityListByType(int type, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10")
                                                                          Integer pageSize,HttpServletRequest request) {
        String token = request.getHeader("token");

        String userId = userModelService.getUserIdByToken(token);
        Page<CommunityModelVo> pageList = new Page<CommunityModelVo>(pageNo, pageSize);

        pageList = communityModelService.loadCommunityListByType(pageList, type, userId);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.OK(pageList);
    }


    @ApiOperation("朋友圈搜索")
    @RequestMapping(value = "/loadCommunityBySearch", method = RequestMethod.POST)
    public Result<Page<CommunityModelVo>> loadCommunityBySearch(int pageNo, int pageSize, HttpServletRequest request, String search,
                                                                Integer type, Integer sortModel) {
        String token = request.getHeader("token");

        String userId = userModelService.getUserIdByToken(token);
        Result<Page<CommunityModelVo>> result = new Result<Page<CommunityModelVo>>();
        Page<CommunityModelVo> pageList = new Page<CommunityModelVo>(pageNo, pageSize);

        Page<CommunityModelVo> communityModelVoPage = communityModelService.loadCommunityBySearch(pageList, search, type, sortModel, userId);
        result.setResult(communityModelVoPage);

        return result;
    }


    @ApiOperation("发朋友圈")
    @RequestMapping(value = "/addMoments", method = RequestMethod.POST)
    public Result addMoments(CommunityModel communityModel, HttpServletRequest request) {
        Result result = new Result<>();
//        if (!ValidateTool.checkIsNull(communityModel.getTitle())) {
//            result.error500("请输入标题");
//            return result;
//        }
        if (!ValidateTool.checkIsNull(communityModel.getContent())) {
            result.error500("请输入内容");
            return result;
        }
        String token = request.getHeader("token");
        UserModel user = userModelService.getUserModelByToken(token);
        communityModel.setUserId(user.getId());
        communityModel.setUserType(user.getUserType());
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
    public Result addCommunityStar(String id, HttpServletRequest request, String type,String pageType) {
        String token = request.getHeader("token");
        String userId = userModelService.getUserIdByToken(token);
        communityModelService.addCommunityStar(id, userId, type, pageType);
        return Result.OK();
    }


    @ApiOperation("我的点赞我的收藏朋友圈列表")
    @RequestMapping(value = "/loadGoodCommunityList", method = RequestMethod.POST)
    public Result<Page<CommunityModelVo>> loadGoodCommunityList(int type, int pageNo, int pageSize, HttpServletRequest request) {
        String token = request.getHeader("token");

        String userId = userModelService.getUserIdByToken(token);
        Result<Page<CommunityModelVo>> result = new Result<Page<CommunityModelVo>>();
        Page<CommunityModelVo> pageList = new Page<CommunityModelVo>(pageNo, pageSize);

        pageList = communityModelService.loadGoodCommunityList(pageList, userId, type);
        result.setResult(pageList);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return result;
    }


}
