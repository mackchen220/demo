package org.jeecg.modules.webAdmin.talentHospital.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.talentHospital.entity.WebTalentHospital;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: tb_talent_hospital
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface WebTalentHospitalMapper extends BaseMapper<WebTalentHospital> {

    List<WebTalentHospital> queryPageList(Page page);
}
