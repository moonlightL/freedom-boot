package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author: MoonlightL
 * @ClassName: SysLogExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: TODO
 * @DateTime: 2019-08-04 11:59
 */
public enum  SysLogExceptionEnum implements GlobalExceptionMap {

	ERROR_LOG_NOT_EXIST(5001, "日志记录不存在"),

	;

	private int code;

	private String message;

	SysLogExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
