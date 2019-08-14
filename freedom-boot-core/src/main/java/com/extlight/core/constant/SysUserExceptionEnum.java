package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: SysUserExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 用户异常枚举
 * @DateTime: 2019/7/31 20:09
 */
public enum SysUserExceptionEnum implements GlobalExceptionMap {

    /*  用户相关： 1000~1999   */
    ERROR_USER_NOT_EXIST(1001, "用户不存在"),
    ERROR_USER_PASSWORD_WRONG(1002, "用户名或密码不正确"),
    ERROR_LOGIN_EXPIRE(1003, "未登录或登陆过期"),
    ERROR_OLD_PASSWORD_WRONG(1004, "原始密码不正确"),
    ERROR_UPLOAD_AVATAR_IS_EMPTY(1005, "上传头像为空"),
    ERROR_UPDATE_AVATAR_FAIL(1006, "修改个人头像失败"),

    ;

    private int code;

    private String message;

    SysUserExceptionEnum(int code, String message) {
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
