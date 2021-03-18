package org.jeecg.modules.webAdmin.vip.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.vip.entity.AdminVip;
import org.jeecg.modules.webAdmin.vip.service.IAdminVipService;

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
 * @Description: 会员设置
 * @Author: jeecg-boot
 * @Date:   2021-03-17
 * @Version: V1.0
 */
@Api(tags="会员设置")
@RestController
@RequestMapping("/vip/adminVip")
@Slf4j
public class AdminVipController extends JeecgController<AdminVip, IAdminVipService> {
	@Autowired
	private IAdminVipService adminVipService;

	/**
	 * 分页列表查询
	 *
	 * @param adminVip
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会员设置-分页列表查询")
	@ApiOperation(value="会员设置-分页列表查询", notes="会员设置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminVip adminVip,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminVip> queryWrapper = QueryGenerator.initQueryWrapper(adminVip, req.getParameterMap());
		Page<AdminVip> page = new Page<AdminVip>(pageNo, pageSize);
		IPage<AdminVip> pageList = adminVipService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminVip
	 * @return
	 */
	@AutoLog(value = "会员设置-添加")
	@ApiOperation(value="会员设置-添加", notes="会员设置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminVip adminVip) {
		adminVipService.save(adminVip);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminVip
	 * @return
	 */
	@AutoLog(value = "会员设置-编辑")
	@ApiOperation(value="会员设置-编辑", notes="会员设置-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminVip adminVip) {
		adminVipService.updateById(adminVip);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会员设置-通过id删除")
	@ApiOperation(value="会员设置-通过id删除", notes="会员设置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminVipService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "会员设置-批量删除")
	@ApiOperation(value="会员设置-批量删除", notes="会员设置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminVipService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会员设置-通过id查询")
	@ApiOperation(value="会员设置-通过id查询", notes="会员设置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminVip adminVip = adminVipService.getById(id);
		if(adminVip==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminVip);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminVip
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminVip adminVip) {
        return super.exportXls(request, adminVip, AdminVip.class, "会员设置");
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
        return super.importExcel(request, response, AdminVip.class);
    }

}
