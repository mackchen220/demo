package org.jeecg.modules.community.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.community.model.vo.CommunityModelVo;
import org.jeecg.modules.course.mapper.CourseMapper;
import org.jeecg.modules.course.model.Course;
import org.jeecg.modules.index.mapper.PartyModelMapper;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.user.mapper.TalentInfoModelMapper;
import org.jeecg.modules.user.mapper.UserFocusModelMapper;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.mapper.UserStarMapper;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.UserStar;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.mapper.CommunityModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CommunityModelServiceImpl implements CommunityModelService {

    @Resource
    private CommunityModelMapper communityModelMapper;

    @Resource
    private UserModelService userModelService;

    @Resource
    private UserStarMapper userStarMapper;

    @Resource
    private TalentInfoModelMapper talentInfoModelMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private PartyModelMapper partyModelMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserFocusModelMapper userFocusModelMapper;

    @Transactional
    @Override
    public int insertSelective(CommunityModel record) {
        TalentInfoModel talentInfoModel = talentInfoModelMapper.selectByUserId(record.getUserId());
        if (ValidateTool.isNull(talentInfoModel) || Constant.TYPE_INT_1 == talentInfoModel.getAuthenticated()) {
            throw new JeecgBootException("完成达人认证才能发朋友圈");
        }
        record.setId(SeqUtils.nextIdStr());
        record.setCheckStatus(Constant.TYPE_INT_2);
        return communityModelMapper.insertSelective(record);
    }

    @Override
    public CommunityModel selectByPrimaryKey(String id) {
        return communityModelMapper.selectByPrimaryKey(id);
    }


    @Override
    public Page<CommunityModelVo> loadCommunityListByType(Page<CommunityModelVo> page, int type, String userId) {

        if (ValidateTool.isNotNull(type) && Constant.TYPE_INT_2 == type) {
            // 达人动态
            return page.setRecords(communityModelMapper.loadTalentCommunity(page, userId));
        } else {
            //官方精选//视频动态
            return page.setRecords(communityModelMapper.loadCommunityListByType(page, type, userId));
        }
    }


    //我的点赞
    @Override
    public Page<CommunityModelVo> loadGoodCommunityList(Page<CommunityModelVo> page, String userId, int type) {

        List<CommunityModelVo> communityModelVos = communityModelMapper.loadStarAndGoodCommunityList(page, type, userId);

        return page.setRecords(communityModelVos);
    }

    @Override
    public Page<CommunityModelVo> loadCommunityBySearch(Page<CommunityModelVo> page, String search, Integer type, Integer sortModel, String userId) {

        List<CommunityModelVo> communityModelVos = communityModelMapper.loadCommunityBySearch(page, type, search, sortModel, userId);
        return page.setRecords(communityModelVos);
    }

    @Override
    public Map loadMomentsInfo(String id, String userId) {

        if (ValidateTool.isNull(id)) {
            throw new JeecgBootException("参数为空");
        }
        CommunityModel model = communityModelMapper.selectByPrimaryKey(id);
        if (ValidateTool.isNull(model)) {
            throw new JeecgBootException("朋友圈已删除");
        }
        Object o = redisUtil.get(RedisKey.WATCH_NUM + userId + id);
        if (ValidateTool.isNull(o)) {
            //防止浏览量疯狂加
            communityModelMapper.updateCommunityNum(id, Constant.TYPE_INT_1, null, null, null);
            redisUtil.set(RedisKey.WATCH_NUM + userId + id, id, 30);
        }
        //是否关注
        int isFans = userFocusModelMapper.selectUserFocus(model.getUserId(), userId);
        UserStar userStar = userStarMapper.loadUserStarByUserId(id, userId);
        UserModel user = userModelService.getUserById(model.getUserId());
        Map<String, Object> map = BeanUtil.beanToMap(model);
        map.put("nikeName", user.getNickName());
        map.put("headImage", user.getHeadImage());
        map.put("goodStatus", ValidateTool.isNotNull(userStar) ? userStar.getGood() : Constant.CHECKTYPE0);
        map.put("starStatus", ValidateTool.isNotNull(userStar) ? userStar.getStar() : Constant.CHECKTYPE0);
        map.put("isFans", isFans);
        map.remove("updateTime");
        map.remove("createBy");
        map.remove("updateBy");
        map.remove("delFlag");
        return map;
    }

    @Override
    public IPage<CommunityModel> getListByUserId(Page<CommunityModel> page, String userId) {
        List<CommunityModel> list = communityModelMapper.getListByUserId(page, userId);
        return page.setRecords(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCommunityStar(String id, String userId, String type, String pageType) {
        if (ValidateTool.isNull(pageType)) {
            throw new JeecgBootException("非法参数");
        }
        if (Constant.CHECKTYPE1.equals(pageType)) {
            CommunityModel communityModel = communityModelMapper.selectByPrimaryKey(id);
            if (ValidateTool.isNull(communityModel)) {
                throw new JeecgBootException("朋友圈已删除");
            }
        }
        if (Constant.CHECKTYPE2.equals(pageType)) {
            PartyModel partyModel = partyModelMapper.selectByPrimaryKey(id);
            if (ValidateTool.isNull(partyModel)) {
                throw new JeecgBootException("活动不存在");
            }
        }
        if (Constant.CHECKTYPE3.equals(pageType)) {
            Course course = courseMapper.selectByPrimaryKey(id);
            if (ValidateTool.isNull(course)) {
                throw new JeecgBootException("课程已删除");
            }
        }
        UserStar userStar = userStarMapper.loadUserStarByUserId(id, userId);
        //点赞
        if (Constant.CHECKTYPE1.equals(type)) {
            //是否已经点过赞
            if (ValidateTool.isNotNull(userStar) && Constant.CHECKTYPE1.equals(userStar.getGood())) {
                //点过赞,取消点赞
                userStarMapper.updateStar(id, userId, null, Constant.CHECKTYPE0);
//                communityModelMapper.updateCommunityNum(id, null, null, Constant.TYPE_INT_fuyi, null);
                updateNum(pageType, id, Constant.TYPE_INT_fuyi, null, null, null);
            } else if (ValidateTool.isNotNull(userStar)) {
                ////点过赞,取消过点赞，恢复点赞
                userStarMapper.updateStar(id, userId, null, Constant.CHECKTYPE1);
//                communityModelMapper.updateCommunityNum(id, null, null, Constant.TYPE_INT_1, null);
                updateNum(pageType, id, Constant.TYPE_INT_1, null, null, null);
            } else {
                UserStar model = new UserStar();
                model.setId(SeqUtils.nextIdStr());
                model.setUserId(userId);
                model.setCommunityId(id);
                model.setGood(Constant.CHECKTYPE1);
                model.setPageType(pageType);
                userStarMapper.insertSelective(model);
//                communityModelMapper.updateCommunityNum(id, null, null, Constant.TYPE_INT_1, null);
                updateNum(pageType, id, Constant.TYPE_INT_1, null, null, null);
            }
        } else if (Constant.CHECKTYPE3.equals(type)) {
            //转发
            updateNum(pageType, id, null, null, null, Constant.TYPE_INT_1);
        } else {
            //收藏
            //是否已经点收藏
            if (ValidateTool.isNotNull(userStar) && Constant.CHECKTYPE1.equals(userStar.getStar())) {
                //点过收藏,取消收藏
                userStarMapper.updateStar(id, userId, Constant.CHECKTYPE0, null);
//                communityModelMapper.updateCommunityNum(id, null, Constant.TYPE_INT_fuyi, null, null);
                updateNum(pageType, id, null, Constant.TYPE_INT_fuyi, null, null);
            } else if (ValidateTool.isNotNull(userStar)) {
                userStarMapper.updateStar(id, userId, Constant.CHECKTYPE1, null);
//                communityModelMapper.updateCommunityNum(id, null, Constant.TYPE_INT_1, null, null);
                updateNum(pageType, id, null, Constant.TYPE_INT_1, null, null);
            } else {
                UserStar model = new UserStar();
                model.setId(SeqUtils.nextIdStr());
                model.setUserId(userId);
                model.setCommunityId(id);
                model.setStar(Constant.CHECKTYPE1);
                model.setPageType(pageType);
                userStarMapper.insertSelective(model);
//                communityModelMapper.updateCommunityNum(id, null, Constant.TYPE_INT_1, null, null);
                updateNum(pageType, id, null, Constant.TYPE_INT_1, null, null);
            }
        }

    }


    public void updateNum(String pageType, String id, Integer goodNum, Integer starNum, Integer watchNum, Integer forwardNum) {
        //更新点赞收藏数量
        if (Constant.CHECKTYPE1.equals(pageType)) {
            communityModelMapper.updateCommunityNum(id, watchNum, starNum, goodNum, forwardNum);
        }
        if (Constant.CHECKTYPE2.equals(pageType)) {
            partyModelMapper.updatePartyNum(id, watchNum, starNum, goodNum, forwardNum);
        }
        if (Constant.CHECKTYPE3.equals(pageType)) {
            courseMapper.updateCourseNum(id, watchNum, starNum, goodNum, forwardNum);
        }
    }


}
