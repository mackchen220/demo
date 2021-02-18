package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.course.model.vo.UserCourseDetailVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;

import java.util.List;

/**
 * @className: CourseService
 * @description: 社区模块业务
 * @author: LongXiang
 * @data: 2021-02-05 16:50
 * @version: V1.0
 */
public interface CourseService {

    IPage<CommunityModel> followList(Page<CommunityModel> page, String userId);

    List<String> topSearch();

    IPage<UserCourseVo> findList(int pageNo, int pageSize, int type, String city);

    IPage<UserCourseVo> searchList(int pageNo, int pageSize, int type, String search);

    UserCourseDetailVo searchInfoDetail(String id, int courseType, String userId);

}
