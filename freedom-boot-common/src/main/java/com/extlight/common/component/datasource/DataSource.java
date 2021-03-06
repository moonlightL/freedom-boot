package com.extlight.common.component.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author MoonlightL
 * @ClassName: DataSource
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/5/30 20:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    DataSourceType value() default DataSourceType.MASTER;
}
