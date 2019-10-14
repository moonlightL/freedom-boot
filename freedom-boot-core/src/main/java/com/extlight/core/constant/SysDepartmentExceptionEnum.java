package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: SysDepartmentExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-10-14 11:55:12
 */
public enum SysDepartmentExceptionEnum implements GlobalExceptionMap {

    ERROR_RESOURCE_NOT_EXIST(1001, "资源不存在"),

	;

    private int code;

    private String message;

	SysDepartmentExceptionEnum(int code, String message) {
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
