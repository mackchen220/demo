package org.jeecg.modules.webAdmin.verified.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.verified.entity.AdminVerifiedConfig;
import org.jeecg.modules.webAdmin.verified.service.IAdminVerifiedConfigService;

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
 * @Description: 实名认证配置
 * @Author: jeecg-boot
 * @Date:   2021-03-12
 * @Version: V1.0
 */
@Api(tags="实名认证配置")
@RestController
@RequestMapping("/verified/adminVerifiedConfig")
@Slf4j
public class AdminVerifiedConfigController extends JeecgController<AdminVerifiedConfig, IAdminVerifiedConfigService> {
	@Autowired
	private IAdminVerifiedConfigService adminVerifiedConfigService;

	/**
	 * 分页列表查询
	 *
	 * @param adminVerifiedConfig
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "实名认证配置-分页列表查询")
	@ApiOperation(value="实名认证配置-分页列表查询", notes="实名认证配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminVerifiedConfig adminVerifiedConfig,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminVerifiedConfig> queryWrapper = QueryGenerator.initQueryWrapper(adminVerifiedConfig, req.getParameterMap());
		Page<AdminVerifiedConfig> page = new Page<AdminVerifiedConfig>(pageNo, pageSize);
		IPage<AdminVerifiedConfig> pageList = adminVerifiedConfigService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminVerifiedConfig
	 * @return
	 */
	@AutoLog(value = "实名认证配置-添加")
	@ApiOperation(value="实名认证配置-添加", notes="实名认证配置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminVerifiedConfig adminVerifiedConfig) {
		adminVerifiedConfigService.save(adminVerifiedConfig);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminVerifiedConfig
	 * @return
	 */
	@AutoLog(value = "实名认证配置-编辑")
	@ApiOperation(value="实名认证配置-编辑", notes="实名认证配置-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminVerifiedConfig adminVerifiedConfig) {
		adminVerifiedConfigService.updateById(adminVerifiedConfig);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "实名认证配置-通过id删除")
	@ApiOperation(value="实名认证配置-通过id删除", notes="实名认证配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminVerifiedConfigService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "实名认证配置-批量删除")
	@ApiOperation(value="实名认证配置-批量删除", notes="实名认证配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminVerifiedConfigService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "实名认证配置-通过id查询")
	@ApiOperation(value="实名认证配置-通过id查询", notes="实名认证配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminVerifiedConfig adminVerifiedConfig = adminVerifiedConfigService.getById(id);
		if(adminVerifiedConfig==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminVerifiedConfig);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminVerifiedConfig
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminVerifiedConfig adminVerifiedConfig) {
        return super.exportXls(request, adminVerifiedConfig, AdminVerifiedConfig.class, "实名认证配置");
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
        return super.importExcel(request, response, AdminVerifiedConfig.class);
    }

}
