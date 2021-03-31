package org.jeecg.modules.webAdmin.projectInfo.service.impl;

import cn.hutool.captcha.generator.AbstractGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.projectInfo.entity.AdminProjectInfo;
import org.jeecg.modules.webAdmin.projectInfo.mapper.AdminProjectInfoMapper;
import org.jeecg.modules.webAdmin.projectInfo.service.IAdminProjectInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: tb_project_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Service
public class AdminProjectInfoServiceImpl extends ServiceImpl<AdminProjectInfoMapper, AdminProjectInfo> implements IAdminProjectInfoService {

    @Resource
    private AdminProjectInfoMapper adminProjectInfoMapper;

    @Override
    public Page loadProjectInfoList(Page page) {
        List<AdminProjectInfo> adminProjectInfos = adminProjectInfoMapper.loadProjectInfoList(page);
        return page.setRecords(adminProjectInfos);
    }
}
