package com.extlight.common.exception;

/**
 * @author MoonlightL
 * @Title: GlobalException
 * @ProjectName freedom-boot
 * @Description: 全局异常
 * @date 2019/5/30 19:38
 */
public class GlobalException extends RuntimeException {

    private int code;

    private String message;

    public GlobalException(GlobalExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}