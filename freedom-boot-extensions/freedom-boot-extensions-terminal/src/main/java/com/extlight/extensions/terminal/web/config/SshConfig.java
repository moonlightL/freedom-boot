package com.extlight.extensions.terminal.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: SshConfig
 * @ProjectName: freedom-boot
 * @Description: SSH 配置
 * @DateTime: 2019/8/21 17:44
 */
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ssh")
public class SshConfig {

	private String hostname;

	private String username;

	private String password;
}
