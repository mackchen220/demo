package org.jeecg.modules.webAdmin.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.community.entity.AdminCommunity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 朋友圈
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
public interface IAdminCommunityService extends IService<AdminCommunity> {

    Page loadCommunityList(Page page);
}
