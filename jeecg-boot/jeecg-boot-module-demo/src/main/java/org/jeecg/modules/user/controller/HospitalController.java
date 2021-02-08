package org.jeecg.modules.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
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

    @DynamicResponseParameters(name = "Result",properties = {
            @DynamicParameter(name = "hospitalId",value = "机构id"),
            @DynamicParameter(name = "name",value = "机构名称" ,example = "天马山医院"),
            @DynamicParameter(name = "hospitalImage",value = "机构封面图" ),
            @DynamicParameter(name = "content",value = "机构简介",example = "专门坑钱的医院"),
            @DynamicParameter(name = "talents",value = "达人列表",example = "[{ \"nickName\": \"乌龟大师7\", \"headImage\": null, \"id\": \"806488232119238656\"}]\n"),
    })
    @ApiOperation("搜机构，机构列表接口")
    @RequestMapping(value = "/loadAllHospitlist", method = RequestMethod.POST)
    public Result<Page<HospitalModel>> loadAllHospitlist(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        String search) {
        Result<Page<HospitalModel>> result = new Result<Page<HospitalModel>>();
        Page<HospitalModel> pageList = new Page<HospitalModel>(pageNo, pageSize);
        Page<HospitalModel> talentInfoVoPage = hospitalModelService.loadAllHospitlist(pageList,search);
        result.setResult(talentInfoVoPage);
        return result;
    }

    @DynamicResponseParameters(name = "Result",properties = {
            @DynamicParameter(name = "hospitalId",value = "机构id"),
            @DynamicParameter(name = "name",value = "机构名称" ,example = "天马山医院"),
            @DynamicParameter(name = "hospitalImage",value = "机构封面图" ),
            @DynamicParameter(name = "content",value = "机构简介",example = "专门坑钱的医院"),
            @DynamicParameter(name = "talents",value = "达人列表",example = "[{ \"nickName\": \"乌龟大师7\", \"headImage\": null, \"id\": \"806488232119238656\"}]\n"),
    })
    @ApiOperation("搜机构推荐列表")
    @RequestMapping(value = "/loadOtherHospitlist", method = RequestMethod.POST)
    public Result<Page<HospitalModel>> loadOtherHospitlist(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<Page<HospitalModel>> result = new Result<Page<HospitalModel>>();
        Page<HospitalModel> pageList = new Page<HospitalModel>(pageNo, pageSize);
        Page<HospitalModel> talentInfoVoPage = hospitalModelService.loadAllHospitlist(pageList,null);
        result.setResult(talentInfoVoPage);
        return result;
    }


}
