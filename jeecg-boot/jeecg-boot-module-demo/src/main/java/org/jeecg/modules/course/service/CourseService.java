package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.course.model.Course;

/**
 * @className: CourseService
 * @description: 社区模块业务
 * @author: LongXiang
 * @data: 2021-02-05 16:50
 * @version: V1.0
 */
public interface CourseService {

    IPage<Course> followList(Page<Course> page, String userId);

}
