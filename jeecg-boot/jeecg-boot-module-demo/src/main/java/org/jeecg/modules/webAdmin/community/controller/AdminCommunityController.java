package org.jeecg.modules.webAdmin.community.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.community.entity.AdminCommunity;
import org.jeecg.modules.webAdmin.community.service.IAdminCommunityService;

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
 * @Description: 朋友圈
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
@Api(tags="朋友圈")
@RestController
@RequestMapping("/community/adminCommunity")
@Slf4j
public class AdminCommunityController extends JeecgController<AdminCommunity, IAdminCommunityService> {
	@Autowired
	private IAdminCommunityService adminCommunityService;

	/**
	 * 分页列表查询
	 *
	 * @param adminCommunity
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "朋友圈-分页列表查询")
//	@ApiOperation(value="朋友圈-分页列表查询", notes="朋友圈-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<?> queryPageList(AdminCommunity adminCommunity,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		adminCommunity.setDelFlag(0);
//		QueryWrapper<AdminCommunity> queryWrapper = QueryGenerator.initQueryWrapper(adminCommunity, req.getParameterMap());
//		Page<AdminCommunity> page = new Page<AdminCommunity>(pageNo, pageSize);
//		IPage<AdminCommunity> pageList = adminCommunityService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}

	@AutoLog(value = "朋友圈-分页列表查询")
	@ApiOperation(value="朋友圈-分页列表查询", notes="朋友圈-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminCommunity adminCommunity,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<AdminCommunity> page = new Page<AdminCommunity>(pageNo, pageSize);
		IPage<AdminCommunity> pageList = adminCommunityService.loadCommunityList(page);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminCommunity
	 * @return
	 */
	@AutoLog(value = "朋友圈-添加")
	@ApiOperation(value="朋友圈-添加", notes="朋友圈-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminCommunity adminCommunity) {
		adminCommunityService.save(adminCommunity);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminCommunity
	 * @return
	 */
	@AutoLog(value = "朋友圈-编辑")
	@ApiOperation(value="朋友圈-编辑", notes="朋友圈-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminCommunity adminCommunity) {
		adminCommunityService.updateById(adminCommunity);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "朋友圈-通过id删除")
	@ApiOperation(value="朋友圈-通过id删除", notes="朋友圈-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminCommunityService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "朋友圈-批量删除")
	@ApiOperation(value="朋友圈-批量删除", notes="朋友圈-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminCommunityService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "朋友圈-通过id查询")
	@ApiOperation(value="朋友圈-通过id查询", notes="朋友圈-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminCommunity adminCommunity = adminCommunityService.getById(id);
		if(adminCommunity==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminCommunity);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminCommunity
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminCommunity adminCommunity) {
        return super.exportXls(request, adminCommunity, AdminCommunity.class, "朋友圈");
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
        return super.importExcel(request, response, AdminCommunity.class);
    }

}
