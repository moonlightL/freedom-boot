package com.extlight.core.model;

import com.extlight.common.base.BaseResponse;
import com.extlight.common.component.mybatis.CreateTime;
import com.extlight.common.component.mybatis.UpdateTime;
import com.extlight.core.model.vo.SysPermissionVO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @Author MoonlightL
 * @ClassName: SysPermission
 * @ProjectName: freedom-boot
 * @Description: 系统权限
 * @Date 2019/7/1 17:11
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_sys_permission")
public class SysPermission extends BaseResponse<SysPermissionVO> {

    private static final long serialVersionUID = 1L;

    /**
     *  id
     */
    @Id
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
     * 资源类型 参考：PermissionEnum
     */
    private Integer resourceType;

    /**
     * 业务类型 1：核心 2：扩展 3：业务
     */
    private Integer businessType;

    /**
     * 权限编码
     */
    private String perCode;

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
    @CreateTime
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @UpdateTime
    private LocalDateTime updateTime;

}
