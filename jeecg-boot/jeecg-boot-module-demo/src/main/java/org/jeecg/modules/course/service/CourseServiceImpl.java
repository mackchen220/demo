package org.jeecg.modules.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.DateHelper;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.mapper.CommunityModelMapper;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;
import org.jeecg.modules.course.mapper.CourseMapper;
import org.jeecg.modules.course.model.Activity;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.course.model.vo.CourseInfoVo;
import org.jeecg.modules.course.model.vo.CourseVo;
import org.jeecg.modules.course.model.vo.UserCourseDetailVo;
import org.jeecg.modules.course.model.vo.UserCourseVo;
import org.jeecg.modules.index.mapper.PartyModelMapper;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.user.mapper.OrderModelMapper;
import org.jeecg.modules.user.mapper.UserCourseMapper;
import org.jeecg.modules.user.mapper.UserFocusModelMapper;
import org.jeecg.modules.user.mapper.UserStarMapper;
import org.jeecg.modules.user.model.OrderModel;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserCourse;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.UserModelVo;
import org.jeecg.modules.user.service.UserCourseService;
import org.jeecg.modules.user.service.UserCourseServiceImpl;
import org.jeecg.modules.user.service.UserFocusModelService;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @className: CourseServiceImpl
 * @description: 社区模块业务代码
 * @data: 2021-02-05 16:51
 * @version: V1.0
 */
@Log4j2
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
    @Resource
    private UserStarMapper userStarMapper;
    @Resource
    private OrderModelMapper orderModelMapper;
    @Resource
    private UserFocusModelMapper userFocusModelMapper;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public IPage<CommunityModelVo> followList(Page<CommunityModelVo> page, String userId) {
        List<CommunityModelVo> list = communityModelMapper.selectByFocusUserId(page, userId);
        return page.setRecords(list);
    }


    @Override
    public IPage<UserCourseVo> findList(int pageNo, int pageSize, int type, String city, String userId) {
//        List<?> list;
        List<UserCourseVo> list;
        Page page = new Page<>(pageNo, pageSize);
        switch (type) {
            case 1:
                list = communityModelMapper.getListOrderByLikeNum(page, null, city, userId);
                break;
            case 2:
                list = communityModelMapper.getListOrderByLikeNum(page, 2, city, userId);
                break;
            case 3:
                list = communityModelMapper.getListOrderByLikeNum(page, 1, city, userId);
                break;
            case 4:
                list = courseMapper.getListOrderByLikeNum(page, 1, city, userId);
                break;
            case 5:
                list = communityModelMapper.getListOrderByLikeNum(page, 2, city, userId);
                break;
            case 6:
                list = partyModelMapper.getListOrderByLikeNum(page, city, userId);
                break;
            case 7:
                list = courseMapper.getListOrderByLikeNum(page, null, city, userId);
                break;
            default:
                throw new JeecgBootException(ErrorInfoCode.SEARCH_TYPE_ERROR.getMsg());
        }
//        List<UserCourseVo> result = new LinkedList<>();
//        this.packUserCourseVo(list, result);
        return page.setRecords(list);
    }

//    private void packUserCourseVo(List<?> list, List<UserCourseVo> result) {
//        list.forEach(v -> {
//            if (v instanceof Course) {
//                Course info = (Course) v;
//                UserCourseVo vo = UserCourseVo.valueOf(2, info.getId(), info.getTitle(), info.getImage(), info.getWatchNum(),
//                        info.getGoodNum(), info.getStarNum(), info.getForwardNum());
//                this.packUserByCourse(vo, info.getUserId());
//                result.add(vo);
//            } else if (v instanceof CommunityModelVo) {
//                CommunityModelVo info = (CommunityModelVo) v;
//                UserCourseVo vo = UserCourseVo.valueOf(1, info.getId(), info.getTitle(), info.getImageUrl(), info.getWatchNum(),
//                        info.getGoodNum(), info.getStarNum(), info.getForwardNum(), info.getType(), info.getGoodStatus(), info.getStarStatus());
//                this.packUserByCourse(vo, info.getUserId());
//                result.add(vo);
//            } else {
//                PartyModel info = (PartyModel) v;
//                UserCourseVo vo = UserCourseVo.valueOf(3, info.getId(), info.getTitle(), info.getImage(), info.getWatchNum(),
//                        info.getGoodNum(), info.getStarNum(), info.getForwardNum());
//                this.packUserByCourse(vo, info.getUserId());
//                result.add(vo);
//            }
//        });
//    }

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
    public IPage<UserCourseVo> searchList(int pageNo, int pageSize, int type, String search, String userId) {
        Page page = new Page<>(pageNo, pageSize);
        List<UserCourseVo> list = courseMapper.searchListOrderByType(page, type, search, userId);
        return page.setRecords(list);
    }

    @Override
    public IPage<UserModelVo> loadMyFocus(Page page, String userId) {
        List list = userFocusModelMapper.loadMyFocus(page, userId);
        return page.setRecords(list);
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
    public CourseVo getCourseInfo(String id, UserModel userModel) {
        CourseVo courseInfo = courseMapper.getCourseInfo(id);
        if (ValidateTool.isNull(courseInfo)) {
            throw new JeecgBootException("课程不存在");
        }
        if (ValidateTool.isNotNull(courseInfo.getUserId())) {
            UserModel user = userModelService.getUserById(courseInfo.getUserId());
            courseInfo.setHeadImage(ValidateTool.isNotNull(user) ? user.getHeadImage() : "");
            courseInfo.setNickName(ValidateTool.isNotNull(user) ? user.getNickName() : "");
            courseInfo.setUserSign(ValidateTool.isNotNull(user) ? user.getSign() : "");
        }
        Object o = redisUtil.get(RedisKey.WATCH_NUM + userModel.getId() + id);
        if (ValidateTool.isNull(o)) {
            //防止浏览量疯狂加
            courseMapper.updateCourseNum(id, Constant.TYPE_INT_1, null, null, null);
            redisUtil.set(RedisKey.WATCH_NUM + userModel.getId() + id, id, 30);
        }
        String url = courseInfo.getUrl();
        String course = userCourseMapper.loadUserCourse(userModel.getId(), id);
        if (Constant.TYPE_INT_1 == courseInfo.getType() && (ValidateTool.isNull(course) || Integer.parseInt(course) < 1)) {
            //没购买的用户不能看付费视频
            courseInfo.setUrl(null);
        }
        if (Constant.CHECKTYPE1.equals(courseInfo.getVipFree()) && ValidateTool.isNotNull(userModel.getVipId())) {
            //会员免费的课程
            courseInfo.setUrl(url);
        }
        courseInfo.setByState(1);
        if (Constant.TYPE_INT_1 == courseInfo.getType()) {
            courseInfo.setByState(Integer.parseInt(course) > 0 ? 1 : 0);
        }
        int star = userStarMapper.isStar(id, userModel.getId(), Constant.CHECKTYPE1, null);
        int good = userStarMapper.isStar(id, userModel.getId(), null, Constant.CHECKTYPE1);
        courseInfo.setStarStatus(String.valueOf(star));
        courseInfo.setGoodStatus(String.valueOf(good));
        return courseInfo;
    }

    @Override
    public Page<CourseVo> loadCommendCourse(Page<CourseVo> page, String type) {
        List<CourseVo> courses = courseMapper.loadCourseListPage(page, type);
        return page.setRecords(courses);
    }


    @Override
    public Page<CourseVo> searchCourse(Page<CourseVo> page, String search) {
        List<CourseVo> courses = courseMapper.searchCourse(page, search);
        return page.setRecords(courses);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map addUserCourse(String courseId, UserModel userModel) {
        Map<String, Object> map = new HashMap<>();
        Course course = courseMapper.selectByPrimaryKey(courseId);
        if (ValidateTool.isNull(course)) {
            throw new JeecgBootException("课程不存在");
        }
        String buy = userCourseMapper.loadUserCourse(userModel.getId(), courseId);
        if (ValidateTool.isNotNull(buy) && Integer.parseInt(buy) > 0) {
            throw new JeecgBootException("课程已订阅,无须重复订阅");
        }
        OrderModel orderModel = new OrderModel();
        if (Constant.TYPE_INT_1 == course.getType()) {
            orderModel.setId(SeqUtils.nextIdStr());
            orderModel.setUserId(userModel.getId());
            orderModel.setOperationType(Constant.TYPE_INT_2);
            orderModel.setContent("课程");
            orderModel.setNum(Constant.TYPE_INT_1);
            orderModel.setOptStatus(Constant.TYPE_INT_1);
            orderModel.setAmount(String.valueOf(course.getPrice()));
            orderModel.setCourseId(courseId);
            orderModelMapper.insertSelective(orderModel);
            map.put("orderId", orderModel.getId());
//            if (ValidateTool.isNotNull(userModel.getVipId()) && Constant.CHECKTYPE1.equals(course.getVipFree())){
//
//            }
        } else {
            //免费课程，不调用支付接口
            map.put("orderId", null);
        }
        return map;
    }


    @Override
    public String courseCallBack(String orderId) {

        log.info("购买课程回调{}", orderId);
        OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
        if (ValidateTool.isNull(orderModel)) {
            log.warn("购买课程回调,查询不到订单{}", orderId);
            return null;
        }
        Course course = courseMapper.selectByPrimaryKey(orderModel.getCourseId());
        if (ValidateTool.isNull(course)) {
            throw new JeecgBootException("课程不存在");
        }
        UserCourse userCourse = new UserCourse();
        userCourse.setId(SeqUtils.nextIdStr());
        userCourse.setUserId(orderModel.getUserId());
        userCourse.setCourseId(orderModel.getCourseId());
        userCourse.setPrice(orderModel.getAmount());
        userCourse.setBeginTime(DateHelper.getTodayTime2());
        //有效期一年
        userCourse.setEndTime(DateHelper.plusDate(DateHelper.getTodayTime2(), 1, 1, DateHelper.DATETIME_FORMAT));

        userCourseMapper.insertSelective(userCourse);
        return "ok";
    }


}
