package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: SysPermissionExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 权限异常枚举
 * @DateTime: 2019/7/31 20:11
 */
public enum SysPermissionExceptionEnum implements GlobalExceptionMap {

    /*  菜单相关： 3000~3999   */
    ERROR_PERMISSION_NOT_EXIST(3001, "权限不存在"),
    ERROR_PERMISSION_CANNOT_REMOVE(3002, "该权限不能删除"),
    ERROR_PERMISSION_NOT_MODULE_TYPE(3003, "非模块类型 ID")
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
