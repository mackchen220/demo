package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.mapper.CommunityModelMapper;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.course.mapper.CourseMapper;
import org.jeecg.modules.course.mapper.TopSearchMapper;
import org.jeecg.modules.course.model.TopSearch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
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
    private TopSearchMapper topSearchMapper;
    @Resource
    private CommunityModelMapper communityModelMapper;
    @Resource
    private CourseMapper courseMapper;

    @Override
    public IPage<CommunityModel> followList(Page<CommunityModel> page, String userId) {
        List<CommunityModel> list = communityModelMapper.selectByFocusUserId(page, userId);
        return page.setRecords(list);
    }

    @Override
    public List<String> topSearch() {
        List<TopSearch> list = topSearchMapper.selectAll();
        List<String> rtn = new LinkedList<>();
        if (ValidateTool.isNotEmpty(list)) {
            list.forEach(v -> rtn.add(v.getTopSearch()));
        }
        return rtn;
    }

    @Override
    public IPage<?> findList(int pageNo, int pageSize, int type, String city) {
        List<?> list = new LinkedList<>();
        switch (type) {
            case 1:
                list = communityModelMapper.getListOrderByLikeNum(new Page<>(pageNo, pageSize), null, city);
                break;
            case 2:
                list = communityModelMapper.getListOrderByLikeNum(new Page<>(pageNo, pageSize), 2, city);
                break;
            case 3:
                list = communityModelMapper.getListOrderByLikeNum(new Page<>(pageNo, pageSize), 1, city);
                break;
            case 4:
                list = courseMapper.getListOrderByLikeNum(new Page<>(pageNo, pageSize), )
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
        }
        return null;
    }

}
