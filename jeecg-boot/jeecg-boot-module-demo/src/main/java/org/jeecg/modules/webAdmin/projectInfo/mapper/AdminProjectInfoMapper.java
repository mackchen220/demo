package org.jeecg.modules.webAdmin.projectInfo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.projectInfo.entity.AdminProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: tb_project_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface AdminProjectInfoMapper extends BaseMapper<AdminProjectInfo> {

    List<AdminProjectInfo>  loadProjectInfoList(Page page);

}
