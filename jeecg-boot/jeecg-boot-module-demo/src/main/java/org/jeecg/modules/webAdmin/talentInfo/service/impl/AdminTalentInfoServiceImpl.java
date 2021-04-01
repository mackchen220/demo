package org.jeecg.modules.webAdmin.talentInfo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.talentInfo.entity.AdminTalentInfo;
import org.jeecg.modules.webAdmin.talentInfo.mapper.AdminTalentInfoMapper;
import org.jeecg.modules.webAdmin.talentInfo.service.IAdminTalentInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import sun.management.Agent;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: tb_talent_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Service
public class AdminTalentInfoServiceImpl extends ServiceImpl<AdminTalentInfoMapper, AdminTalentInfo> implements IAdminTalentInfoService {

    @Resource
    private AdminTalentInfoMapper adminTalentInfoMapper;

    @Override
    public Page queryPageList(Page page) {
        List<AdminTalentInfo> adminTalentInfos = adminTalentInfoMapper.queryPageList(page);
        return page.setRecords(adminTalentInfos);
    }
}
