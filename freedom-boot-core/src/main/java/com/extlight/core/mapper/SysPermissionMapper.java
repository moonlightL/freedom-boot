package com.extlight.core.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.core.model.SysPermission;

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
}
