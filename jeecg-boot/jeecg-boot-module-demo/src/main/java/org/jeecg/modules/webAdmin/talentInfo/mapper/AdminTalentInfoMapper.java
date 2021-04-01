package org.jeecg.modules.webAdmin.talentInfo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.talentInfo.entity.AdminTalentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: tb_talent_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface AdminTalentInfoMapper extends BaseMapper<AdminTalentInfo> {

    List<AdminTalentInfo> queryPageList(Page page);
}
