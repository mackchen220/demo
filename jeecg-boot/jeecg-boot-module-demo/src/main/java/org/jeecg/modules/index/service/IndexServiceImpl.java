package org.jeecg.modules.index.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.index.mapper.CourseModelMapper;
import org.jeecg.modules.index.mapper.TurnImageModelMapper;
import org.jeecg.modules.index.model.CourseModel;
import org.jeecg.modules.index.model.TurnImageModel;
import org.jeecg.modules.index.model.vo.TurnImageModelVo;
import org.jeecg.modules.user.mapper.TalentInfoModelMapper;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.TalentInfoModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class IndexServiceImpl implements IndexService{

    @Resource
    private TurnImageModelMapper turnImageModelMapper;
    @Resource
    private CourseModelMapper courseModelMapper;
    @Resource
    private TalentInfoModelMapper talentInfoModelMapper;

    @Override
    public Map loadIndexlist() {
        Map map=new HashMap();
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
            jsonObject.put("turnUrl",imageModel.getTurnUrl());
            jsonObject.put("url",imageModel.getUrl());
            jsonObject.put("title",imageModel.getTitle());
            list1.add(jsonObject);
        }
        map.put("turnImage",list1);
        //加载首页推荐课程
        List<CourseModel> courseModels = courseModelMapper.loadIndexCourseModelList("4");
        List<JSONObject> list2 = new ArrayList<>();
        for (CourseModel courseModel : courseModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("image",courseModel.getImage());
            jsonObject.put("title",courseModel.getTitle());
            jsonObject.put("price",courseModel.getPrice());
            jsonObject.put("num",courseModel.getWatchNum());
            list2.add(jsonObject);
        }
        map.put("courses",list2);
        //首页达人推荐
        List<UserModel> talents = talentInfoModelMapper.loadIndexTalentList("4");
        List<JSONObject> list3 = new ArrayList<>();

        for (UserModel model : talents) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("headName",model.getHeadImage());
            jsonObject.put("nickName",model.getNickName());
            list3.add(jsonObject);
        }
        map.put("talents",list3);

        return map;
    }
}
