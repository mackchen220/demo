package org.jeecg.modules.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.CaseModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;
import org.jeecg.modules.user.model.vo.UserProjectVo;
import org.jeecg.modules.user.service.CaseModelService;
import org.jeecg.modules.user.service.TalentInfoModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public Result loadOtherTalentList(String seach) {
        List list = talentInfoModelService.loadOtherTalentList(seach);
        return Result.OK(list);
    }




    @ApiOperation("上传案例接口")
    @RequestMapping(value = "/addCase", method = RequestMethod.POST)
    public Result addCase(CaseModel caseModel,String token) {
        String id = userModelService.getUserIdByToken(token);
        caseModelService.insert(caseModel,id);
        return Result.OK();
    }

    @ApiOperation("查看案例接口")
    @RequestMapping(value = "/loadCaselist", method = RequestMethod.POST)
    public Result addCase(String token,String type) {
        String id = userModelService.getUserIdByToken(token);
        Map map = caseModelService.loadCaseList(id, type);
        return Result.OK(map);
    }



    @ApiOperation("搜机构接口")
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


    @ApiOperation("搜机构接口")
    @RequestMapping(value = "/getOtherProjectlist", method = RequestMethod.POST)
    public Result<Page<UserProjectVo>> getOtherProjectlist() {
        Result<Page<UserProjectVo>> result = new Result<Page<UserProjectVo>>();
        Page<UserProjectVo> pageList = new Page<UserProjectVo>(1, 4);
        Page<UserProjectVo> userProjectVoPage = talentInfoModelService.loadProjectlist(null, pageList);
        result.setResult(userProjectVoPage);
        return result;
    }



}
