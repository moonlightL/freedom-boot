package com.extlight.common.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author MoonlightL
 * @ClassName: UpdateTime
 * @ProjectName freedom-boot
 * @Description: 修改时间注解
 * @Date 2019/6/27 13:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UpdateTime {

    String value() default "";
}