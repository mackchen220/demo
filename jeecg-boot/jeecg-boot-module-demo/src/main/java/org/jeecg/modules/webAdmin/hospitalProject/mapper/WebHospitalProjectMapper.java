package org.jeecg.modules.webAdmin.hospitalProject.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.hospitalProject.entity.WebHospitalProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: tb_hospital_project
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface WebHospitalProjectMapper extends BaseMapper<WebHospitalProject> {

    List<WebHospitalProject> loadHospitalProjectList(Page page);


}
