package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @Author MoonlightL
 * @ClassName: LoginDTO
 * @ProjectName freedom-boot
 * @Description: 登录接口封装对象
 * @Date 2019/7/5 11:29
 */
@Setter
@Getter
@ToString
public class LoginDTO extends BaseRequest {

    @NotEmpty(message="用户名不能为空")
    private String username;

    @NotEmpty(message="密码不能为空")
    private String password;

    @NotEmpty(message="验证码不能为空")
    private String verifyCode;

    private Boolean rememberMe = false;
}
