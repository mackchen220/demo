package org.jeecg.modules.webAdmin.talentHospital.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.talentHospital.entity.WebTalentHospital;
import org.jeecg.modules.webAdmin.talentHospital.service.IWebTalentHospitalService;

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
 * @Description: tb_talent_hospital
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Api(tags="tb_talent_hospital")
@RestController
@RequestMapping("/talentHospital/webTalentHospital")
@Slf4j
public class WebTalentHospitalController extends JeecgController<WebTalentHospital, IWebTalentHospitalService> {
	@Autowired
	private IWebTalentHospitalService webTalentHospitalService;

	/**
	 * 分页列表查询
	 *
	 * @param webTalentHospital
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "tb_talent_hospital-分页列表查询")
//	@ApiOperation(value="tb_talent_hospital-分页列表查询", notes="tb_talent_hospital-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<?> queryPageList(WebTalentHospital webTalentHospital,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<WebTalentHospital> queryWrapper = QueryGenerator.initQueryWrapper(webTalentHospital, req.getParameterMap());
//		Page<WebTalentHospital> page = new Page<WebTalentHospital>(pageNo, pageSize);
//		IPage<WebTalentHospital> pageList = webTalentHospitalService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}

	 @AutoLog(value = "tb_talent_hospital-分页列表查询")
	 @ApiOperation(value="tb_talent_hospital-分页列表查询", notes="tb_talent_hospital-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(WebTalentHospital webTalentHospital,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 Page<WebTalentHospital> page = new Page<WebTalentHospital>(pageNo, pageSize);
		 IPage<WebTalentHospital> pageList = webTalentHospitalService.queryPageList(page);
		 return Result.OK(pageList);
	 }


	/**
	 *   添加
	 *
	 * @param webTalentHospital
	 * @return
	 */
	@AutoLog(value = "tb_talent_hospital-添加")
	@ApiOperation(value="tb_talent_hospital-添加", notes="tb_talent_hospital-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WebTalentHospital webTalentHospital) {
		webTalentHospitalService.save(webTalentHospital);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param webTalentHospital
	 * @return
	 */
	@AutoLog(value = "tb_talent_hospital-编辑")
	@ApiOperation(value="tb_talent_hospital-编辑", notes="tb_talent_hospital-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WebTalentHospital webTalentHospital) {
		webTalentHospitalService.updateById(webTalentHospital);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_talent_hospital-通过id删除")
	@ApiOperation(value="tb_talent_hospital-通过id删除", notes="tb_talent_hospital-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		webTalentHospitalService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_talent_hospital-批量删除")
	@ApiOperation(value="tb_talent_hospital-批量删除", notes="tb_talent_hospital-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.webTalentHospitalService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_talent_hospital-通过id查询")
	@ApiOperation(value="tb_talent_hospital-通过id查询", notes="tb_talent_hospital-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WebTalentHospital webTalentHospital = webTalentHospitalService.getById(id);
		if(webTalentHospital==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(webTalentHospital);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param webTalentHospital
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WebTalentHospital webTalentHospital) {
        return super.exportXls(request, webTalentHospital, WebTalentHospital.class, "tb_talent_hospital");
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
        return super.importExcel(request, response, WebTalentHospital.class);
    }

}
