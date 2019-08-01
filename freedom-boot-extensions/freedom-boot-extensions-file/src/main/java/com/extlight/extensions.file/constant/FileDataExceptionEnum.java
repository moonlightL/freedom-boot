package com.extlight.extensions.file.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: FileDataExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-08-02 00:04:21
 */
public enum FileDataExceptionEnum implements GlobalExceptionMap {

    ERROR_RESOURCE_NOT_EXIST(5001, "资源不存在"),
    ERROR_FILE_DELETED(5002, "该文件已被删除"),

	;

    private int code;

    private String message;

	FileDataExceptionEnum(int code, String message) {
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
