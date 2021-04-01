package org.jeecg.modules.webAdmin.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.user.entity.AdminUser;
import org.jeecg.modules.webAdmin.user.mapper.AdminUserMapper;
import org.jeecg.modules.webAdmin.user.service.IAdminUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: tb_user
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Page queryPageList(Page page) {
        List<AdminUser> adminUsers = adminUserMapper.queryPageList(page);
        return page.setRecords(adminUsers);
    }
}
