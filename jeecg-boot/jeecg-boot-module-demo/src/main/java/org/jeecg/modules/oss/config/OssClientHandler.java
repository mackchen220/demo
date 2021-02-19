package org.jeecg.modules.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @className: OssClientHandler
 * @description:
 * @author: LongXiang
 * @data: 2021-02-19 19:31
 * @version: V1.0
 */
@Component
public class OssClientHandler {

    private OSS ossClient;

    @Value("${project.oss.aliyun.endpoint}")
    private String endpoint;
    @Value("${project.oss.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${project.oss.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${project.oss.aliyun.bucketName}")
    private String bucketName;

    @PostConstruct
    private void init() {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public String upload(String fileName, InputStream input) {
        ossClient.putObject(bucketName, fileName, input);
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, new Date());
        return url.toString().substring(0, url.toString().indexOf("?"));
    }

    @PreDestroy
    private void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

}
