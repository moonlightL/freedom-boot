package com.extlight.common.component.log;

import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;

import java.lang.annotation.*;

/**
 * @Author MoonlightL
 * @ClassName: ActionLog
 * @ProjectName freedom-boot
 * @Description: 操作日志注解
 * @Date 2019/6/28 18:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ActionLog {

    String value() default "";

    /**
     * 模块名称
     * @return
     */
    ModuleEnum moduleName();

    /**
     * 操作类型
     * @return
     */
    ActionEnum actionType();
}
