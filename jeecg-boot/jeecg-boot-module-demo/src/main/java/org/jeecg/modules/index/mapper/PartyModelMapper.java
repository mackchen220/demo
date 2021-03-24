package org.jeecg.modules.index.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.index.model.PartyModel;

import java.util.List;

@Mapper
public interface PartyModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(PartyModel record);

    int insertSelective(PartyModel record);

    PartyModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PartyModel record);

    int updateByPrimaryKey(PartyModel record);

    //更新活动表收藏点赞转发观看数
    int updatePartyNum(@Param("id") String id, @Param("watchNum")Integer watchNum ,@Param("starNum")Integer starNum,
                           @Param("goodNum")Integer goodNum,@Param("forwardNum")Integer forwardNum);

    List<PartyModel> loadPartyList(Page<PartyModel> page, @Param("userId") String userId);

    List<PartyModel> getListOrderByLikeNum(Page<PartyModel> page, @Param("city") String city);

    List<PartyModel> loadHengYangPartyList(@Param("limit")String limit);

}
