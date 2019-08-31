package com.extlight.core.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.TreeNode;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysPermissionService
 * @ProjectName freedom-boot
 * @Description: 系统权限 Service
 * @Date 2019/7/1 17:22
 */
public interface SysPermissionService extends BaseService<SysPermission> {

    /**
     * 查询层次关系的权限列表
     * @return
     * @throws GlobalException
     */
    List<SysPermissionVO> findHierarchyPermissionList() throws GlobalException;

    /**
     * 通过用户 id 获取权列表
     * @param userId
     * @return
     * @throws GlobalException
     */
    List<SysPermission> findPermissionListByUserId(Long userId) throws GlobalException;

    /**
     * 通过角色 id 获取权限节点
     * @param roleId
     * @return
     * @throws GlobalException
     */
    List<TreeNode> findPermissionNodesByRoleId(Long roleId) throws GlobalException;

    /**
     * 获取通用按钮集合（增删改查）
     * @param url
     * @return
     * @throws GlobalException
     */
    List<SysPermission> findCommonButtonList(String url) throws GlobalException;

    /**
     * 获取模块列表
     * @return
     * @throws GlobalException
     */
	List<SysPermissionVO> findModuleList() throws GlobalException;
}
