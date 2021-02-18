package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.model.AddressModel;
import org.jeecg.modules.user.model.vo.AddressModelVo;

import java.util.List;

@Mapper
public interface AddressModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(AddressModel record);

    int insertSelective(AddressModelVo record);

    AddressModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AddressModel record);

    int updateByPrimaryKey(AddressModel record);

    List<AddressModelVo> loadUserAddressList(@Param("userId") String userId);
}
