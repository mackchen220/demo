package org.jeecg.modules.webAdmin.user.controller;

import java.util.Arrays;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.user.service.UserModelService;
import org.jeecg.modules.webAdmin.user.entity.AdminUser;
import org.jeecg.modules.webAdmin.user.service.IAdminUserService;

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
 * @Description: tb_user
 * @Author: jeecg-boot
 * @Date:   2021-03-05
 * @Version: V1.0
 */
@Api(tags="tb_user")
@RestController
@RequestMapping("/user/adminUser")
@Slf4j
public class AdminUserController extends JeecgController<AdminUser, IAdminUserService> {
	@Autowired
	private IAdminUserService adminUserService;

	@Resource
	private UserModelService userModelService;

	/**
	 * 分页列表查询
	 *
	 * @param adminUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tb_user-分页列表查询")
	@ApiOperation(value="tb_user-分页列表查询", notes="tb_user-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AdminUser adminUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
//		Map<String, String[]> parameterMap = req.getParameterMap();
//		String[] strs=new String[1];
//		strs[0]="0";
//		parameterMap.put("delFlag",strs);
//		log.info("参数{},dax{}", JSON.toJSONString(parameterMap),parameterMap.size());
		adminUser.setDelFlag(0);
		QueryWrapper<AdminUser> queryWrapper = QueryGenerator.initQueryWrapper(adminUser, req.getParameterMap());
		Page<AdminUser> page = new Page<AdminUser>(pageNo, pageSize);
		IPage<AdminUser> pageList = adminUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param adminUser
	 * @return
	 */
	@AutoLog(value = "tb_user-添加")
	@ApiOperation(value="tb_user-添加", notes="tb_user-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AdminUser adminUser) {
		adminUserService.save(adminUser);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param adminUser
	 * @return
	 */
	@AutoLog(value = "tb_user-编辑")
	@ApiOperation(value="tb_user-编辑", notes="tb_user-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AdminUser adminUser) {
		adminUserService.updateById(adminUser);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_user-通过id删除")
	@ApiOperation(value="tb_user-通过id删除", notes="tb_user-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		userModelService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tb_user-批量删除")
	@ApiOperation(value="tb_user-批量删除", notes="tb_user-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.adminUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tb_user-通过id查询")
	@ApiOperation(value="tb_user-通过id查询", notes="tb_user-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AdminUser adminUser = adminUserService.getById(id);
		if(adminUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(adminUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param adminUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AdminUser adminUser) {
        return super.exportXls(request, adminUser, AdminUser.class, "tb_user");
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
        return super.importExcel(request, response, AdminUser.class);
    }

}
