package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import com.extlight.core.model.SysPermission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author MoonlightL
 * @ClassName: SysPermissionDTO
 * @ProjectName freedom-boot
 * @Description: 系统权限请求对象
 * @Date 2019/7/1 17:22
 */
@Setter
@Getter
@ToString
public class SysPermissionDTO extends BaseRequest<SysPermission> {

    /**
     *  id
     */
    private Long id;

    /**
     * 权限名称
     */
    @NotEmpty(message="权限名称不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private String name;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 权限访问地址
     */
    private String url;

    /**
     * 资源类型 参考：PermissionEnum
     */
    @NotNull(message="资源类型不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private Integer resourceType;

    /**
     * 业务类型 1：核心 2：扩展 3：业务
     */
    @NotNull(message="业务类型不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private Integer businessType;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 父级 id
     */
    private Long pid;

    /**
     * 状态 1：可用 0：禁用
     */
    private Integer state;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否普通 1：是 0：否 （增删改查为普通，其余的为特殊）
     */
    private Integer common;
}
