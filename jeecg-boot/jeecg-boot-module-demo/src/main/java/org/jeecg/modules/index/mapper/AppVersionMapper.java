package org.jeecg.modules.index.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.index.model.AppVersion;

@Mapper
public interface AppVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);

    AppVersion loadNewAppVersion();
}
