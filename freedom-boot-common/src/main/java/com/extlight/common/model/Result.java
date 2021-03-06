package com.extlight.common.model;

import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.exception.GlobalExceptionMap;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: Result
 * @ProjectName freedom-boot
 * @Description: 响应对象
 * @Date 2019/5/30 19:39
 */
@Getter
public class Result implements Serializable {

    private int code;

    private boolean success;

    private String msg;

    private Object data;

    private Result(GlobalExceptionMap exceptionMap) {
        this.code = exceptionMap.getCode();
        this.msg = exceptionMap.getMessage();
        this.success = (code == 200);
    }

    private Result(GlobalExceptionMap exceptionMap, Object data) {
        this.code = exceptionMap.getCode();
        this.msg = exceptionMap.getMessage();
        this.data = data;
        this.success = (code == 200);
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = (code == 200);
    }

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(GlobalExceptionEnum.SUCCESS,data);
    }

    public static Result fail() {
        return fail(GlobalExceptionEnum.ERROR_SERVER);
    }

    public static Result fail(GlobalExceptionMap exceptionMap) {
        return new Result(exceptionMap);
    }

    public static Result fail(int code, String message) {
        return new Result(code,message);
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", code);
        map.put("success", success);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }
}
