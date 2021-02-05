package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.course.mapper.CourseMapper;
import org.jeecg.modules.course.model.Course;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: CourseServiceImpl
 * @description: 社区模块业务代码
 * @author: LongXiang
 * @data: 2021-02-05 16:51
 * @version: V1.0
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Override
    public IPage<Course> followList(Page<Course> page, String userId) {
        List<Course> list = courseMapper.findListByUserId(page, null);
        return page.setRecords(list);
    }
}
