package com.extlight.common.component.aspect;

import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.GlobalConstant;
import com.extlight.common.event.SysLogEvent;
import com.extlight.common.utils.IpUtil;
import com.extlight.common.utils.JsonUtil;
import com.extlight.common.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @Title: ActionLogAspect
 * @ProjectName freedom-boot
 * @Description: 操作日志切面
 * @Date 2019/6/28 18:25
 */
@Component
@Aspect
public class ActionLogAspect {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Pointcut("@annotation(com.extlight.common.component.annotation.ActionLog)")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        //执行方法
        Object result = point.proceed();

        //保存日志
        this.saveActionLog(point);

        return result;
    }

    private void saveActionLog(ProceedingJoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 执行方法
        Method method = signature.getMethod();
        // 方法参数名
        String[] parameterNames = signature.getParameterNames();
        // 方法参数值
        Object[] args = joinPoint.getArgs();

        ActionLog syslog = method.getAnnotation(ActionLog.class);
        if (syslog != null) {

            SysLogEvent event = new SysLogEvent(this);
            event.setModuleName(syslog.moduleName().getMessage())
                 .setActionType(syslog.actionType().getCode())
                 .setMethodName(method.getDeclaringClass().getName() + "." + method.getName())
                 .setMethodParam(this.getParameter(parameterNames, args))
                 .setUserId(this.getUserId(request))
                 .setIp(IpUtil.getIpAddr(request))
                 .setLocation(IpUtil.getLocation(request))
                 .setRemark(syslog.value())
                 .setCreateTime(LocalDateTime.now());

            publisher.publishEvent(event);
        }
    }

    private String getParameter(String[] parameterNames, Object[] args) {

        Object arg = args[0];
        Map<String, Object> map = new HashMap<>(10);

        if (arg instanceof BaseRequest) {
            Class clazz = arg.getClass();
            Field[] fs = clazz.getDeclaredFields();
            try {
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    f.setAccessible(true);
                    Object val;
                    val = f.get(arg);
                    map.put(f.getName(), val);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < parameterNames.length; i++) {
                map.put(parameterNames[i], args[i]);
            }
        }
        return JsonUtil.toStr(map, true);
    }

    private Long getUserId(HttpServletRequest request) {

        String token = request.getHeader(GlobalConstant.TOKEN);
        if (StringUtils.isEmpty(token)) {
            // 登录的情况
            return Long.valueOf(request.getAttribute("userId").toString());
        }

        Claims claims = TokenUtil.getClaims(token);
        if (claims == null) {
            return 0L;
        }

        return Long.valueOf(claims.getId());
    }
}
