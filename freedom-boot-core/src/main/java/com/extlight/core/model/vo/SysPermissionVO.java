package com.extlight.core.model.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysPermissionVO
 * @ProjectName freedom-boot
 * @Description: 系统权限响应对象
 * @Date 2019/7/1 17:23
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"id", "name"})
public class SysPermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  id
     */
    private Long id;

    /**
     * 权限名称
     */
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
     * 模块编码
     */
    private String moduleCode;

    /**
     * 权限类型 参考：PerTypeEnum
     */
    private Integer perType;

    /**
     * 权限编码
     */
    private String perCode;

    /**
     * 资源类型 参考：ResourceTypeEnum
     */
    private Integer resourceType;

    /**
     * 父级 id
     */
    private Long pid;

    /**
     * 状态
     */
    private Boolean state;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否普通（增删改查为普通，其余的为特殊）
     */
    private Boolean common;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     *  子权限
     */
    private List<SysPermissionVO> children = new ArrayList<>();

    /**
     * 是否加载
     */
    private Boolean load;

}
