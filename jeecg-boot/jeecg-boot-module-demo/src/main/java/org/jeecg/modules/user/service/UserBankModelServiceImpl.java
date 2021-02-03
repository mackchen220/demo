package org.jeecg.modules.user.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.index.model.TurnImageModel;
import org.jeecg.modules.index.model.vo.TurnImageModelVo;
import org.jeecg.modules.user.model.vo.UserBankVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.jeecg.modules.user.model.UserBankModel;
import org.jeecg.modules.user.mapper.UserBankModelMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBankModelServiceImpl implements UserBankModelService{

    @Resource
    private UserBankModelMapper userBankModelMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserModelService userModelService;


    @Override
    public Result insertUserBank(UserBankModel userBankModel,String captchaCode,String phone,String token) {
        Result<String> result = new Result<>();
        if (!ValidateTool.checkIsNull(userBankModel) || !ValidateTool.checkIsNull(userBankModel.getRealName())){
                result.error500("请输入姓名");
                return result;
        }
        if (!ValidateTool.checkIsNull(userBankModel.getCardNumber())){
            result.error500("请输入银行卡号");
            return result;
        }
        if (!ValidateTool.checkIsNull(userBankModel.getBank())){
            result.error500("请选择银行");
            return result;
        }
        if (!ValidateTool.checkIsNull(userBankModel.getCity())){
            result.error500("请选择所在地址");
            return result;
        }
        if (!ValidateTool.checkIsNull(userBankModel.getSubBankName())){
            result.error500("请输入开户行");
            return result;
        }
        if (!ValidateTool.checkIsNull(captchaCode)){
            result.error500("请输入验证码");
            return result;
        }
//        ValidateTool.checkParamIsMobile(phone);
        String realKey = MD5Util.MD5Encode(captchaCode + phone, "utf-8");
        Object checkCode = redisUtil.get(realKey);
        //当进入登录页时，有一定几率出现验证码错误 #1714
        if (checkCode == null || !checkCode.toString().equals(captchaCode)) {
            result.error500("验证码错误");
            return result;
        }
        String userId = userModelService.getUserIdByToken(token);
        UserBankModel userBank = userBankModelMapper.loadBankInfoByUserId(userBankModel.getCardNumber());
        if (ValidateTool.checkIsNull(userBank)){
            result.error500("卡号已绑定");
        }
        userBankModel.setId(SeqUtils.nextIdStr());
        userBankModel.setUserId(userId);
        userBankModelMapper.insertSelective(userBankModel);
        return result;
    }

    @Override
    public UserBankModel selectByPrimaryKey(String id) {
        return userBankModelMapper.loadBankInfoByUserId(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserBankModel record) {
        return userBankModelMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public List<UserBankVo> loadUserCard(String userId) {
        List<UserBankModel> userBankModels = userBankModelMapper.loadUserCard(userId);
        //不返回多余字段
        ArrayList<UserBankVo> list = new ArrayList<>();
        for (UserBankModel model : userBankModels) {
            UserBankVo modelVo = new UserBankVo();
            BeanUtils.copyProperties(model, modelVo);
            list.add(modelVo);
        }

        return list;
    }
}
