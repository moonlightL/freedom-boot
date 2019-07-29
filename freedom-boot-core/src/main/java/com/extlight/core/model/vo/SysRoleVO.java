package com.extlight.core.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author MoonlightL
 * @ClassName: SysRoleVO
 * @ProjectName freedom-boot
 * @Description: 系统角色响应对象
 * @Date 2019/7/1 15:44
 */
@Setter
@Getter
@ToString
public class SysRoleVO implements Serializable {

    /**
     *  id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 描述
     */
    private String descr;

    /**
     * 是否选中
     */
    private Boolean checked;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
