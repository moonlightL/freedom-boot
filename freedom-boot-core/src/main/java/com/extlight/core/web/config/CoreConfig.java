package com.extlight.core.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @ClassName: CoreConfig
 * @ProjectName freedom-boot
 * @Description: 系统配置封装类
 * @Date 2019/6/27 14:10
 */
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "core.default")
public class CoreConfig {

    /**
     * 默认密码
     */
    private String password;

    /**
     * 默认头像
     */
    private String avatar;
}
