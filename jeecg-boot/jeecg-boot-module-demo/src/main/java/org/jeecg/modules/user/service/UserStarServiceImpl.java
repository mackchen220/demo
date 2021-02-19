package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.UserStarMapper;
import org.jeecg.modules.user.model.UserStar;

@Service
public class UserStarServiceImpl implements UserStarService{

    @Resource
    private UserStarMapper userStarMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return userStarMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserStar record) {
        return userStarMapper.insert(record);
    }

    @Override
    public int insertSelective(UserStar record) {
        return userStarMapper.insertSelective(record);
    }

    @Override
    public UserStar selectByPrimaryKey(String id) {
        return userStarMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserStar record) {
        return userStarMapper.updateByPrimaryKeySelective(record);
    }


}
