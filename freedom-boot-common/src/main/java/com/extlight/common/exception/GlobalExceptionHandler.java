package com.extlight.common.exception;

import com.extlight.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: GlobalExceptionHandler
 * @ProjectName freedom-boot
 * @Description: 全局异常处理
 * @Date 2019/5/30 19:39
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String SUFFIX_JSON = ".json";

    private static final String SUFFIX_HTML = ".html";

    /**
     * 自定义异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result handleException(GlobalException e) {
        return Result.fail(e.getCode(),e.getMessage());
    }

    /**
     * 数据校验异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result handleBindException(BindException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        return Result.fail(GlobalExceptionEnum.ERROR_PARAM.getCode(),allErrors.get(0).getDefaultMessage());
    }

    /**
     * Shiro 异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler
    public ModelAndView handleShiroException(HttpServletRequest request, ShiroException e) {

        String url = request.getRequestURL().toString();
        log.error("=====异常 url:{}=====", url);

        ModelAndView mv = new ModelAndView("error");

        if (url.endsWith(SUFFIX_JSON)) {
            if (e instanceof UnauthorizedException) {
                Map<String, Object> resultMap = Result.fail(GlobalExceptionEnum.ERROR_UNAUTHORIZED).toMap();
                mv = new ModelAndView("jsonView", resultMap);
            } else if (e instanceof LockedAccountException) {
                Map<String, Object> resultMap = Result.fail(GlobalExceptionEnum.ERROR_STATE_WRONG).toMap();
                mv = new ModelAndView("jsonView", resultMap);
            }
        } else if (url.endsWith(SUFFIX_HTML)) {
            if (e instanceof UnauthorizedException) {
                Map<String, Object> resultMap = Result.fail(GlobalExceptionEnum.ERROR_UNAUTHORIZED).toMap();
                mv = new ModelAndView("error", resultMap);
            } else if (e instanceof LockedAccountException) {
                Map<String, Object> resultMap = Result.fail(GlobalExceptionEnum.ERROR_STATE_WRONG).toMap();
                mv = new ModelAndView("error", resultMap);
            }
        }

        return mv;
    }
}
