package com.extlight.common.exception;

/**
 * @Author MoonlightL
 * @ClassName: GlobalExceptionMap
 * @ProjectName freedom-boot
 * @Description: 异常枚举接口
 * @DateTime 2019-07-29 11:07
 */
public interface GlobalExceptionMap {

    /**
     * 返回 code
     * @return
     */
    int getCode();

    /**
     * 返回消息
     * @return
     */
    String getMessage();
}
