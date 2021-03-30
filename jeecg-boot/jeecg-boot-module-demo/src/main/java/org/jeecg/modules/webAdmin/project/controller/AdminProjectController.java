package org.jeecg.modules.webAdmin.project.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.project.entity.AdminProject;
import org.jeecg.modules.webAdmin.project.service.IAdminProjectService;

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
 * @Description: tb_project
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Api(tags="tb_project")
@RestController
@RequestMapping("/project/adminProject")
@Slf4j
public class AdminProjectController extends JeecgController<AdminProject, IAdminProjectService> {
	@Autowired
	private IAdminProjectService adminProjectService;

	/**
	 * 分页列表查询
	 *
	 * @param adminProject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tb_project-分页列表查询")
	@ApiOperation(value="tb_project-分页列表查询", notes="tb_project-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminProject adminProject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminProject> queryWrapper = QueryGenerator.initQueryWrapper(adminProject, req.getParameterMap());
		Page<AdminProject> page = new Page<AdminProject>(pageNo, pageSize);
		IPage<AdminProject> pageList = adminProjectService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminProject
	 * @return
	 */
	@AutoLog(value = "tb_project-添加")
	@ApiOperation(value="tb_project-添加", notes="tb_project-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminProject adminProject) {
		adminProjectService.save(adminProject);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminProject
	 * @return
	 */
	@AutoLog(value = "tb_project-编辑")
	@ApiOperation(value="tb_project-编辑", notes="tb_project-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminProject adminProject) {
		adminProjectService.updateById(adminProject);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_project-通过id删除")
	@ApiOperation(value="tb_project-通过id删除", notes="tb_project-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminProjectService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_project-批量删除")
	@ApiOperation(value="tb_project-批量删除", notes="tb_project-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminProjectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_project-通过id查询")
	@ApiOperation(value="tb_project-通过id查询", notes="tb_project-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminProject adminProject = adminProjectService.getById(id);
		if(adminProject==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminProject);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminProject
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminProject adminProject) {
        return super.exportXls(request, adminProject, AdminProject.class, "tb_project");
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
        return super.importExcel(request, response, AdminProject.class);
    }

}
