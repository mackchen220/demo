package org.jeecg.modules.index.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.index.mapper.CourseModelMapper;
import org.jeecg.modules.index.mapper.TurnImageModelMapper;
import org.jeecg.modules.index.model.CourseModel;
import org.jeecg.modules.index.model.TurnImageModel;
import org.jeecg.modules.index.model.vo.TurnImageModelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class IndexServiceImpl implements IndexService{

    @Resource
    private TurnImageModelMapper turnImageModelMapper;
    @Resource
    private CourseModelMapper courseModelMapper;

    @Override
    public Result loadIndexlist() {
        //加载首页轮播图
        String dateTime = DateUtils.formatDateTime();
        List<TurnImageModel> turnImageModels = turnImageModelMapper.loadTurnImageList(dateTime);
        log.info("copy前的{},{}",dateTime,turnImageModels);
        //不返回多余字段
        ArrayList<TurnImageModelVo> list = new ArrayList<>();
        for (TurnImageModel model : turnImageModels) {
            TurnImageModelVo modelVo = new TurnImageModelVo();
            BeanUtils.copyProperties(model, modelVo);
            list.add(modelVo);
        }
        log.info("copy后的{}",list);
        //加载首页推荐课程
        List<CourseModel> courseModels = courseModelMapper.loadIndexCourseModelList();

        return null;
    }
}
