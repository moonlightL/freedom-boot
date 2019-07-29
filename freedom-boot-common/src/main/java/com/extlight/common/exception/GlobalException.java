package com.extlight.common.exception;

/**
 * @Author MoonlightL
 * @ClassName: GlobalException
 * @ProjectName freedom-boot
 * @Description: 全局异常
 * @Date 2019/5/30 19:38
 */
public class GlobalException extends RuntimeException {

    private int code;

    private String message;

    public GlobalException(GlobalExceptionMap globalExceptionMap) {
        super(globalExceptionMap.getMessage());
        this.code = globalExceptionMap.getCode();
        this.message = globalExceptionMap.getMessage();
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
