package org.jeecg.modules.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.HospitalModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;
import org.jeecg.modules.user.service.HospitalModelService;
import org.jeecg.modules.user.service.TalentHospitalService;
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
@RequestMapping("/hy/Hospital")
@Api(tags = "前台机构模块")
public class HospitalController {

    @Resource
    private TalentInfoModelService talentInfoModelService;
    @Resource
    private UserModelService userModelService;
    @Resource
    private HospitalModelService hospitalModelService;
    @Resource
    private TalentHospitalService talentHospitalService;


//    @ApiOperation("达人机构列表接口")
//    @RequestMapping(value = "/loadHospitalList", method = RequestMethod.POST)
//    public Result<List> loadTalentList(String userId) {
//        List list = talentHospitalService.loadHospitalistByUserId(userId);
//        return  Result.OK(list);
//    }


    @ApiOperation("达人咨询查看报价接口")
    @RequestMapping(value = "/loadProjectList", method = RequestMethod.POST)
    public Result<Map> loadTalentList(String id,String projectId,String hospitalId) {
        Map map = talentHospitalService.loadProjectList(id, projectId,hospitalId);
        return  Result.OK(map);
    }

    @ApiOperation("达人咨询，报价单接口")
    @RequestMapping(value = "/getQuotation", method = RequestMethod.POST)
    public Result<Map> getQuotation(String materialId, String projectIds, String hospitalId) {
        Map map = talentHospitalService.getQuotation(projectIds, materialId, hospitalId);
        return Result.OK(map);
    }




    @ApiOperation("达人严选列表接口")
    @RequestMapping(value = "/loadAllHospitlist", method = RequestMethod.POST)
    public Result<Page<HospitalModel>> loadAllHospitlist(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<Page<HospitalModel>> result = new Result<Page<HospitalModel>>();
        Page<HospitalModel> pageList = new Page<HospitalModel>(pageNo, pageSize);
        Page<HospitalModel> talentInfoVoPage = hospitalModelService.loadAllHospitlist(pageList);
        result.setResult(talentInfoVoPage);
        return result;
    }

}
