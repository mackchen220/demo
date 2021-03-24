package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.UserAgencyModel;
import org.jeecg.modules.user.model.vo.ExtensionVo;

import java.util.List;

@Mapper
public interface UserAgencyModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserAgencyModel record);

    int insertSelective(UserAgencyModel record);

    UserAgencyModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserAgencyModel record);

    int updateByPrimaryKey(UserAgencyModel record);

    int updateNum(@Param("id") String id);

    UserAgencyModel loadUserAgency(@Param("userId") String userId,@Param("puserId") String puserId);


    String countUserNum(@Param("userId") String userId);

    List<String> loadUserId(@Param("userId") String userId);

    List<ExtensionVo> loadProxyIncome(Page<ExtensionVo> page , @Param("userId") String userId);


}
