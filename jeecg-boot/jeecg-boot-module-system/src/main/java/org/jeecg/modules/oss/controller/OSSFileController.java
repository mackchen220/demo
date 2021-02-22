package org.jeecg.modules.oss.controller;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.oss.entity.OSSFile;
import org.jeecg.modules.oss.service.IOSSFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@Api(tags = "文件上传模块")
@RequestMapping("/sys/oss/file")
public class OSSFileController {

	private static final long LIMIT_SIZE = 10 * 1024 * 1024;

	@Autowired
	private IOSSFileService ossFileService;

	@ResponseBody
	@GetMapping("/list")
	public Result<IPage<OSSFile>> queryPageList(OSSFile file,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
		Result<IPage<OSSFile>> result = new Result<>();
		QueryWrapper<OSSFile> queryWrapper = QueryGenerator.initQueryWrapper(file, req.getParameterMap());
		Page<OSSFile> page = new Page<>(pageNo, pageSize);
		IPage<OSSFile> pageList = ossFileService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@ResponseBody
	@PostMapping("/upload")
	@ApiOperation("文件上传接口")
	//@RequiresRoles("admin")
	public Result upload(@RequestParam("file") MultipartFile multipartFile) {
		if (ValidateTool.isNotNull(multipartFile)) {
			if (multipartFile.getSize() > LIMIT_SIZE) {
				throw new JeecgBootException(ErrorInfoCode.UPLOAD_FILE_OVER_LIMIT_ERROR.getMsg());
			}

			Result<Map<String, String>> result = new Result<>();
			try {
				String url = ossFileService.upload(multipartFile);
				result.success("上传成功！");
				Map<String, String> data = new HashMap<>(1);
				data.put("url", url);
				result.setResult(data);
			} catch (Exception ex) {
				log.info(ex.getMessage(), ex);
				result.error500("上传失败");
			}
			return result;
		} else {
			throw new JeecgBootException(ErrorInfoCode.UPLOAD_FILE_NOT_EXIST_ERROR.getMsg());
		}
	}

	@ResponseBody
	@DeleteMapping("/delete")
	public Result delete(@RequestParam(name = "id") String id) {
		Result result = new Result();
		OSSFile file = ossFileService.getById(id);
		if (file == null) {
			result.error500("未找到对应实体");
		}
		else {
			boolean ok = ossFileService.delete(file);
			if (ok) {
				result.success("删除成功!");
			}
		}
		return result;
	}

	/**
	 * 通过id查询.
	 */
	@ResponseBody
	@GetMapping("/queryById")
	public Result<OSSFile> queryById(@RequestParam(name = "id") String id) {
		Result<OSSFile> result = new Result<>();
		OSSFile file = ossFileService.getById(id);
		if (file == null) {
			result.error500("未找到对应实体");
		}
		else {
			result.setResult(file);
			result.setSuccess(true);
		}
		return result;
	}

}
