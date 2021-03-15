package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.BankModel;

import java.util.List;

@Mapper
public interface BankModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(BankModel record);

    int insertSelective(BankModel record);

    BankModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BankModel record);

    int updateByPrimaryKey(BankModel record);

    List<BankModel> loadBankList(Page<BankModel> page);

}
