package org.jeecg.modules.user.service;

import lombok.extern.log4j.Log4j2;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.user.mapper.UserModelMapper;
import org.jeecg.modules.user.model.UserModel;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.mapper.UserFocusModelMapper;
import org.jeecg.modules.user.model.UserFocusModel;

@Log4j2
@Service
public class UserFocusModelServiceImpl implements UserFocusModelService{

    @Resource
    private UserFocusModelMapper userFocusModelMapper;
    @Resource
    private UserModelMapper userModelMapper;



    @Override
    public Result addUserFocus(String id,String userId) {
        Result result = new Result<>();
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);
        if (userModel==null){
            throw new JeecgBootException("关注失败");
        }
        log.info("id{},userid{}",id,userId);
        UserFocusModel userFocusModel = userFocusModelMapper.selectById(id, userId);
        if (userFocusModel==null){
            UserFocusModel model = new UserFocusModel();
            model.setId(SeqUtils.nextIdStr());
            model.setUserId(id);
            model.setFocusUserId(userId);
            userFocusModelMapper.insertSelective(model);
        }else {
            //关注过
            if (Constant.TYPE_INT_1==userFocusModel.getDelFlag()){
                userFocusModelMapper.updatedDelFlag(id, userId, Constant.CHECKTYPE0);
            }else {
                throw new JeecgBootException("已关注");
            }
        }
        return result;
    }

    @Override
    public Result delUserFocus(String id, String userId) {
        Result result = new Result<>();
        UserModel userModel = userModelMapper.loadUser(userId, null, null, null);
        UserFocusModel userFocusModel = userFocusModelMapper.selectById(id, userId);

        if (userModel==null || userFocusModel==null){
            throw new JeecgBootException("取消关注失败");
        }else {
            userFocusModelMapper.updatedDelFlag(id, userId, Constant.CHECKTYPE1);
        }
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(UserFocusModel record) {
        return userFocusModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserFocusModel record) {
        return userFocusModelMapper.updateByPrimaryKey(record);
    }


}
