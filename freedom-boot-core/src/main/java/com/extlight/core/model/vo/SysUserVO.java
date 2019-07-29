package com.extlight.core.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysUserVO
 * @ProjectName freedom-boot
 * @Description: 系统用户响应对象
 * @Date 2019/6/27 13:47
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class SysUserVO implements Serializable {

    /**
     *  id
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 邮箱地址
     */
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
     * qq 号
     */
    private Integer qq;

    /**
     * 状态 0：禁用 1：开启 2：锁定
     */
    private Integer state;


    /**
     * 是否是超级管理员 1：是 0：否
     */
    private Boolean superAdmin;

    /**
     * 最后登录 ip
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 角色集合
     */
    private List<SysRoleVO> roleList;

    /**
     * 权限集合
     */
    private List<SysPermissionVO> permissionList;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 当前定位
     */
    private String location;
}
