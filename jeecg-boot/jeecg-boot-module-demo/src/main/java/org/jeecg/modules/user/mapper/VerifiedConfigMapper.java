package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.VerifiedConfig;

@Mapper
public interface VerifiedConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(VerifiedConfig record);

    int insertSelective(VerifiedConfig record);


    VerifiedConfig getConfig();


    VerifiedConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VerifiedConfig record);

    int updateByPrimaryKey(VerifiedConfig record);


}
