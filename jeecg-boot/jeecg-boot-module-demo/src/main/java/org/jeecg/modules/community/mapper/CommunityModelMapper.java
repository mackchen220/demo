package org.jeecg.modules.community.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.community.model.CommunityModel;

import java.util.List;

public interface CommunityModelMapper{
    int deleteByPrimaryKey(String id);

    int insert(CommunityModel record);

    int insertSelective(CommunityModel record);

    CommunityModel selectByPrimaryKey(String id);

    int updateSelective(CommunityModel record);

    int updateByPrimaryKey(CommunityModel record);

    List<CommunityModel> loadCommunityListByType(Page<CommunityModel> page,@Param("type") int type);

    List<CommunityModel> getListByUserId(Page<CommunityModel> page, @Param("userId") String userId);

    List<CommunityModel> selectByFocusUserId(Page<CommunityModel> page, @Param("userId") String userId);

    List<CommunityModel> getListOrderByLikeNum(Page<CommunityModel> page, @Param("type") Integer type, @Param("city") String city);

    //更新朋友圈表收藏点赞转发观看数
    int updateCommunityNum(@Param("communityId") String communityId,@Param("userId") String userId,
                            @Param("watchNum")Integer watchNum ,@Param("starNum")Integer starNum,
                            @Param("goodNum")Integer goodNum,@Param("forwardNum")Integer forwardNum);

}
