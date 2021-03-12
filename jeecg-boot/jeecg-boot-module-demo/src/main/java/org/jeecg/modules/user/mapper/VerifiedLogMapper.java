package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.VerifiedLog;

@Mapper
public interface VerifiedLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(VerifiedLog record);

    int insertSelective(VerifiedLog record);

    VerifiedLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VerifiedLog record);

    int updateByPrimaryKey(VerifiedLog record);
}