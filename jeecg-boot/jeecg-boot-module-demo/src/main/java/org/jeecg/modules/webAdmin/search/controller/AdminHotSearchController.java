package org.jeecg.modules.webAdmin.search.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.search.entity.AdminHotSearch;
import org.jeecg.modules.webAdmin.search.service.IAdminHotSearchService;

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
 * @Description: 热搜词管理
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Api(tags="热搜词管理")
@RestController
@RequestMapping("/search/adminHotSearch")
@Slf4j
public class AdminHotSearchController extends JeecgController<AdminHotSearch, IAdminHotSearchService> {
	@Autowired
	private IAdminHotSearchService adminHotSearchService;

	/**
	 * 分页列表查询
	 *
	 * @param adminHotSearch
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "热搜词管理-分页列表查询")
	@ApiOperation(value="热搜词管理-分页列表查询", notes="热搜词管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminHotSearch adminHotSearch,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminHotSearch> queryWrapper = QueryGenerator.initQueryWrapper(adminHotSearch, req.getParameterMap());
		Page<AdminHotSearch> page = new Page<AdminHotSearch>(pageNo, pageSize);
		IPage<AdminHotSearch> pageList = adminHotSearchService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminHotSearch
	 * @return
	 */
	@AutoLog(value = "热搜词管理-添加")
	@ApiOperation(value="热搜词管理-添加", notes="热搜词管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminHotSearch adminHotSearch) {
		adminHotSearchService.save(adminHotSearch);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminHotSearch
	 * @return
	 */
	@AutoLog(value = "热搜词管理-编辑")
	@ApiOperation(value="热搜词管理-编辑", notes="热搜词管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminHotSearch adminHotSearch) {
		adminHotSearchService.updateById(adminHotSearch);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "热搜词管理-通过id删除")
	@ApiOperation(value="热搜词管理-通过id删除", notes="热搜词管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminHotSearchService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "热搜词管理-批量删除")
	@ApiOperation(value="热搜词管理-批量删除", notes="热搜词管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminHotSearchService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "热搜词管理-通过id查询")
	@ApiOperation(value="热搜词管理-通过id查询", notes="热搜词管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminHotSearch adminHotSearch = adminHotSearchService.getById(id);
		if(adminHotSearch==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminHotSearch);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminHotSearch
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminHotSearch adminHotSearch) {
        return super.exportXls(request, adminHotSearch, AdminHotSearch.class, "热搜词管理");
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
        return super.importExcel(request, response, AdminHotSearch.class);
    }

}
