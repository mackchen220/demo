package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.model.BankModel;
import org.jeecg.modules.user.model.UserBankModel;
import org.jeecg.modules.user.model.vo.UserBankVo;

import java.util.List;

public interface UserBankModelService{



    Result insertUserBank(UserBankModel record,String captchaCode,String phone,String userId);

    UserBankModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserBankModel record);

    List<UserBankVo> loadUserCard(String userId);


    IPage<BankModel>  loadBankList(Page<BankModel> page);
}
