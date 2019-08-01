package com.extlight.core.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: SysFileExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-07-31 17:42:54
 */
public enum SysFileExceptionEnum implements GlobalExceptionMap {

    ERROR_FILE_NOT_EXIST(5001, "资源不存在"),
    ERROR_UPLOAD_FILE_IS_EMPTY(5002, "上传文件为空"),
    ERROR_FILE_DELETED(5003, "该文件已被删除"),

	;

    private int code;

    private String message;

	SysFileExceptionEnum(int code, String message) {
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
