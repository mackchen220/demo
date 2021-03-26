package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.user.model.TalentCustomer;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.UserModel;
import org.jeecg.modules.user.model.vo.*;

import java.util.List;
import java.util.Map;

public interface TalentInfoModelService{


    int deleteByPrimaryKey(String id);

    int insert(TalentInfoModel record);

    int insertSelective(TalentInfoModel record);

    TalentInfoModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentInfoModel record);

    int updateByPrimaryKey(TalentInfoModel record);

//    //达人严选
    Page<TalentInfoVo> loadTalentList(Page<TalentInfoVo> pageList, String search,String city);

    //搜索达人下面的可能感兴趣的人
    List loadOtherTalentList(String city);


    Page<UserProjectVo> loadProjectlist(String search,Page<UserProjectVo> pageList);

    TalentInfoModel getTalentByUserId(String userId);

    int addAdvisoryNum(String userId);

    int addTalentInfo(String userId,String idNum,String name,String year,String city);

    Map getTalentBond(String userId);

    String addTalentBond(String userId);

    Map loadTalentCenter(UserModel user);

    void updateTalentInfo(String talentId);

    String talentCallBack(String orderId);

    void addCustomer(String talentId, String userId);


    Page<TalentCustomerVo> loadMyCustomer(Page<TalentCustomerVo> pageList, String userId);


    Map loadCustomrInfo(String talentId,String id,String userId);


    Map loadCustomrIncome(String userId,String year,String month,Page<UserIncomeDetailVo> page);



    Map loadExtensionCenter(UserModel userModel);



    Page<ExtensionVo> loadExtensionIncome(String userId, Page<ExtensionVo> page, Integer sortModel);



    Map<String, Object> talentMiniInfo(String userId,String talentId);


    Map<String, Object> talentArchives(String userId,String talentId);

}
