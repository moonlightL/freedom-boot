package com.extlight.core.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.vo.SysPermissionVO;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysPermissionMapper
 * @ProjectName freedom-boot
 * @Description: 系统权限 Mapper
 * @Date 2019/7/1 17:21
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 通过用户 id 获取权限列表
     * @param userId
     * @return
     */
    List<SysPermission> selectByUserId(Long userId);

    /**
     * 通过角色 id 获取权限列表
     * @param roleId
     * @return
     */
    List<SysPermission> selectByRoleId(Long roleId);

    /**
     * 通过 url 获取通用按钮列表
     * @param url
     * @return
     */
    List<SysPermission> selectCommonButtonList(String url);

    /**
     * 获取层级关系的权限列表
     * @return
     */
    List<SysPermissionVO> selectHierarchyPermissionList();

    /**
     * 获取指定 pid 下的权限列表
     * @param id
     * @return
     */
    List<SysPermissionVO> selectByPid(Long id);
}
