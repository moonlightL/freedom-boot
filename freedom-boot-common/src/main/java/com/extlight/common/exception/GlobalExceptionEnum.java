package com.extlight.common.exception;

import lombok.Getter;

/**
 * @Author MoonlightL
 * @ClassName: GlobalExceptionEnum
 * @ProjectName freedom-boot
 * @Description: 全局异常枚举
 * @Date 2019/5/31 13:46
 */
@Getter
public enum GlobalExceptionEnum implements GlobalExceptionMap {

    SUCCESS(200, "请求成功"),

    ERROR_PARAM(400, "请求参数有误"),
    ERROR_UNAUTHORIZED(401, "用户未授权"),
    ERROR_FORBIDDEN(403, "资源被禁止访问"),
    ERROR_STATE_WRONG(406, "该用户被禁用"),
    ERROR_SERVER(500, "系统异常"),
    ERROR_VERIFY_CODE_WRONG(600, "验证码不正确"),
    ERROR_CAN_NOT_DELETE_RESOURCE(999, "该资源无法删除");

    private int code;

    private String message;

    GlobalExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
