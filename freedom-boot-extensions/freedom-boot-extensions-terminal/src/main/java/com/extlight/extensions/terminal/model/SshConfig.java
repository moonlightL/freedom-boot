package com.extlight.extensions.terminal.model;

import lombok.Getter;

/**
 * @Author MoonlightL
 * @Title: SshConfig
 * @ProjectName: freedom-boot
 * @Description: SSH 配置
 * @DateTime: 2019/8/21 17:44
 */
@Getter
public class SshConfig {

	private String hostname;

	private String username;

	private String password;

	public SshConfig(String hostname, String username, String password) {
		this.hostname = hostname;
		this.username = username;
		this.password = password;
	}
}
