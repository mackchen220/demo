package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserIncomeDetail;
import org.jeecg.modules.user.model.vo.UserIncomeDetailVo;

import java.util.List;

@Mapper
public interface UserIncomeDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserIncomeDetail record);

    int insertSelective(UserIncomeDetail record);

    UserIncomeDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserIncomeDetail record);

    int updateByPrimaryKey(UserIncomeDetail record);

    List<UserIncomeDetailVo> loadUserIncomeList(Page<UserIncomeDetailVo> page, @Param("userId")String userId , @Param("type") Integer type);

}
