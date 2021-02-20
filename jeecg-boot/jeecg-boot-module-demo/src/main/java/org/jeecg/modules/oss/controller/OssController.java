package org.jeecg.modules.oss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.RandomUtil;
import org.jeecg.modules.commons.util.SeqUtils;
import org.jeecg.modules.commons.util.ValidateTool;
import org.jeecg.modules.oss.config.OssClientHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: OssController
 * @description:
 * @author: LongXiang
 * @data: 2021-02-19 16:05
 * @version: V1.0
 */
@Slf4j
@Controller
@RequestMapping("/hy/upload")
@Api(tags = "前台文件上传模块")
public class OssController {

    private static final long LIMIT_SIZE = 2 * 1024 * 1024;

    @Resource
    private OssClientHandler ossClientHandler;

    @PostMapping("/uploadFile")
    @ApiOperation("文件上传接口")
    public Result<Map<String, Object>> upload(MultipartFile file) {
        if (ValidateTool.isNotNull(file)) {
            if (file.getSize() > LIMIT_SIZE) {
                throw new JeecgBootException(ErrorInfoCode.UPLOAD_FILE_OVER_LIMIT_ERROR.getMsg());
            }

            String fileSuffix = "";
            if (file.getOriginalFilename() != null) {
                int index = file.getOriginalFilename().lastIndexOf(".");
                fileSuffix = file.getOriginalFilename().substring(index);
            }
            String fileName = RandomUtil.nextNumberLetter(5, 0, true) + "_" + SeqUtils.nextId() + fileSuffix;

            Map<String, Object> rlt = new HashMap<>();
            try {
                String url = ossClientHandler.upload(fileName, file.getInputStream());
                rlt.put("url", url);
            } catch (IOException e) {
                log.error("uploadFile error.", e);
                throw new JeecgBootException(ErrorInfoCode.UPLOAD_FILE_ERROR.getMsg());
            }

            return Result.OK(rlt);
        } else {
            throw new JeecgBootException(ErrorInfoCode.UPLOAD_FILE_NOT_EXIST_ERROR.getMsg());
        }
    }

}
