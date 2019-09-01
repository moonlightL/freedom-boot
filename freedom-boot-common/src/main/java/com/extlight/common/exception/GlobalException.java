package com.extlight.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author MoonlightL
 * @ClassName: GlobalException
 * @ProjectName freedom-boot
 * @Description: 全局异常
 * @Date 2019/5/30 19:38
 */
@Setter
@Getter
@ToString
public class GlobalException extends RuntimeException {

    private int code;

    private String message;

    private Boolean isJson;

    public GlobalException(GlobalExceptionMap globalExceptionMap) {
        super(globalExceptionMap.getMessage());
        this.code = globalExceptionMap.getCode();
        this.message = globalExceptionMap.getMessage();
        this.isJson = true;
    }

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.isJson = true;
    }

    public GlobalException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
        this.isJson = false;
    }

}
