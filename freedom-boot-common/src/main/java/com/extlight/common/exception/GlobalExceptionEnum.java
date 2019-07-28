package com.extlight.common.exception;

import lombok.Getter;

/**
 * @author MoonlightL
 * @Title: GlobalExceptionEnum
 * @ProjectName freedom-boot
 * @Description: 全局异常枚举
 * @date 2019/5/31 13:46
 */
@Getter
public enum GlobalExceptionEnum {

    SUCCESS(200, "请求成功"),

    ERROR_PARAM(400, "请求参数有误"),
    ERROR_UNAUTHORIZED(401, "用户未授权"),
    ERROR_FORBIDDEN(403, "资源被禁止访问"),
    ERROR_SERVER(500, "系统异常"),
    ERROR_VERIFY_CODE_WRONG(600, "验证码不正确"),
    ERROR_CAN_NOT_DELETE_RESOURCE(999, "该资源无法删除"),

    /*  用户相关： 1000~1999   */
    ERROR_USER_NOT_EXIST(1001, "用户不存在"),
    ERROR_USER_PASSWORD_WRONG(1002, "用户名或密码不正确"),
    ERROR_USER_STATE_WRONG(1003, "该用户被锁定或禁用"),
    ERROR_LOGIN_EXPIRE(1004, "未登录或登陆过期"),
    ERROR_OLD_PASSWORD_WRONG(1005, "原始密码不正确"),

    /*  角色相关： 2000~2999   */
    ERROR_ROLE_NOT_EXIST(2001, "角色不存在"),
    ERROR_ROLE_DATA_IS_EMPTY(2002, "角色ID为空"),

    /*  菜单相关： 3000~3999   */
    ERROR_PERMISSION_NOT_EXIST(3001, "权限不存在"),


    /* 在线用户相关 4000~4999 */
    ERROR_CAN_NOT_KICK_SELF(4000, "不能踢出自己")

    ;

    private int code;

    private String message;

    GlobalExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
