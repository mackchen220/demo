package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.TalentCustomer;
import org.jeecg.modules.user.model.vo.TalentCustomerVo;

import java.util.List;

@Mapper
public interface TalentCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(TalentCustomer record);

    int insertSelective(TalentCustomer record);

    TalentCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TalentCustomer record);

    int updateByPrimaryKey(TalentCustomer record);


    List<TalentCustomerVo> loadMyCustomer(Page<TalentCustomerVo> page ,@Param("talentId") String talentId);
}
