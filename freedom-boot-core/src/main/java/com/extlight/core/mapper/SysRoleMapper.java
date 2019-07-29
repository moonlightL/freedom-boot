package com.extlight.core.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.core.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: SysRoleMapper
 * @ProjectName freedom-boot
 * @Description: 系统角色 Mapper
 * @Date 2019/7/1 15:46
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过用户 id 获取角色列表
     * @param userId 用户 id
     * @return
     */
    List<SysRole> selectByUserId(Long userId);

    /**
     * 删除 role_permission
     * @param roleId
     */
    void deleteRolePermissionByRoleId(Long roleId);

    /**
     * 批量插入 role_permission
     * @param paramList
     * @return
     */
    int insertRolePermissionBatch(@Param("paramList") List<Map<String, Long>> paramList);
}
