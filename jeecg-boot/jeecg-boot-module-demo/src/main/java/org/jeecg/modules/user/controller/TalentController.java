package org.jeecg.modules.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.CaseModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;
import org.jeecg.modules.user.service.CaseModelService;
import org.jeecg.modules.user.service.TalentInfoModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/hy/talent")
@Api(tags = "前台用户模块")
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


    @ApiOperation("上传案例接口")
    @RequestMapping(value = "/addCase", method = RequestMethod.POST)
    public Result addCase(CaseModel caseModel,String token) {
        String id = userModelService.getUserIdByToken(token);
        caseModelService.insert(caseModel,id);
        return Result.OK();
    }




}
