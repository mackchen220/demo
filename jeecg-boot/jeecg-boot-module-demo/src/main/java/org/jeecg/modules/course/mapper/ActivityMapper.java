package org.jeecg.modules.course.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.course.model.Activity;

import java.util.List;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateSelective(Activity record);

    List<Activity> getListOrderByLikeNum(Page<Activity> page, @Param("city") String city);

}