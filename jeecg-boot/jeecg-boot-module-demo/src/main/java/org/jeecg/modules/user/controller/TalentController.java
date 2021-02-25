package org.jeecg.modules.user.controller;


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
import org.jeecg.modules.user.model.CaseModel;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;
import org.jeecg.modules.user.model.vo.UserProjectVo;
import org.jeecg.modules.user.service.CaseModelService;
import org.jeecg.modules.user.service.TalentInfoModelService;
import org.jeecg.modules.user.service.UserFocusModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public Result talentMiniInfo(String token, @ApiParam(name = "达人ID", required = true) String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        String mineId = userModelService.getUserIdByToken(token);
        Map<String, Object> rtn = new HashMap<>();

        UserModel user = userModelService.getUserById(userId);
        if (Objects.nonNull(user)) {
            rtn.put("id", userId);
            rtn.put("nikeName", user.getNickName());
            rtn.put("gender", user.getGender());
            rtn.put("birthday", user.getBirthday());
            rtn.put("province", user.getProvince());
            rtn.put("city", user.getCity());
            rtn.put("wechat", user.getWechat());
        }

        TalentInfoModel talent = talentInfoModelService.getTalentByUserId(userId);
        if (Objects.nonNull(talent)) {
            rtn.put("isTalent", true);
            rtn.put("num", talent.getNum());
            rtn.put("orderNum", talent.getOrderNum());
            rtn.put("advisoryNum", talent.getAdvisoryNum());
            rtn.put("likeNum", talent.getLikeNum());
            rtn.put("averageScore", talent.getAverageScore());
        } else {
            rtn.put("isTalent", false);
        }
        //粉丝数量
        int fansNum = userFocusModelService.getFansNum(userId);
        rtn.put("fansNum", fansNum);
        //是否关注
        boolean isFans = userFocusModelService.isFans(userId, mineId);
        rtn.put("isFans", isFans);
        return Result.OK(rtn);
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
    public Result talentArchives(String token, @ApiParam(name = "达人ID", required = true) String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new JeecgBootException(ErrorInfoCode.PARAMS_ERROR.getMsg());
        }
        Map<String, Object> rtn = new HashMap<>();

        UserModel user = userModelService.getUserById(userId);
        if (Objects.nonNull(user)) {
            rtn.put("id", userId);
            rtn.put("nikeName", user.getNickName());
            rtn.put("province", user.getProvince());
            rtn.put("city", user.getCity());
            rtn.put("createTime", user.getCreateTime());
        }

        TalentInfoModel talent = talentInfoModelService.getTalentByUserId(userId);
        if (Objects.nonNull(talent)) {
            rtn.put("isTalent", true);
            rtn.put("averageScore", talent.getAverageScore());
            rtn.put("effect", talent.getEffect());
            rtn.put("price", talent.getPrice());
            rtn.put("attitude", talent.getAttitude());
            rtn.put("deposit", talent.getDeposit() > 0);
            rtn.put("num", talent.getNum());
        } else {
            rtn.put("isTalent", false);
        }
        //是否关注
        String mineId = userModelService.getUserIdByToken(token);
        boolean isFans = userFocusModelService.isFans(userId, mineId);
        rtn.put("isFans", isFans);

        //TODO 客户数量未确定含义

        return Result.OK(rtn);
    }

    @ApiOperation("达人身份认证")
    @PostMapping("/addTalentInfo")
    public Result addTalentInfo(String token,String idNum,String name,String year,String city) {
        String id = userModelService.getUserIdByToken(token);

        talentInfoModelService.addTalentInfo(id,idNum,name,year,city);
        return Result.oKWithToken(token,null);
    }



    @ApiOperation("获取保证金")
    @PostMapping("/getbond")
    public Result getTalentBond(String token) {
        String talentBond = talentInfoModelService.getTalentBond();
        return Result.oKWithToken(token,talentBond);
    }



    @ApiOperation("缴纳保证金")
    @PostMapping("/addTalentBond")
    public Result addTalentBond(String token) {
        String id = userModelService.getUserIdByToken(token);
        talentInfoModelService.addTalentBond(id);
        return Result.oKWithToken(token,null);
    }


}
