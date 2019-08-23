package com.extlight.common.component.log;

import com.extlight.common.base.BaseRequest;
import com.extlight.common.utils.IpUtil;
import com.extlight.common.utils.JsonUtil;
import com.extlight.common.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: ActionLogAspect
 * @ProjectName freedom-boot
 * @Description: 操作日志切面
 * @Date 2019/6/28 18:25
 */
@Component
@Aspect
public class ActionLogAspect {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Pointcut("@annotation(com.extlight.common.component.log.ActionLog)")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object result = point.proceed();
        this.saveActionLog(point);

        return result;
    }

    /**
     * 保存日志
     * @param joinPoint
     * @throws Exception
     */
    private void saveActionLog(ProceedingJoinPoint joinPoint) throws Exception {
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
            event.setModuleName(syslog.module().newInstance().getName())
                 .setActionType(syslog.actionType().getCode())
                 .setMethodName(method.getDeclaringClass().getName() + "." + method.getName())
                 .setMethodParam(this.getParameter(parameterNames, args))
                 .setUserId(this.getUserId(request))
                 .setIp(IpUtil.getIpAddr(request))
                 .setLocation(IpUtil.getLocation(request))
                 .setRemark(syslog.value())
                 .setCreateTime(LocalDateTime.now());

            this.publisher.publishEvent(event);
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

        } else if (arg instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) arg;
            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.entrySet().forEach(i -> map.put(i.getKey(), StringUtil.join(i.getValue(), ",")));

        } else if (arg instanceof MultipartFile[]) {
            MultipartFile[] files = (MultipartFile[]) arg;
            int index = 1;
            for (MultipartFile file : files) {
                map.put("fileName_" + (index++), file.getOriginalFilename());
            }

        } else if (arg instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) arg;
            map.put("fileName", file.getOriginalFilename());

        } else {
            for (int i = 0; i < parameterNames.length; i++) {
                if (args[i] instanceof String) {
                    map.put(parameterNames[i], args[i]);
                }
            }
        }

        return JsonUtil.toStr(map, true);
    }

    private Long getUserId(HttpServletRequest request) {
        return Long.valueOf(Long.valueOf(request.getAttribute("userId").toString()));
    }
}
