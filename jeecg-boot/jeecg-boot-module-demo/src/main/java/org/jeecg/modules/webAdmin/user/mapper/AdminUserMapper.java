package org.jeecg.modules.webAdmin.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.webAdmin.user.entity.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: tb_user
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    List<AdminUser> queryPageList(Page page);
}
