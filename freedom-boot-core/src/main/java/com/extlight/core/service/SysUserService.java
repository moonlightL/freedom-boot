package com.extlight.core.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.model.SysUser;
import com.extlight.core.model.vo.SysUserVO;

/**
 * @author MoonlightL
 * @Title: SysUserService
 * @ProjectName freedom-boot
 * @Description: 系统用户 Service
 * @date 2019/5/31 11:15
 */
public interface SysUserService extends BaseService<SysUser, SysUserVO> {

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     * @throws GlobalException
     */
    SysUser findUserByUsername(String username) throws GlobalException;

    /**
     * 分配角色
     * @param userId 用户 id
     * @param roleIdStr 角色 id (多个 id 通过"," 拼接)
     * @return
     * @throws GlobalException
     */
    int asignRole(Long userId, String roleIdStr) throws GlobalException;
}
