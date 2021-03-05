package org.jeecg.modules.webAdmin.turnImage.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.webAdmin.turnImage.entity.AdminTurnImage;
import org.jeecg.modules.webAdmin.turnImage.service.IAdminTurnImageService;

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
 * @Description: 轮播图
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Api(tags="轮播图")
@RestController
@RequestMapping("/turnImage/adminTurnImage")
@Slf4j
public class AdminTurnImageController extends JeecgController<AdminTurnImage, IAdminTurnImageService> {
	@Autowired
	private IAdminTurnImageService adminTurnImageService;

	/**
	 * 分页列表查询
	 *
	 * @param adminTurnImage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "轮播图-分页列表查询")
	@ApiOperation(value="轮播图-分页列表查询", notes="轮播图-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminTurnImage adminTurnImage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AdminTurnImage> queryWrapper = QueryGenerator.initQueryWrapper(adminTurnImage, req.getParameterMap());
		Page<AdminTurnImage> page = new Page<AdminTurnImage>(pageNo, pageSize);
		IPage<AdminTurnImage> pageList = adminTurnImageService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminTurnImage
	 * @return
	 */
	@AutoLog(value = "轮播图-添加")
	@ApiOperation(value="轮播图-添加", notes="轮播图-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminTurnImage adminTurnImage) {
		adminTurnImageService.save(adminTurnImage);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminTurnImage
	 * @return
	 */
	@AutoLog(value = "轮播图-编辑")
	@ApiOperation(value="轮播图-编辑", notes="轮播图-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminTurnImage adminTurnImage) {
		adminTurnImageService.updateById(adminTurnImage);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "轮播图-通过id删除")
	@ApiOperation(value="轮播图-通过id删除", notes="轮播图-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		adminTurnImageService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "轮播图-批量删除")
	@ApiOperation(value="轮播图-批量删除", notes="轮播图-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminTurnImageService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "轮播图-通过id查询")
	@ApiOperation(value="轮播图-通过id查询", notes="轮播图-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminTurnImage adminTurnImage = adminTurnImageService.getById(id);
		if(adminTurnImage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminTurnImage);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminTurnImage
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminTurnImage adminTurnImage) {
        return super.exportXls(request, adminTurnImage, AdminTurnImage.class, "轮播图");
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
        return super.importExcel(request, response, AdminTurnImage.class);
    }

}
