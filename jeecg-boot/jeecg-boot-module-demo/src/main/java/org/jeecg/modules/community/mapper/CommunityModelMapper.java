package org.jeecg.modules.community.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.community.model.CommunityModel;
import org.jeecg.modules.community.model.vo.CommunityModelVo;

import java.util.List;

public interface CommunityModelMapper{
    int deleteByPrimaryKey(String id);

    int insertSelective(CommunityModel record);

    CommunityModel selectByPrimaryKey(String id);

    int updateSelective(CommunityModel record);

    int updateByPrimaryKey(CommunityModel record);

    //朋友圈列表
    List<CommunityModelVo> loadCommunityListByType(Page<CommunityModelVo> page, @Param("type") int type, @Param("userId") String userId);


    //朋友圈列表达人
    List<CommunityModelVo> loadTalentCommunity(Page<CommunityModelVo> page, @Param("userId") String userId);


    List<CommunityModel> getListByUserId(Page<CommunityModel> page, @Param("userId") String userId);

    List<CommunityModelVo> selectByFocusUserId(Page<CommunityModelVo> page, @Param("userId") String userId);

    List<CommunityModelVo> getListOrderByLikeNum(Page<CommunityModelVo> page, @Param("type") Integer type, @Param("city") String city);

    //更新朋友圈表收藏点赞转发观看数
    int updateCommunityNum(@Param("communityId") String communityId,@Param("userId") String userId,
                            @Param("watchNum")Integer watchNum ,@Param("starNum")Integer starNum,
                            @Param("goodNum")Integer goodNum,@Param("forwardNum")Integer forwardNum);


    //我的点赞和收藏朋友圈列表
    List<CommunityModelVo> loadStarAndGoodCommunityList(Page<CommunityModelVo> page, @Param("type") int type, @Param("userId") String userId);

    List<CommunityModelVo> loadCommunityBySearch(Page<CommunityModelVo> page, @Param("type") Integer type,
                                                   @Param("search") String search, @Param("sortmodel") Integer sortmodel,
                                                 @Param("userId") String userId);



}
