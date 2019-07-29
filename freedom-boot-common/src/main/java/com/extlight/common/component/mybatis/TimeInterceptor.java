package com.extlight.common.component.mybatis;

import com.extlight.common.component.annotation.CreateTime;
import com.extlight.common.component.annotation.UpdateTime;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @Author MoonlightL
 * @ClassName: TimeInterceptor
 * @ProjectName freedom-boot
 * @Description: mybatis 时间拦截器
 * @Date 2019/6/27 11:59
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class TimeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取 SQL 命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取参数
        Object parameter = invocation.getArgs()[1];

        // 获取私有成员变量
        Field[] declaredFields = parameter.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getAnnotation(CreateTime.class) != null) {
                // insert 语句插入 createTime
                if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }

            if (field.getAnnotation(UpdateTime.class) != null) {
                // insert 或 update 语句插入 updateTime
                if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
