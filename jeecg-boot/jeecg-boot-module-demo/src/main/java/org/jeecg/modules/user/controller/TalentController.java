package org.jeecg.modules.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.service.CommunityModelService;
import org.jeecg.modules.user.model.*;
import org.jeecg.modules.user.model.vo.*;
import org.jeecg.modules.user.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/hy/talent")
@Api(tags = "前台达人模块")
public class TalentController {

    @Resource
    private TalentInfoModelService talentInfoModelService;
    @Resource
    private UserModelService userModelService;
    @Resource
    private CaseModelService caseModelService;
    @Resource
    private UserFocusModelService userFocusModelService;
    @Resource
    private CommunityModelService communityModelService;

    @ApiOperation("达人严选列表接口")
    @RequestMapping(value = "/loadTalentList", method = RequestMethod.POST)
    public Result<Page<TalentInfoVo>> loadTalentList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     String seach, String city) {
        Result<Page<TalentInfoVo>> result = new Result<Page<TalentInfoVo>>();
        Page<TalentInfoVo> pageList = new Page<TalentInfoVo>(pageNo, pageSize);
        Page<TalentInfoVo> talentInfoVoPage = talentInfoModelService.loadTalentList(pageList, seach, city);
        result.setResult(talentInfoVoPage);
        return result;
    }

    @ApiOperation("搜索达人下面的可能感兴趣的人")
    @RequestMapping(value = "/loadOtherTalentList", method = RequestMethod.POST)
    public Result loadOtherTalentList(String city) {
        List list = talentInfoModelService.loadOtherTalentList(city);
        return Result.OK(list);
    }


    @ApiOperation("上传案例接口")
    @RequestMapping(value = "/addCase", method = RequestMethod.POST)
    public Result addCase(CaseModel caseModel, String token) {
        String id = userModelService.getUserIdByToken(token);
        caseModelService.insert(caseModel, id);
        return Result.OK();
    }

    @ApiOperation("查看案例接口")
    @RequestMapping(value = "/loadCaselist", method = RequestMethod.POST)
    public Result addCase(String token, String type) {
        String id = userModelService.getUserIdByToken(token);
        Map map = caseModelService.loadCaseList(id, type);
        return Result.OK(map);
    }


    @ApiOperation("搜项目接口，和首页搜索，达人一栏共用接口")
    @RequestMapping(value = "/getProjectlist", method = RequestMethod.POST)
    public Result<Page<UserProjectVo>> loadProjectlist(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       String search) {
        Result<Page<UserProjectVo>> result = new Result<Page<UserProjectVo>>();
        Page<UserProjectVo> pageList = new Page<UserProjectVo>(pageNo, pageSize);
        Page<UserProjectVo> userProjectVoPage = talentInfoModelService.loadProjectlist(search, pageList);
        result.setResult(userProjectVoPage);
        return result;
    }


    @ApiOperation("搜项目可能感兴趣的接口")
    @RequestMapping(value = "/getOtherProjectlist", method = RequestMethod.POST)
    public Result<Page<UserProjectVo>> getOtherProjectlist() {
        Result<Page<UserProjectVo>> result = new Result<Page<UserProjectVo>>();
        Page<UserProjectVo> pageList = new Page<UserProjectVo>(1, 4);
        Page<UserProjectVo> userProjectVoPage = talentInfoModelService.loadProjectlist(null, pageList);
        result.setResult(userProjectVoPage);
        return result;
    }

    @ApiOperation("达人咨询统计接口")
    @PostMapping("/addAdvisoryNum")
    public Result addAdvisoryNum(@ApiParam(name = "达人用户ID") String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        int num = talentInfoModelService.addAdvisoryNum(userId);
        return Result.OK(num);
    }

    @ApiOperation("达人动态上方迷你资料卡")
    @PostMapping("/talentMiniInfo")
    public Result talentMiniInfo(HttpServletRequest request, @ApiParam(name = "达人ID", required = true) String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        String token = request.getHeader("token");
        String mineId = userModelService.getUserIdByToken(token);
        Map<String, Object> map = talentInfoModelService.talentMiniInfo(mineId, userId);
        return Result.OK(map);
    }

    @ApiOperation("达人动态列表")
    @PostMapping("/talentMoments")
    public Result talentMoments(@ApiParam(name = "达人ID", required = true) String userId,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        IPage<CommunityModel> page = communityModelService.getListByUserId(new Page<>(pageNo, pageSize), userId);
        return Result.OK(page);
    }

    @ApiOperation("达人档案")
    @PostMapping("/talentArchives")
    public Result talentArchives(@ApiParam(name = "达人ID", required = true) String userId, HttpServletRequest request) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        String token = request.getHeader("token");

        String mineId = userModelService.getUserIdByToken(token);

        Map<String, Object> map = talentInfoModelService.talentArchives(mineId, userId);
        return Result.OK(map);
    }

    @ApiOperation("达人身份认证")
    @PostMapping("/addTalentInfo")
    public Result addTalentInfo(HttpServletRequest request, String idNum, String name, String year, String city) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        talentInfoModelService.addTalentInfo(id, idNum, name, year, city);
        return Result.OK();
    }


    @ApiOperation("获取保证金及其认证进度")
    @PostMapping("/getbondInfo")
    public Result getTalentBond(HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));

        Map map = talentInfoModelService.getTalentBond(id);
        return Result.OK(map);
    }


    @ApiOperation("缴纳保证金")
    @PostMapping("/addTalentBond")
    public Result addTalentBond(HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        String orderId = talentInfoModelService.addTalentBond(id);
        return Result.OK(orderId);
    }

    //达人中心
    @ApiOperation("达人中心")
    @PostMapping("/loadTalentCenter")
    public Result loadTalentCenter(HttpServletRequest request) {
        UserModel userModelByToken = userModelService.getUserModelByToken(TokenUtils.getToken(request));
        Map map = talentInfoModelService.loadTalentCenter(userModelByToken);
        return Result.OK(map);
    }


    //达人咨询
    @ApiOperation("添加客户")
    @PostMapping("/addCustomer")
    public Result addCustomer(String token, String userId) {
        String id = userModelService.getUserIdByToken(token);
        talentInfoModelService.addCustomer(id, userId);
        return Result.OK(null);
    }


    @ApiOperation("达人中心 我的客户列表")
    @RequestMapping(value = "/loadMyCustomer", method = RequestMethod.POST)
    public Result<Page<TalentCustomerVo>> loadMyCustomer(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest request) {
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Result<Page<TalentCustomerVo>> result = new Result<Page<TalentCustomerVo>>();
        Page<TalentCustomerVo> pageList = new Page<TalentCustomerVo>(pageNo, pageSize);
        Page<TalentCustomerVo> page = talentInfoModelService.loadMyCustomer(pageList, id);
        return Result.OK(page);
    }


    //达人中心
    @ApiOperation("客户详情")
    @PostMapping("/loadCustomrInfo")
    public Result loadCustomrInfo(String token, String userId, String id) {
        String talentId = userModelService.getUserIdByToken(token);
        Map map = talentInfoModelService.loadCustomrInfo(talentId, id, userId);
        return Result.OK( map);
    }

    //达人中心
    @ApiOperation("客户消费详情")
    @PostMapping("/loadCustomrIncome")
    public Result loadCustomrIncome(String token, String userId, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, String year, String month) {

        Page<UserIncomeDetailVo> page = new Page<>(pageNo, pageSize);
        Map map = talentInfoModelService.loadCustomrIncome(userId, year, month, page);

        return Result.OK(map);
    }


    //推广中心
    @ApiOperation("推广中心")
    @PostMapping("/loadExtensionCenter")
    public Result loadExtensionCenter(HttpServletRequest request) {
        UserModel model = userModelService.getUserModelByToken(TokenUtils.getToken(request));
        Map map = talentInfoModelService.loadExtensionCenter(model);
        return Result.OK(map);
    }


    //推广中心分页详情
    @ApiOperation("推广中心分页详情")
    @PostMapping("/loadExtensionIncome")
    public Result loadExtensionIncome(HttpServletRequest request, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, Integer sortModel) {
        Page<ExtensionVo> page = new Page<>(pageNo, pageSize);
        String id = userModelService.getUserIdByToken(TokenUtils.getToken(request));
        Page<ExtensionVo> extensionVoPage = talentInfoModelService.loadExtensionIncome(id, page, sortModel);
        return Result.OK(extensionVoPage);
    }


}
