package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.mapper.CommunityModelMapper;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.course.mapper.CourseMapper;
import org.jeecg.modules.course.model.Activity;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.course.model.vo.CourseInfoVo;
import org.jeecg.modules.course.model.vo.CourseVo;
import org.jeecg.modules.course.model.vo.UserCourseDetailVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;
import org.jeecg.modules.index.mapper.PartyModelMapper;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.user.mapper.UserCourseMapper;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.UserCourseService;
import org.jeecg.modules.user.service.UserCourseServiceImpl;
import org.jeecg.modules.user.service.UserFocusModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private UserFocusModelService userFocusModelService;
    @Resource
    private UserModelService userModelService;
    @Resource
    private CommunityModelMapper communityModelMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private PartyModelMapper partyModelMapper;
    @Resource
    private UserCourseMapper userCourseMapper;



    @Override
    public IPage<CommunityModel> followList(Page<CommunityModel> page, String userId) {
        List<CommunityModel> list = communityModelMapper.selectByFocusUserId(page, userId);
        return page.setRecords(list);
    }


    @Override
    public IPage<UserCourseVo> findList(int pageNo, int pageSize, int type, String city) {
        List<?> list;
        Page page = new Page<>(pageNo, pageSize);
        switch (type) {
            case 1:
                list = communityModelMapper.getListOrderByLikeNum(page, null, city);
                break;
            case 2:
                list = communityModelMapper.getListOrderByLikeNum(page, 2, city);
                break;
            case 3:
                list = communityModelMapper.getListOrderByLikeNum(page, 1, city);
                break;
            case 4:
                list = courseMapper.getListOrderByLikeNum(page, 1, city);
                break;
            case 5:
                list = courseMapper.getListOrderByLikeNum(page, 2, city);
                break;
            case 6:
                list = partyModelMapper.getListOrderByLikeNum(page, city);
                break;
            case 7:
                list = courseMapper.getListOrderByLikeNum(page, null, city);
                break;
            default:
                throw new JeecgBootException(ErrorInfoCode.SEARCH_TYPE_ERROR.getMsg());
        }
        List<UserCourseVo> result = new LinkedList<>();
        this.packUserCourseVo(list, result);
        return page.setRecords(result);
    }

    /**
     * 封装社区动态数据
     *
     * @param list
     * @param result
     */
    private void packUserCourseVo(List<?> list, List<UserCourseVo> result) {
        list.forEach(v -> {
            if (v instanceof Course) {
                Course info = (Course) v;
                UserCourseVo vo = UserCourseVo.valueOf(2, info.getId(), info.getTitle(), info.getImage(), info.getWatchNum(),
                        info.getGoodNum(), info.getStarNum(), info.getForwardNum());
                this.packUserByCourse(vo, info.getCreateBy());
                result.add(vo);
            } else if (v instanceof CommunityModel) {
                CommunityModel info = (CommunityModel) v;
                UserCourseVo vo = UserCourseVo.valueOf(1, info.getId(), info.getTitle(), info.getImageUrl(), info.getWatchNum(),
                        info.getGoodNum(), info.getStarNum(), info.getForwardNum());
                this.packUserByCourse(vo, info.getCreateBy());
                result.add(vo);
            } else {
                Activity info = (Activity) v;
                UserCourseVo vo = UserCourseVo.valueOf(3, info.getId(), info.getTitle(), info.getImage(), info.getWatchNum(),
                        info.getGoodNum(), info.getStarNum(), info.getForwardNum());
                this.packUserByCourse(vo, info.getCreateBy());
                result.add(vo);
            }
        });
    }

    private void packUserByCourse(UserCourseVo vo, String userId) {
        if (ValidateTool.isBlank(userId) || ValidateTool.isNull(vo)) {
            return;
        }
        UserModel user = userModelService.getUserById(userId);
        if (ValidateTool.isNotNull(user)) {
            vo.setUserId(user.getId());
            vo.setHeadImage(user.getHeadImage());
            vo.setNickName(user.getNickName());
        }
    }

    @Override
    public IPage<UserCourseVo> searchList(int pageNo, int pageSize, int type, String search) {
        Page page = new Page<>(pageNo, pageSize);
        List<Course> list = courseMapper.searchListOrderByType(page, type, search);
        List<UserCourseVo> result = new LinkedList<>();
        this.packUserCourseVo(list, result);
        return page.setRecords(result);
    }

    @Override
    public UserCourseDetailVo searchInfoDetail(String id, int courseType, String userId) {
        UserCourseDetailVo detailVo = new UserCourseDetailVo();
        switch (courseType) {
            case 1:
                CommunityModel info = communityModelMapper.selectByPrimaryKey(id);
                if (ValidateTool.isNotNull(info)) {
                    boolean isFans = userFocusModelService.isFans(info.getUserId(), userId);
                    detailVo = UserCourseDetailVo.valueOf(courseType, id, info.getTitle(), info.getImageUrl(), info.getWatchNum(), info.getGoodNum(),
                            info.getStarNum(), info.getForwardNum(), info.getCity(), info.getCreateTime(), isFans);
                    this.packUserByCourse(detailVo, info.getUserId());
                }
                break;
            case 2:
                Course course = courseMapper.selectByPrimaryKey(id);
                if (ValidateTool.isNotNull(course)) {
                    boolean isFans = userFocusModelService.isFans(course.getCreateBy(), userId);
                    detailVo = UserCourseDetailVo.valueOf(courseType, id, course.getTitle(), course.getImage(), course.getWatchNum(), course.getGoodNum(),
                            course.getStarNum(), course.getForwardNum(), course.getCity(), course.getCreateTime(), isFans);
                    this.packUserByCourse(detailVo, course.getCreateBy());
                }
                break;
            case 3:
                PartyModel party = partyModelMapper.selectByPrimaryKey(id);
                if (ValidateTool.isNotNull(party)) {
                    boolean isFans = userFocusModelService.isFans(party.getCreateBy(), userId);
                    detailVo = UserCourseDetailVo.valueOf(courseType, id, party.getTitle(), party.getImage(), party.getWatchNum(), party.getGoodNum(),
                            party.getStarNum(), party.getForwardNum(), party.getCity(), party.getCreateTime(), isFans);
                    this.packUserByCourse(detailVo, party.getCreateBy());
                }
                break;
            default:
                throw new JeecgBootException(ErrorInfoCode.SEARCH_TYPE_ERROR.getMsg());
        }
        return detailVo;
    }

    @Override
    public Map loadHengYangCourse(String type) {

        //亨氧学院推荐
        CourseVo course1 = courseMapper.getCourse(Constant.CHECKTYPE1, null);
        //亨氧学院banner
        CourseVo course2 = courseMapper.getCourse(null, Constant.CHECKTYPE1);

        List<CourseVo> courses1 = courseMapper.loadCourseListByType(null, null);

        List<CourseVo> courses2 = courseMapper.loadCourseListByType(null, Constant.CHECKTYPE1);

        Map<String, Object> map = new HashMap<>();
        map.put("recommed", course1);
        map.put("banner", ValidateTool.isNotNull(course2) ? course2.getImage() : "");
        map.put("courses", courses1);
        map.put("others", courses2);

        return map;
    }

    @Override
    public List<PartyModel> loadHengYangActivity() {
        List<PartyModel> partyModels = partyModelMapper.loadHengYangPartyList("2");
        return partyModels;
    }

    @Override
    public CourseVo getCourseInfo(String id,String userId) {
        CourseVo courseInfo = courseMapper.getCourseInfo(id);
        if (ValidateTool.isNotNull(courseInfo.getUserId())) {
            UserModel user = userModelService.getUserById(courseInfo.getUserId());
            courseInfo.setHeadImage(ValidateTool.isNotNull(user) ? user.getHeadImage() : "");
            courseInfo.setNickName(ValidateTool.isNotNull(user) ? user.getNickName() : "");
            courseInfo.setUserSign(ValidateTool.isNotNull(user) ? user.getSign() : "");
        }
        if (Constant.TYPE_INT_1 == courseInfo.getType()) {
            courseInfo.setUrl(null);
        }
        String course = userCourseMapper.loadUserCourse(userId, id);
        courseInfo.setByState(Integer.parseInt(course) > 0 ? 1 : 0);
        return courseInfo;
    }

    @Override
    public Map loadCommendCourse(String type) {
        //亨氧学院banner
        CourseVo course = courseMapper.getCourse(null, Constant.CHECKTYPE1);
        List<CourseVo> courses = courseMapper.loadCourseListByType(null, type);

        Map<String, Object> map = new HashMap<>();
        map.put("banner", course);
        map.put("courses", courses);

        return map;
    }

    @Override
    public List<CourseInfoVo> getCourseInfoList(String courseId) {

        return courseMapper.getCourseInfoList(courseId);
    }


    @Override
    public Page<CourseVo> loadCourseModelList(Page<CourseVo> page, String search) {
        List<CourseVo> courses = courseMapper.loadCourseModelList(page, search);
        return page.setRecords(courses);
    }
}
