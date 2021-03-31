package org.jeecg.modules.webAdmin.hospitalProject.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.hospitalProject.entity.WebHospitalProject;
import org.jeecg.modules.webAdmin.hospitalProject.service.IWebHospitalProjectService;

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
 * @Description: tb_hospital_project
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Api(tags="tb_hospital_project")
@RestController
@RequestMapping("/hospitalProject/webHospitalProject")
@Slf4j
public class WebHospitalProjectController extends JeecgController<WebHospitalProject, IWebHospitalProjectService> {
	@Autowired
	private IWebHospitalProjectService webHospitalProjectService;

	/**
	 * 分页列表查询
	 *
	 * @param webHospitalProject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "tb_hospital_project-分页列表查询")
//	@ApiOperation(value="tb_hospital_project-分页列表查询", notes="tb_hospital_project-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<?> queryPageList(WebHospitalProject webHospitalProject,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<WebHospitalProject> queryWrapper = QueryGenerator.initQueryWrapper(webHospitalProject, req.getParameterMap());
//		Page<WebHospitalProject> page = new Page<WebHospitalProject>(pageNo, pageSize);
//		IPage<WebHospitalProject> pageList = webHospitalProjectService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}

	@AutoLog(value = "tb_hospital_project-分页列表查询")
	@ApiOperation(value="tb_hospital_project-分页列表查询", notes="tb_hospital_project-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WebHospitalProject webHospitalProject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<WebHospitalProject> page = new Page<WebHospitalProject>(pageNo, pageSize);
		IPage<WebHospitalProject> pageList = webHospitalProjectService.loadHospitalProjectList(page);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param webHospitalProject
	 * @return
	 */
	@AutoLog(value = "tb_hospital_project-添加")
	@ApiOperation(value="tb_hospital_project-添加", notes="tb_hospital_project-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WebHospitalProject webHospitalProject) {
		webHospitalProjectService.save(webHospitalProject);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param webHospitalProject
	 * @return
	 */
	@AutoLog(value = "tb_hospital_project-编辑")
	@ApiOperation(value="tb_hospital_project-编辑", notes="tb_hospital_project-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WebHospitalProject webHospitalProject) {
		webHospitalProjectService.updateById(webHospitalProject);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_hospital_project-通过id删除")
	@ApiOperation(value="tb_hospital_project-通过id删除", notes="tb_hospital_project-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		webHospitalProjectService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_hospital_project-批量删除")
	@ApiOperation(value="tb_hospital_project-批量删除", notes="tb_hospital_project-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.webHospitalProjectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_hospital_project-通过id查询")
	@ApiOperation(value="tb_hospital_project-通过id查询", notes="tb_hospital_project-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WebHospitalProject webHospitalProject = webHospitalProjectService.getById(id);
		if(webHospitalProject==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(webHospitalProject);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param webHospitalProject
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WebHospitalProject webHospitalProject) {
        return super.exportXls(request, webHospitalProject, WebHospitalProject.class, "tb_hospital_project");
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
        return super.importExcel(request, response, WebHospitalProject.class);
    }

}
