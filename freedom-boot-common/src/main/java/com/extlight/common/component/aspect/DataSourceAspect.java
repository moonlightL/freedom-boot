package com.extlight.common.component.aspect;

import com.extlight.common.component.annotation.DataSource;
import com.extlight.common.config.datasource.DataSourceType;
import com.extlight.common.config.datasource.DynamicDataSourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author MoonlightL
 * @Title: DataSourceAspect
 * @ProjectName freedom-boot
 * @Description:
 * @date 2019/5/30 20:34
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    @Pointcut("execution(* com.extlight.*.service.*.*(..))")
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (dataSource != null)  {
            DynamicDataSourceContextHolder.setDateSoureType(dataSource.value().name());
        } else {
            DynamicDataSourceContextHolder.setDateSoureType(this.isSlave(method.getName()) ? DataSourceType.SLAVE.name() : DataSourceType.MASTER.name());
        }

        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDateSoureType();
        }
    }

    private static final String[] METHOD_NAMES = {"query", "find", "get", "list", "page"};

    private Boolean isSlave(String methodName) {
        return StringUtils.startsWithAny(methodName, METHOD_NAMES);
    }
}
