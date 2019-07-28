package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author MoonlightL
 * @Title: SysUserDTO
 * @ProjectName freedom-boot
 * @Description: 系统用户请求对象
 * @Date 2019/6/27 13:47
 */
@Setter
@Getter
@ToString
public class SysUserDTO extends BaseRequest {

    /**
     *  id
     */
    private Long id;

    /**
     * 用户名
     */
    @NotEmpty(message="用户名不能为空", groups = BaseRequest.Save.class)
    private String username;

    /**
     * 昵称
     */
    @NotEmpty(message="昵称不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private String nickname;

    /**
     * 性别
     */
    @NotNull(message="性别不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private Integer gender;

    /**
     * 邮箱地址
     */
    @NotEmpty(message="邮箱地址不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 地址
     */
    private String address;

    /**
     * qq 号码
     */
    private Integer qq;

    /**
     * 备注
     */
    private String remark;
}
