package org.jeecg.modules.webAdmin.user.service.impl;

import org.jeecg.modules.webAdmin.user.entity.AdminUser;
import org.jeecg.modules.webAdmin.user.mapper.AdminUserMapper;
import org.jeecg.modules.webAdmin.user.service.IAdminUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: tb_user
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {

}
