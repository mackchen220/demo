package org.jeecg.modules.webAdmin.bank.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.bank.entity.AdminBank;
import org.jeecg.modules.webAdmin.bank.service.IAdminBankService;

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
 * @Description: 银行卡管理
 * @Author: jeecg-boot
 * @Date:   2021-03-15
 * @Version: V1.0
 */
@Api(tags="银行卡管理")
@RestController
@RequestMapping("/bank/adminBank")
@Slf4j
public class AdminBankController extends JeecgController<AdminBank, IAdminBankService> {
	@Autowired
	private IAdminBankService adminBankService;

	/**
	 * 分页列表查询
	 *
	 * @param adminBank
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "银行卡管理-分页列表查询")
	@ApiOperation(value="银行卡管理-分页列表查询", notes="银行卡管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminBank adminBank,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminBank> queryWrapper = QueryGenerator.initQueryWrapper(adminBank, req.getParameterMap());
		Page<AdminBank> page = new Page<AdminBank>(pageNo, pageSize);
		IPage<AdminBank> pageList = adminBankService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminBank
	 * @return
	 */
	@AutoLog(value = "银行卡管理-添加")
	@ApiOperation(value="银行卡管理-添加", notes="银行卡管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminBank adminBank) {
		adminBankService.save(adminBank);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminBank
	 * @return
	 */
	@AutoLog(value = "银行卡管理-编辑")
	@ApiOperation(value="银行卡管理-编辑", notes="银行卡管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminBank adminBank) {
		adminBankService.updateById(adminBank);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "银行卡管理-通过id删除")
	@ApiOperation(value="银行卡管理-通过id删除", notes="银行卡管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminBankService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "银行卡管理-批量删除")
	@ApiOperation(value="银行卡管理-批量删除", notes="银行卡管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminBankService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "银行卡管理-通过id查询")
	@ApiOperation(value="银行卡管理-通过id查询", notes="银行卡管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminBank adminBank = adminBankService.getById(id);
		if(adminBank==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminBank);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminBank
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminBank adminBank) {
        return super.exportXls(request, adminBank, AdminBank.class, "银行卡管理");
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
        return super.importExcel(request, response, AdminBank.class);
    }

}
