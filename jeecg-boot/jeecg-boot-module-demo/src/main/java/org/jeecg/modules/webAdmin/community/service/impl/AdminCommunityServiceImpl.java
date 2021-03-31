package org.jeecg.modules.webAdmin.community.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.community.entity.AdminCommunity;
import org.jeecg.modules.webAdmin.community.mapper.AdminCommunityMapper;
import org.jeecg.modules.webAdmin.community.service.IAdminCommunityService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 朋友圈
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
@Service
public class AdminCommunityServiceImpl extends ServiceImpl<AdminCommunityMapper, AdminCommunity> implements IAdminCommunityService {

    @Resource
    private AdminCommunityMapper adminCommunityMapper;
    @Override
    public Page loadCommunityList(Page page) {
        List<AdminCommunity> adminCommunities = adminCommunityMapper.loadCommunityList(page);
        return page.setRecords(adminCommunities);
    }
}
