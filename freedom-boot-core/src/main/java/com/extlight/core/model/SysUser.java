package com.extlight.core.model;

import com.extlight.common.base.BaseResponse;
import com.extlight.common.component.annotation.CreateTime;
import com.extlight.common.component.annotation.UpdateTime;
import com.extlight.core.model.vo.SysUserVO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @Author MoonlightL
 * @ClassName: SysUser
 * @ProjectName freedom-boot
 * @Description: 系统用户
 * @Date 2019/5/31 10:33
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_sys_user")
public class SysUser extends BaseResponse<SysUserVO> {

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
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;


    /**
     * 加密盐
     */
    private String salt;

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
     * 删除标志 1：是 0：否
     */
    private Integer del;

    /**
     * 状态 0：禁用 1：开启 2：锁定
     */
    private Integer state;

    /**
     * 是否是超级管理员 1：是 0：否
     */
    private Boolean superAdmin;

    /**
     * 备注
     */
    private String remark;

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
