package org.jeecg.modules.oss.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.util.SeqUtils;
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
public class OssController {

    @Resource
    private OssClientHandler ossClientHandler;

    @PostMapping("/uploadFile")
    public Result<?> upload(MultipartFile file) {
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = "image_" + SeqUtils.nextId() + fileSuffix;

        Map<String, Object> rlt = new HashMap<>();
        try {
            String url = ossClientHandler.upload(fileName, file.getInputStream());
            rlt.put("url", url);
        } catch (IOException e) {
            log.error("uploadFile error.", e);
            throw new JeecgBootException(ErrorInfoCode.UPLOAD_FILE_ERROR.getMsg());
        }

        return Result.OK(rlt);
    }

}
