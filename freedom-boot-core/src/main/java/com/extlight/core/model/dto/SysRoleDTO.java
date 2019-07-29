package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @Author MoonlightL
 * @ClassName: SysRoleDTO
 * @ProjectName freedom-boot
 * @Description: 系统角色请求对象
 * @Date 2019/7/1 15:43
 */
@Setter
@Getter
@ToString
public class SysRoleDTO extends BaseRequest {

    /**
     *  id
     */
    @Id
    private Long id;

    /**
     * 角色名称
     */
    @NotEmpty(message="角色名称不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private String name;

    /**
     * 角色编码
     */
    @NotEmpty(message="角色编码不能为空", groups = {BaseRequest.Save.class, BaseRequest.Update.class})
    private String code;

    /**
     * 描述
     */
    private String descr;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
