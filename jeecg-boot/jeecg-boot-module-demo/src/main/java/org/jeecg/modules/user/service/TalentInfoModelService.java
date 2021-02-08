package org.jeecg.modules.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.user.model.TalentInfoModel;
import org.jeecg.modules.user.model.vo.TalentInfoVo;

public interface TalentInfoModelService{


    int deleteByPrimaryKey(String id);

    int insert(TalentInfoModel record);

    int insertSelective(TalentInfoModel record);

    TalentInfoModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentInfoModel record);

    int updateByPrimaryKey(TalentInfoModel record);

//    //达人严选
    Page<TalentInfoVo> loadTalentList(Page<TalentInfoVo> pageList, String search,String city);

    TalentInfoModel getTalentByUserId(String userId);

    int addAdvisoryNum(String userId);

}
