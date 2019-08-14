package com.extlight.extensions.file.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: FileConfigExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-08-02 00:04:21
 */
public enum FileConfigExceptionEnum implements GlobalExceptionMap {

    ERROR_RESOURCE_NOT_EXIST(4101, "资源不存在"),
    ERROR_UPLOAD_DIR_IS_EMPTY(4102, "文件上传目录为空，请在【文件配置】中设置再进行操作"),
    ERROR_QN_CONFIG_IS_EMPTY(4103, "七牛云配置不完全，请在【文件配置】中设置再进行操作"),
    ERROR_OSS_CONFIG_IS_EMPTY(4104, "OSS 配置不完全，请在【文件配置】中设置再进行操作"),
	;

    private int code;

    private String message;

	FileConfigExceptionEnum(int code, String message) {
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
