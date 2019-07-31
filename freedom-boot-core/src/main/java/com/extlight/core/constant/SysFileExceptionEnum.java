package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: SysFileExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-07-31 17:42:54
 */
public enum SysFileExceptionEnum implements GlobalExceptionMap {

    ERROR_RESOURCE_NOT_EXIST(1001, "资源不存在"),

	;

    private int code;

    private String message;

	SysFileExceptionEnum(int code, String message) {
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
