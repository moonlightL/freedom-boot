package com.extlight.extensions.file.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: FileConfigExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-08-02 00:04:21
 */
public enum FileConfigExceptionEnum implements GlobalExceptionMap {

    ERROR_RESOURCE_NOT_EXIST(1001, "资源不存在"),

	;

    private int code;

    private String message;

	FileConfigExceptionEnum(int code, String message) {
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
