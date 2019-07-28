package com.extlight.core.constant;

import lombok.Getter;


/**
 * @Author MoonlightL
 * @Title: StateEnum
 * @ProjectName freedom-boot
 * @Description: 状态枚举
 * @Date 2019/6/27 14:52
 */
@Getter
public enum StateEnum {

    FORBIDDEN(0, "禁用"),
    NORMAL(1, "正常"),
    LOCK(2, "锁定");

    private int code;

    private String message;

    StateEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
