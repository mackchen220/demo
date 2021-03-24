package org.jeecg.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.model.VipModel;
import org.jeecg.modules.user.model.vo.VipModelVo;

import java.util.List;

@Mapper
public interface VipModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(VipModel record);

    int insertSelective(VipModel record);

    VipModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VipModel record);

    int updateByPrimaryKey(VipModel record);

    List<VipModelVo> getVipList();

    int updateNum(String id);


}
