package com.extlight.core.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.core.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author MoonlightL
 * @Title: SysUserMapper
 * @ProjectName freedom-boot
 * @Description: 系统用户 Mapper
 * @date 2019/5/31 10:38
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户对象
     * @param username 用户名
     * @return
     */
    SysUser selectByUsername(String username);


    /**
     * 批量删除用户
     * @param idList 用户 id 集合
     * @return
     */
    int deleteBatch(@Param("idList") List<Long> idList);

    /**
     * 删除 user_role
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(Long userId);


    /**
     * 批量插入 user_role
     * @param paramList
     * @return
     */
    int insertUserRoleBatch(@Param("paramList") List<Map<String, Long>> paramList);
}
