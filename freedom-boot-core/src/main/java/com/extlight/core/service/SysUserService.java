package com.extlight.core.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.model.SysUser;

/**
 * @Author MoonlightL
 * @ClassName: SysUserService
 * @ProjectName freedom-boot
 * @Description: 系统用户 Service
 * @Date 2019/5/31 11:15
 */
public interface SysUserService extends BaseService<SysUser> {

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

	/**
	 * 修改个人头像
	 * @param originalFilename
	 * @param contentType
	 * @param data
	 * @return 文件访问地址
	 * @throws GlobalException
	 */
	String updateAvatar(String originalFilename, String contentType, byte[] data) throws GlobalException;
}
