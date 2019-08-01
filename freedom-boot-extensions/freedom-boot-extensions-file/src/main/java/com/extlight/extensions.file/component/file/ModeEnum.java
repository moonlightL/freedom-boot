package com.extlight.extensions.file.component.file;

import lombok.Getter;

/**
 * @Author MoonlightL
 * @Title: ModeEnum
 * @ProjectName freedom-boot
 * @Description: 文件管理模式枚举
 * @Date 2019/7/31 16:35
 */
@Getter
public enum ModeEnum {

    DEFAULT(1, "默认"),
    QI_NIU(2, "七牛"),
    OSS(3, "oss");

    private int code;

    private String message;

    ModeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
