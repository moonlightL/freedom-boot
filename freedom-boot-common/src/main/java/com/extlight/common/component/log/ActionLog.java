package com.extlight.common.component.log;

import com.extlight.common.component.module.Module;
import com.extlight.common.constant.ActionEnum;

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
     * 模块类型
     * @return
     */
    Class<? extends Module> module();

    /**
     * 操作类型
     * @return
     */
    ActionEnum actionType();
}
