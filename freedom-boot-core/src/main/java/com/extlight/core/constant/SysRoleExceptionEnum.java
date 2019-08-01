package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @Title: SysRoleExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 角色异常枚举
 * @DateTime: 2019/7/31 20:10
 */
public enum SysRoleExceptionEnum implements GlobalExceptionMap {

    /*  角色相关： 2000~2999   */
    ERROR_ROLE_NOT_EXIST(2001, "角色不存在"),
    ERROR_ROLE_DATA_IS_EMPTY(2002, "角色ID为空"),

    ;

    private int code;

    private String message;

    SysRoleExceptionEnum(int code, String message) {
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
