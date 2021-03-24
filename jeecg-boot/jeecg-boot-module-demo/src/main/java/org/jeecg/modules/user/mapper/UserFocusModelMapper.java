package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserFocusModel;
import org.jeecg.modules.user.model.vo.UserModelVo;

import java.util.List;

@Mapper
public interface UserFocusModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFocusModel record);

    int insertSelective(UserFocusModel record);

    UserFocusModel selectById(@Param("userId") String userId,@Param("focusUserId") String focusUserId);

    int updateByPrimaryKeySelective(UserFocusModel record);

    int updateByPrimaryKey(UserFocusModel record);

    int updatedDelFlag(@Param("userId") String userId,@Param("focusUserId") String focusUserId,@Param("flag") String flag);


    int selectCountByFocusId(String userId);

    int selectUserFocus(@Param("userId") String userId, @Param("fansId") String fansId);

    List<UserModelVo> loadMyFocus(Page<UserModelVo> page, @Param("userId") String userId);


}
