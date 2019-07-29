package com.extlight.common.constant;

import lombok.Getter;


/**
 * @Author MoonlightL
 * @ClassName: SysLogEnum
 * @ProjectName freedom-boot
 * @Description: 日志类型
 * @Date 2019/7/9 14:27
 */
@Getter
public enum ActionEnum {

    SAVE(1, "新增"),
    REMOVE(2, "删除"),
    UPDATE(3, "修改"),
    OTHER(4, "其他"),
    LOGIN(5, "登录"),
    ;

    private int code;

    private String message;

    ActionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(int code) {
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (actionEnum.getCode() == code) {
                return actionEnum.getMessage();
            }
        }
        return "";
    }
}
