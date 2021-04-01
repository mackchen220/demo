package org.jeecg.modules.webAdmin.talentInfo.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.talentInfo.entity.AdminTalentInfo;
import org.jeecg.modules.webAdmin.talentInfo.service.IAdminTalentInfoService;

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
 * @Description: tb_talent_info
 * @Author: jeecg-boot
 * @Date:   2021-03-30
 * @Version: V1.0
 */
@Api(tags="tb_talent_info")
@RestController
@RequestMapping("/talentInfo/adminTalentInfo")
@Slf4j
public class AdminTalentInfoController extends JeecgController<AdminTalentInfo, IAdminTalentInfoService> {
	@Autowired
	private IAdminTalentInfoService adminTalentInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param adminTalentInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
//	@AutoLog(value = "tb_talent_info-分页列表查询")
//	@ApiOperation(value="tb_talent_info-分页列表查询", notes="tb_talent_info-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<?> queryPageList(AdminTalentInfo adminTalentInfo,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<AdminTalentInfo> queryWrapper = QueryGenerator.initQueryWrapper(adminTalentInfo, req.getParameterMap());
//		Page<AdminTalentInfo> page = new Page<AdminTalentInfo>(pageNo, pageSize);
//		IPage<AdminTalentInfo> pageList = adminTalentInfoService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}
	@AutoLog(value = "tb_talent_info-分页列表查询")
	@ApiOperation(value="tb_talent_info-分页列表查询", notes="tb_talent_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminTalentInfo adminTalentInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<AdminTalentInfo> page = new Page<AdminTalentInfo>(pageNo, pageSize);
		IPage<AdminTalentInfo> pageList = adminTalentInfoService.queryPageList(page);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminTalentInfo
	 * @return
	 */
	@AutoLog(value = "tb_talent_info-添加")
	@ApiOperation(value="tb_talent_info-添加", notes="tb_talent_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminTalentInfo adminTalentInfo) {
		adminTalentInfoService.save(adminTalentInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminTalentInfo
	 * @return
	 */
	@AutoLog(value = "tb_talent_info-编辑")
	@ApiOperation(value="tb_talent_info-编辑", notes="tb_talent_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminTalentInfo adminTalentInfo) {
		adminTalentInfoService.updateById(adminTalentInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_talent_info-通过id删除")
	@ApiOperation(value="tb_talent_info-通过id删除", notes="tb_talent_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminTalentInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_talent_info-批量删除")
	@ApiOperation(value="tb_talent_info-批量删除", notes="tb_talent_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminTalentInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_talent_info-通过id查询")
	@ApiOperation(value="tb_talent_info-通过id查询", notes="tb_talent_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminTalentInfo adminTalentInfo = adminTalentInfoService.getById(id);
		if(adminTalentInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminTalentInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminTalentInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminTalentInfo adminTalentInfo) {
        return super.exportXls(request, adminTalentInfo, AdminTalentInfo.class, "tb_talent_info");
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
        return super.importExcel(request, response, AdminTalentInfo.class);
    }

}
