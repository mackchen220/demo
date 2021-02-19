package org.jeecg.modules.community.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.mapper.UserStarMapper;
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
public class CommunityModelServiceImpl implements CommunityModelService{

    @Resource
    private CommunityModelMapper communityModelMapper;

    @Resource
    private UserModelService userModelService;

    @Resource
    private UserStarMapper userStarMapper;

    @Resource
    private UserModelMapper userModelMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return communityModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CommunityModel record) {
        return communityModelMapper.insert(record);
    }

    @Transactional
    @Override
    public int insertSelective(CommunityModel record) {
        record.setId(SeqUtils.nextIdStr());

        return communityModelMapper.insertSelective(record);
    }

    @Override
    public CommunityModel selectByPrimaryKey(String id) {
        return communityModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CommunityModel record) {
        return communityModelMapper.updateSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommunityModel record) {
        return communityModelMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<CommunityModel> loadCommunityListByType(Page<CommunityModel> page,int type) {
        return page.setRecords(communityModelMapper.loadCommunityListByType(page,type));
    }

    @Override
    public Map loadMomentsInfo(String id) {
        CommunityModel model = communityModelMapper.selectByPrimaryKey(id);
        UserModel user = userModelService.getUserById(model.getUserId());
        Map<String, Object> map = BeanUtil.beanToMap(model);
        map.put("nikeName",user.getNickName());
        map.put("headImage",user.getHeadImage());
        map.remove("createTime");
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
    public void addCommunityStar(String id, String userId,String type) {
        CommunityModel communityModel = communityModelMapper.selectByPrimaryKey(id);
        if (ValidateTool.isNull(communityModel)){
            throw new JeecgBootException("朋友圈已删除");
        }
        UserStar userStar = userStarMapper.loadUserStarByUserId(id, userId);

        //点赞
        if (Constant.CHECKTYPE1.equals(type)){
            //是否已经点过赞
            if (ValidateTool.isNotNull(userStar) &&Constant.CHECKTYPE1.equals(userStar.getGood())){
                //点过赞,取消点赞
                userStarMapper.updateStar(id,userId,null,Constant.CHECKTYPE0);
                communityModelMapper.updateCommunityNum(id,userId,null,null,Constant.TYPE_INT_fuyi,null);
            } else if (ValidateTool.isNotNull(userStar)){
                ////点过赞,取消过点赞，恢复点赞
                userStarMapper.updateStar(id,userId,null,Constant.CHECKTYPE1);
                communityModelMapper.updateCommunityNum(id,userId,null,null,Constant.TYPE_INT_1,null);
            }else {
                UserStar model = new UserStar();
                model.setId(SeqUtils.nextIdStr());
                model.setUserId(userId);
                model.setCommunityId(id);
                model.setGood(Constant.CHECKTYPE1);
                userStarMapper.insertSelective(model);
                communityModelMapper.updateCommunityNum(id,userId,null,null,Constant.TYPE_INT_1,null);
            }
        }else {
            //收藏
            //是否已经点收藏
            if (ValidateTool.isNotNull(userStar) &&Constant.CHECKTYPE1.equals(userStar.getStar())){
                //点过收藏,取消收藏
                userStarMapper.updateStar(id,userId,Constant.CHECKTYPE0,null);
                communityModelMapper.updateCommunityNum(id,userId,null,Constant.TYPE_INT_fuyi,null,null);
            }else if (ValidateTool.isNotNull(userStar)){
                userStarMapper.updateStar(id,userId,Constant.CHECKTYPE1,null);
                communityModelMapper.updateCommunityNum(id,userId,null,Constant.TYPE_INT_1,null,null);
            }else {
                UserStar model = new UserStar();
                model.setId(SeqUtils.nextIdStr());
                model.setUserId(userId);
                model.setCommunityId(id);
                model.setStar(Constant.CHECKTYPE1);
                userStarMapper.insertSelective(model);
                communityModelMapper.updateCommunityNum(id,userId,null,Constant.TYPE_INT_1,null,null);
            }
        }

    }
}
