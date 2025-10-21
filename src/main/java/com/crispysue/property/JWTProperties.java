package com.crispysue.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt 令牌相关配置
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JWTProperties {
    private String userSecretKey;
    private Long userTtl;
    private String userTokenName;
}
