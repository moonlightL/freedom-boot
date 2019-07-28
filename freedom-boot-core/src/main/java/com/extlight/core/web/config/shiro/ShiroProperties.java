package com.extlight.core.web.config.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: ShiroProperties
 * @ProjectName freedom-boot
 * @Description: Shiro 自定义属性
 * @Date 2019/7/17 19:41
 */
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "core.shiro")
public class ShiroProperties {

    /**
     * session 有效期，单位毫秒
     */
    private Long sessionTimeout;

    /**
     * 检测有效 session 间隔时间，单位毫秒
     */
    private Long interval;

    /**
     * cookie 有效期，单位秒
     */
    private Integer cookieTimeout;
}
