package com.extlight.core.constant;

import lombok.Getter;

/**
 * @Author: MoonlightL
 * @ClassName: PermissionEnum
 * @ProjectName: freedom-boot
 * @Description: 权限类型
 * @DateTime: 2019-07-06 12:34
 */
@Getter
public enum PermissionEnum {

    MODULE(1, "模块"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮");


    private int code;

    private String message;

    PermissionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
