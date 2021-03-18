package org.jeecg.modules.index.service;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.course.mapper.CourseMapper;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.index.mapper.AppVersionMapper;
import org.jeecg.modules.index.mapper.PlatformConfigurationMapper;
import org.jeecg.modules.index.mapper.TurnImageModelMapper;
import org.jeecg.modules.index.model.AppVersion;
import org.jeecg.modules.index.model.CourseModel;
import org.jeecg.modules.index.model.PlatformConfiguration;
import org.jeecg.modules.index.model.TurnImageModel;
import org.jeecg.modules.user.mapper.TalentInfoModelMapper;
import org.jeecg.modules.user.model.UserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private TurnImageModelMapper turnImageModelMapper;
    @Resource
    private CourseMapper courseModelMapper;
    @Resource
    private TalentInfoModelMapper talentInfoModelMapper;
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private PlatformConfigurationMapper platformConfigurationMapper;

    @Override
    public Map loadIndexlist() {
        Map map = new HashMap();
        //加载首页轮播图
        String dateTime = DateUtils.formatDateTime();
        List<TurnImageModel> turnImageModels = turnImageModelMapper.loadTurnImageList(dateTime);
        //不返回多余字段
        List<JSONObject> list1 = new ArrayList<>();
//        for (TurnImageModel model : turnImageModels) {
//            TurnImageModelVo modelVo = new TurnImageModelVo();
//            BeanUtils.copyProperties(model, modelVo);
//            list1.add(modelVo);
//        }
        for (TurnImageModel imageModel : turnImageModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("turnUrl", imageModel.getTurnUrl());
            jsonObject.put("url", imageModel.getUrl());
            jsonObject.put("title", imageModel.getTitle());
            list1.add(jsonObject);
        }
        map.put("turnImage", list1);
        //加载首页推荐课程
        List<Course> courseModels = courseModelMapper.loadIndexCourseModelList("4");
        List<JSONObject> list2 = new ArrayList<>();
        for (Course courseModel : courseModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image", courseModel.getImage());
            jsonObject.put("title", courseModel.getTitle());
            jsonObject.put("price", courseModel.getPrice());
            jsonObject.put("num", courseModel.getWatchNum());
            list2.add(jsonObject);
        }
        map.put("courses", list2);
        //首页达人推荐
        List<UserModel> talents = talentInfoModelMapper.loadIndexTalentList("4");
        List<JSONObject> list3 = new ArrayList<>();

        for (UserModel model : talents) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("headName", model.getHeadImage());
            jsonObject.put("userId", model.getId());
            jsonObject.put("nickName", model.getNickName());
            list3.add(jsonObject);
        }
        map.put("talents", list3);

        return map;
    }


    @Override
    public Map loadAppVersion(String versionNum) {

        AppVersion appVersion = appVersionMapper.loadNewAppVersion();
        Map<String, Object> map = new HashMap<>();
        if (ValidateTool.isNull(appVersion)) {
            map.put("showVersion", null);
            map.put("sysVersion", null);
            map.put("content", "");
            map.put("downloadUrl", "");
            map.put("updateFlag", 0);
        } else {
            map.put("showVersion", appVersion.getShowVersion());
            map.put("sysVersion", appVersion.getSysVersion());
            map.put("content", appVersion.getContent());
            map.put("downloadUrl", appVersion.getDownloadUrl());
            map.put("updateFlag", appVersion.getUpdateFlag());
        }
        return map;
    }


    @Override
    public Map addInviteImage(UserModel user) {
        AppVersion appVersion = appVersionMapper.loadNewAppVersion();
        List<PlatformConfiguration> configList = platformConfigurationMapper.getConfigList(Constant.CONFIG_KEY_INVITE_IAMGE);
        List<String> imageList = new ArrayList<>();
        if (ValidateTool.isNotNull(configList) && configList.size() > 0) {
            for (PlatformConfiguration configuration : configList) {
                imageList.add(configuration.getConfigValue());
            }
        }
        PlatformConfiguration configByKey = platformConfigurationMapper.getConfigByKey(Constant.CONFIG_KEY_INVITE_CONTEXT);

        Map<String, Object> map = new HashMap<>();
        map.put("headName", user.getHeadImage());
        map.put("inviteCode", user.getInviteCode());
        map.put("nickName", user.getNickName());
        map.put("downloadUrl", appVersion.getDownloadUrl());
        map.put("imageList", imageList);
        map.put("context", ValidateTool.isNotNull(configByKey) ? configByKey.getConfigValue() : "");
        return map;
    }
}
