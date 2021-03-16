package org.jeecg.modules.index.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.index.model.PlatformConfiguration;

@Mapper
public interface PlatformConfigurationMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlatformConfiguration record);

    int insertSelective(PlatformConfiguration record);

    PlatformConfiguration selectByPrimaryKey(String id);

    PlatformConfiguration getConfigByKey(@Param("key") String key);

    int updateByPrimaryKeySelective(PlatformConfiguration record);

    int updateByPrimaryKey(PlatformConfiguration record);


}
