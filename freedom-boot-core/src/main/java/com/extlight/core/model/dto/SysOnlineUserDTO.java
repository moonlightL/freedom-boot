package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysOnlineUserDTO
 * @ProjectName freedom-boot
 * @Description: 在线用户请求对象
 * @Date 2019-07-17 16:17:08
 */
@Setter
@Getter
@ToString
public class SysOnlineUserDTO extends BaseRequest {


	/**
	 * 主键
	 */
	private String sessionId;

	/**
	 * 用户 id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 访问 ip
	 */
	private String ip;

	/**
	 * 访问浏览器
	 */
	private String browser;

	/**
	 * 访问系统
	 */
	private String os;

	/**
	 * 状态 1：在线 0：离线
	 */
	private Integer state;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

}
