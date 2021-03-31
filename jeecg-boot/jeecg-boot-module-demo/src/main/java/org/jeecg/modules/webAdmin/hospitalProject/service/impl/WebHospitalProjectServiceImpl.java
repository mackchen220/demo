package org.jeecg.modules.webAdmin.hospitalProject.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.hospitalProject.entity.WebHospitalProject;
import org.jeecg.modules.webAdmin.hospitalProject.mapper.WebHospitalProjectMapper;
import org.jeecg.modules.webAdmin.hospitalProject.service.IWebHospitalProjectService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: tb_hospital_project
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Service
public class WebHospitalProjectServiceImpl extends ServiceImpl<WebHospitalProjectMapper, WebHospitalProject> implements IWebHospitalProjectService {

    @Resource
    private WebHospitalProjectMapper webHospitalProjectMapper;

    @Override
    public Page loadHospitalProjectList(Page page) {
        List<WebHospitalProject> webHospitalProjects = webHospitalProjectMapper.loadHospitalProjectList(page);
        return page.setRecords(webHospitalProjects);
    }
}
