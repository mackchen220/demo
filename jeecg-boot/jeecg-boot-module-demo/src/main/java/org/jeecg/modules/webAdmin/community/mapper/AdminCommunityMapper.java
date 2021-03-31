package org.jeecg.modules.webAdmin.community.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.community.entity.AdminCommunity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: 朋友圈
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
public interface AdminCommunityMapper extends BaseMapper<AdminCommunity> {

    List<AdminCommunity> loadCommunityList(Page page);
}
