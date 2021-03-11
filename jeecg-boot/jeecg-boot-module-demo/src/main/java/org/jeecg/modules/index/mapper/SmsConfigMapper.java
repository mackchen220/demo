package org.jeecg.modules.index.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.index.model.SmsConfig;

@Mapper
public interface SmsConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsConfig record);

    int insertSelective(SmsConfig record);

    SmsConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsConfig record);

    int updateByPrimaryKey(SmsConfig record);


    SmsConfig getSmsConfig();



}
