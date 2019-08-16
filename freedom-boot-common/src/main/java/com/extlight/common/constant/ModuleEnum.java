package com.extlight.common.constant;

import lombok.Getter;

/**
 * @Author MoonlightL
 * @ClassName: ModuleEnum
 * @ProjectName freedom-boot
 * @Description: 模块枚举
 * @Date 2019/7/9 15:08
 */
@Getter
public enum ModuleEnum {

    SYSTEM(1, "系统模块"),
    GENERATOR(2, "生成器模块"),
    MONITOR(3, "监控模块"),
    FILE(4, "文件模块"),
    TASK(5, "任务模块")
    ;

    private int code;

    private String message;

    ModuleEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
