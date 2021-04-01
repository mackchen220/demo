package org.jeecg.modules.webAdmin.talentHospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.talentHospital.entity.WebTalentHospital;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: tb_talent_hospital
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface IWebTalentHospitalService extends IService<WebTalentHospital> {

    Page queryPageList(Page page);
}
