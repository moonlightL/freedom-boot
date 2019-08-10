package com.extlight.common.component.file;

import lombok.Getter;

/**
 * @Author MoonlightL
 * @Title: FileManageEnum
 * @ProjectName freedom-boot
 * @Description: 文件管理模式枚举
 * @Date 2019/7/31 16:35
 */
@Getter
public enum FileManageEnum {

    LOCAL(1, "本地"),
    QI_NIU(2, "七牛云"),
    OSS(3, "oss");

    private int code;

    private String message;

    FileManageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(int code) {
        for (FileManageEnum modeEnum : FileManageEnum.values()) {
            if (modeEnum.getCode() == code) {
                return modeEnum.getMessage();
            }
        }
        return "";
    }
}
