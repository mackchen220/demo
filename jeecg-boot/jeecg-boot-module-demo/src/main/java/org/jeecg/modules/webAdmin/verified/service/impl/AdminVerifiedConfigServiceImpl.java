package org.jeecg.modules.webAdmin.verified.service.impl;

import org.jeecg.modules.webAdmin.verified.entity.AdminVerifiedConfig;
import org.jeecg.modules.webAdmin.verified.mapper.AdminVerifiedConfigMapper;
import org.jeecg.modules.webAdmin.verified.service.IAdminVerifiedConfigService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 实名认证配置
 * @Author: jeecg-boot
 * @Date:   2021-03-12
 * @Version: V1.0
 */
@Service
public class AdminVerifiedConfigServiceImpl extends ServiceImpl<AdminVerifiedConfigMapper, AdminVerifiedConfig> implements IAdminVerifiedConfigService {

}
