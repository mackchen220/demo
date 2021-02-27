package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserIncome;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;

import java.util.List;

@Mapper
public interface UserIncomeMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserIncome record);

    int insertSelective(UserIncome record);

    UserIncome selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserIncome record);

    int updateByPrimaryKey(UserIncome record);

    String getIncomeByTime(@Param("userId") String userId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    UserIncome getUserIncome(@Param("seqStr") String seqStr);

}
