package com.extlight.extensions.task.constant;

import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author MoonlightL
 * @ClassName: TaskJobExceptionEnum
 * @ProjectName: freedom-boot
 * @Description: 异常枚举
 * @DateTime: 2019-08-14 17:44:56
 */
public enum TaskJobExceptionEnum implements GlobalExceptionMap {

    ERROR_RESOURCE_NOT_EXIST(5001, "任务作业不存在"),
    ERROR_REPEAT_JOB_NAME(5002, "任务名重复"),
    ERROR_SCHEDULE_JOB_FAIL(5003, "调度任务失败"),
    ERROR_TRIGGER_JOB_FAIL(5004, "触发任务失败"),
    ERROR_PAUSE_JOB_FAIL(5005, "暂停任务失败"),
    ERROR_RESUME_JOB_FAIL(5006, "恢复任务失败"),
    ERROR_DELETE_JOB_FAIL(5007, "调度任务失败"),
	;

    private int code;

    private String message;

	TaskJobExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
