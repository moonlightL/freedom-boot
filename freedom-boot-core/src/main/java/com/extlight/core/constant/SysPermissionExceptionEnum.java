package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @Title: SysPermissionExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 权限异常枚举
 * @DateTime: 2019/7/31 20:11
 */
public enum SysPermissionExceptionEnum implements GlobalExceptionMap {

    /*  菜单相关： 3000~3999   */
    ERROR_PERMISSION_NOT_EXIST(3001, "权限不存在"),

    ;

    private int code;

    private String message;

    SysPermissionExceptionEnum(int code, String message) {
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
