package org.jeecg.modules.webAdmin.hospitalProject.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.hospitalProject.entity.WebHospitalProject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: tb_hospital_project
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface IWebHospitalProjectService extends IService<WebHospitalProject> {

    Page loadHospitalProjectList(Page page);
}
