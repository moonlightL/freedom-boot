package com.extlight.core.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.model.SysRole;
import com.extlight.core.model.vo.SysRoleVO;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysRoleService
 * @ProjectName freedom-boot
 * @Description: 系统角色 Service
 * @Date 2019/7/1 15:47
 */
public interface SysRoleService extends BaseService<SysRole> {


    /**
     * 通过用户 id 查询角色列表
     * @param userId  用户id
     * @return
     * @throws GlobalException
     */
    List<SysRole> findRoleListByUserId(Long userId) throws GlobalException;

    /**
     * 分配权限
     * @param roleId  角色 id
     * @param permissionIdStr 权限id（多个id通过 “，” 拼接）
     * @return
     * @throws GlobalException
     */
    int asignPermission(Long roleId, String permissionIdStr) throws GlobalException;
}
