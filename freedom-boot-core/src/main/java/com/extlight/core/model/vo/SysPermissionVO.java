package com.extlight.core.model.vo;

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
public class SysPermissionVO implements Serializable {

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
     * 类型 1：模块 2：菜单 3：按钮
     */
    private Integer type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysPermissionVO that = (SysPermissionVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
