package com.extlight.common.component.annotation;

import com.extlight.common.config.datasource.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MoonlightL
 * @Title: DataSource
 * @ProjectName freedom-boot
 * @Description:
 * @date 2019/5/30 20:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    DataSourceType value() default DataSourceType.MASTER;
}
