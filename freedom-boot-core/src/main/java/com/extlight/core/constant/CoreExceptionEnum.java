package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @Title: CoreExceptionEnum
 * @ProjectName freedom-boot
 * @Description:
 * @DateTime 2019/7/29 15:51
 */
public enum CoreExceptionEnum implements GlobalExceptionMap {

    /*  用户相关： 1000~1999   */
    ERROR_USER_NOT_EXIST(1001, "用户不存在"),
    ERROR_USER_PASSWORD_WRONG(1002, "用户名或密码不正确"),
    ERROR_LOGIN_EXPIRE(1003, "未登录或登陆过期"),
    ERROR_OLD_PASSWORD_WRONG(1004, "原始密码不正确"),

    /*  角色相关： 2000~2999   */
    ERROR_ROLE_NOT_EXIST(2001, "角色不存在"),
    ERROR_ROLE_DATA_IS_EMPTY(2002, "角色ID为空"),

    /*  菜单相关： 3000~3999   */
    ERROR_PERMISSION_NOT_EXIST(3001, "权限不存在"),

    ;

    private int code;

    private String message;

    CoreExceptionEnum(int code, String message) {
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
