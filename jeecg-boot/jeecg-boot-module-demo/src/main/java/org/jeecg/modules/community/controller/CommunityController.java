package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.service.CommunityModelService;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.TalentInfoModelService;
import org.jeecg.modules.user.service.UserFocusModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarException;

@Slf4j
@RestController
@RequestMapping("/hy/Community")
@Api(tags = "朋友圈模块")
public class CommunityController {


    @Resource
    private CommunityModelService communityModelService;
    @Resource
    private UserModelService userModelService;
    @Resource
    private TalentInfoModelService talentInfoModelService;
    @Resource
    private UserFocusModelService userFocusModelService;

    @ApiOperation("社区朋友圈列表")
    @RequestMapping(value = "/loadCommunityListByType", method = RequestMethod.POST)
    public Result<Page<CommunityModel>> loadCommunityListByType(int type,int pageNo,int pageSize,String token) {

        Result<Page<CommunityModel>> result = new Result<Page<CommunityModel>>();
        Page<CommunityModel> pageList = new Page<CommunityModel>(pageNo,pageSize);

        pageList = communityModelService.loadCommunityListByType(pageList, type);
        result.setResult(pageList);
        log.info("查询当前页："+pageList.getCurrent());
        log.info("查询当前页数量："+pageList.getSize());
        log.info("查询结果数量："+pageList.getRecords().size());
        log.info("数据总数："+pageList.getTotal());
        return result;
    }


    @ApiOperation("发朋友圈")
    @RequestMapping(value = "/addMoments", method = RequestMethod.POST)
    public Result loadCommunityListBype(CommunityModel communityModel,String token) {
        Result result = new Result<>();
        if (!ValidateTool.checkIsNull(communityModel.getTitle())){
            result.error500("请输入标题");
            return result;
        }
        if (!ValidateTool.checkIsNull(communityModel.getContent())){
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




    @ApiOperation("达人动态列表")
    @PostMapping("/talentMiniInfo")
    public Result talentMoments(@ApiParam(name = "达人ID", required = true) String userId,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        IPage<CommunityModel> page = communityModelService.getListByUserId(new Page<>(pageNo, pageSize), userId);
        return Result.OK(page);
    }





}
