package org.jeecg.modules.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: gongxun
 * @BelongsPackage: com.gongxunyoupin.bestpay.pay
 * @Author: lzx
 * @CreateTime: 2019-01-08 18:23
 * @Description: ${Description}
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
    private String mchId;
    private String mchKey;
    private String keyPath;
    private String notifyUrl;
}
