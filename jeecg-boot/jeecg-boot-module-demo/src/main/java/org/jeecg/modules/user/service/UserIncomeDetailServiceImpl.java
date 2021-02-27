package org.jeecg.modules.user.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.model.UserIncomeDetail;
import org.jeecg.modules.user.mapper.UserIncomeDetailMapper;

@Service
public class UserIncomeDetailServiceImpl implements UserIncomeDetailService{

    @Resource
    private UserIncomeDetailMapper userIncomeDetailMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return userIncomeDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserIncomeDetail record) {
        return userIncomeDetailMapper.insert(record);
    }

    @Override
    public int insertSelective(UserIncomeDetail record) {
        return userIncomeDetailMapper.insertSelective(record);
    }

    @Override
    public UserIncomeDetail selectByPrimaryKey(String id) {
        return userIncomeDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserIncomeDetail record) {
        return userIncomeDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserIncomeDetail record) {
        return userIncomeDetailMapper.updateByPrimaryKey(record);
    }

}
