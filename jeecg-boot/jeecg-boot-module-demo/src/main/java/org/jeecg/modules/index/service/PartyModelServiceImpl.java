package org.jeecg.modules.index.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.model.UserModel;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.index.model.PartyModel;
import org.jeecg.modules.index.mapper.PartyModelMapper;
import org.jeecg.modules.index.service.PartyModelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartyModelServiceImpl implements PartyModelService{

    @Resource
    private PartyModelMapper partyModelMapper;
    @Resource
    private UserModelMapper userModelMapper;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public int insertSelective(PartyModel record) {
        return partyModelMapper.insertSelective(record);
    }

    @Override
    public PartyModel selectByPrimaryKey(String id) {
        return partyModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PartyModel record) {
        return partyModelMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public Page<PartyModel> loadPartyList(Page<PartyModel> page) {
        List<PartyModel> partyModels = partyModelMapper.loadPartyList(page);
        return page.setRecords(partyModels);
    }

    @Override
    public Map loadPartyInfo(String partyId) {
        PartyModel partyModel = partyModelMapper.selectByPrimaryKey(partyId);
        if (ValidateTool.isNull(partyModel)) {
            throw new JeecgBootException("活动不存在");
        }
        //剩余名额
//        Object num = redisUtil.get(RedisKey.PARTY_NUM + partyId);
//        partyModel.setNextNum(Integer.parseInt(String.valueOf(num)));
        Map<String, Object> map = new HashMap<>();
        UserModel userModel = userModelMapper.loadUser(partyModel.getUserId(), null, null, null,null);
        map.put("userName",ValidateTool.isNull(userModel)?"":userModel.getNickName());
        map.put("headImage",ValidateTool.isNull(userModel)?"":userModel.getHeadImage());
        map.put("party",partyModel);
        return map;
    }
}
