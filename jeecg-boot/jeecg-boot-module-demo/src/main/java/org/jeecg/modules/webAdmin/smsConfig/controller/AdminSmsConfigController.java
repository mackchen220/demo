package org.jeecg.modules.webAdmin.smsConfig.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.smsConfig.entity.AdminSmsConfig;
import org.jeecg.modules.webAdmin.smsConfig.service.IAdminSmsConfigService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 短信配置
 * @Author: jeecg-boot
 * @Date:   2021-03-12
 * @Version: V1.0
 */
@Api(tags="短信配置")
@RestController
@RequestMapping("/smsConfig/adminSmsConfig")
@Slf4j
public class AdminSmsConfigController extends JeecgController<AdminSmsConfig, IAdminSmsConfigService> {
	@Autowired
	private IAdminSmsConfigService adminSmsConfigService;

	/**
	 * 分页列表查询
	 *
	 * @param adminSmsConfig
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "短信配置-分页列表查询")
	@ApiOperation(value="短信配置-分页列表查询", notes="短信配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminSmsConfig adminSmsConfig,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminSmsConfig> queryWrapper = QueryGenerator.initQueryWrapper(adminSmsConfig, req.getParameterMap());
		Page<AdminSmsConfig> page = new Page<AdminSmsConfig>(pageNo, pageSize);
		IPage<AdminSmsConfig> pageList = adminSmsConfigService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminSmsConfig
	 * @return
	 */
	@AutoLog(value = "短信配置-添加")
	@ApiOperation(value="短信配置-添加", notes="短信配置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminSmsConfig adminSmsConfig) {
		adminSmsConfigService.save(adminSmsConfig);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminSmsConfig
	 * @return
	 */
	@AutoLog(value = "短信配置-编辑")
	@ApiOperation(value="短信配置-编辑", notes="短信配置-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminSmsConfig adminSmsConfig) {
		adminSmsConfigService.updateById(adminSmsConfig);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "短信配置-通过id删除")
	@ApiOperation(value="短信配置-通过id删除", notes="短信配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminSmsConfigService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "短信配置-批量删除")
	@ApiOperation(value="短信配置-批量删除", notes="短信配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminSmsConfigService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "短信配置-通过id查询")
	@ApiOperation(value="短信配置-通过id查询", notes="短信配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminSmsConfig adminSmsConfig = adminSmsConfigService.getById(id);
		if(adminSmsConfig==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminSmsConfig);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminSmsConfig
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminSmsConfig adminSmsConfig) {
        return super.exportXls(request, adminSmsConfig, AdminSmsConfig.class, "短信配置");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AdminSmsConfig.class);
    }

}
