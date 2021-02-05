package org.jeecg.modules.Community.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import org.jeecg.modules.commons.util.MapUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.service.UserModelService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.Community.model.CommunityModel;
import org.jeecg.modules.Community.mapper.CommunityModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityModelServiceImpl implements CommunityModelService{

    @Resource
    private CommunityModelMapper communityModelMapper;

    @Resource
    private UserModelService userModelService;

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
        return communityModelMapper.updateByPrimaryKeySelective(record);
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
}
