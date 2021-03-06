package org.jeecg.modules.webAdmin.party.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.party.entity.AdminParty;
import org.jeecg.modules.webAdmin.party.service.IAdminPartyService;

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
 * @Description: 社群活动
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Api(tags="社群活动")
@RestController
@RequestMapping("/party/adminParty")
@Slf4j
public class AdminPartyController extends JeecgController<AdminParty, IAdminPartyService> {
	@Autowired
	private IAdminPartyService adminPartyService;

	/**
	 * 分页列表查询
	 *
	 * @param adminParty
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "社群活动-分页列表查询")
	@ApiOperation(value="社群活动-分页列表查询", notes="社群活动-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminParty adminParty,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		adminParty.setDelFlag(0);
		QueryWrapper<AdminParty> queryWrapper = QueryGenerator.initQueryWrapper(adminParty, req.getParameterMap());
		Page<AdminParty> page = new Page<AdminParty>(pageNo, pageSize);
		IPage<AdminParty> pageList = adminPartyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminParty
	 * @return
	 */
	@AutoLog(value = "社群活动-添加")
	@ApiOperation(value="社群活动-添加", notes="社群活动-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminParty adminParty) {
		adminPartyService.save(adminParty);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminParty
	 * @return
	 */
	@AutoLog(value = "社群活动-编辑")
	@ApiOperation(value="社群活动-编辑", notes="社群活动-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminParty adminParty) {
		adminPartyService.updateById(adminParty);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "社群活动-通过id删除")
	@ApiOperation(value="社群活动-通过id删除", notes="社群活动-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminPartyService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "社群活动-批量删除")
	@ApiOperation(value="社群活动-批量删除", notes="社群活动-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminPartyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "社群活动-通过id查询")
	@ApiOperation(value="社群活动-通过id查询", notes="社群活动-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminParty adminParty = adminPartyService.getById(id);
		if(adminParty==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminParty);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminParty
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminParty adminParty) {
        return super.exportXls(request, adminParty, AdminParty.class, "社群活动");
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
        return super.importExcel(request, response, AdminParty.class);
    }

}
