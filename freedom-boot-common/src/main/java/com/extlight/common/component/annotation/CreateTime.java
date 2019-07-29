package com.extlight.common.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author MoonlightL
 * @ClassName: CreateTime
 * @ProjectName freedom-boot
 * @Description: 创建时间注解
 * @Date 2019/6/27 13:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CreateTime {

    String value() default "";
}
