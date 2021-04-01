package org.jeecg.modules.webAdmin.talentHospital.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.talentHospital.entity.WebTalentHospital;
import org.jeecg.modules.webAdmin.talentHospital.mapper.WebTalentHospitalMapper;
import org.jeecg.modules.webAdmin.talentHospital.service.IWebTalentHospitalService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: tb_talent_hospital
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Service
public class WebTalentHospitalServiceImpl extends ServiceImpl<WebTalentHospitalMapper, WebTalentHospital> implements IWebTalentHospitalService {

    @Resource
    private WebTalentHospitalMapper webTalentHospitalMapper;

    @Override
    public Page queryPageList(Page page) {
        List<WebTalentHospital> webTalentHospitals = webTalentHospitalMapper.queryPageList(page);
        return page.setRecords(webTalentHospitals);
    }
}
