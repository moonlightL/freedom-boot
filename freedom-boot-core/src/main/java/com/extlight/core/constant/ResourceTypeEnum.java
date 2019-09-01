package com.extlight.core.constant;

import lombok.Getter;

/**
 * @Author: MoonlightL
 * @ClassName: ResourceTypeEnum
 * @ProjectName: freedom-boot
 * @Description: 资源类型 枚举
 * @DateTime: 2019-09-01 11:58
 */
@Getter
public enum  ResourceTypeEnum {

	CORE(1, "核心"),
	EXTENSIONS(2, "扩展"),
	BUSINESS(3, "业务");

	private int code;

	private String message;

	ResourceTypeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
