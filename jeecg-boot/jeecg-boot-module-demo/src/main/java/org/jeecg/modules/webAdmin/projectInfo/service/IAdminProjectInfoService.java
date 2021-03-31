package org.jeecg.modules.webAdmin.projectInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.projectInfo.entity.AdminProjectInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: tb_project_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
public interface IAdminProjectInfoService extends IService<AdminProjectInfo> {

    Page loadProjectInfoList(Page page);

}
