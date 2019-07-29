package com.extlight.core.constant;

import lombok.Getter;

/**
 * @Author MoonlightL
 * @ClassName: SessionEnum
 * @ProjectName freedom-boot
 * @Description: online 枚举
 * @Date 2019/7/17 15:19
 */
@Getter
public enum  SessionEnum {

    OFFLINE(0, "离线"),
    ONLINE(1, "在线");

    private int code;

    private String message;

    SessionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
