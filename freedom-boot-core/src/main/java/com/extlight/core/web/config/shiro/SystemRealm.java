package com.extlight.core.web.config.shiro;

import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.core.constant.PermissionEnum;
import com.extlight.core.constant.StateEnum;
import com.extlight.core.model.SysUser;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.SysRoleVO;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysUserService;
import com.extlight.core.component.ShiroService;
import com.github.pagehelper.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author MoonlightL
 * @Title: SystemRealm
 * @ProjectName freedom-boot
 * @Description: 自定义 Realm
 * @Date 2019/7/4 20:26
 */
public class SystemRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ShiroService shiroService;


    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名
        String username = (String) token.getPrincipal();
        SysUser sysUser = this.sysUserService.findUserByUsername(username);
        if (sysUser == null) {
            return null;
        }

        // 判断状态
        if (!sysUser.getState().equals(StateEnum.NORMAL.getCode())) {
            throw new LockedAccountException(GlobalExceptionEnum.ERROR_USER_STATE_WRONG.getMessage());
        }

        SysUserVO userVO = sysUser.toVO(SysUserVO.class);

        return new SimpleAuthenticationInfo(userVO, sysUser.getPassword(), this.getName());
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SysUserVO userVO = (SysUserVO) principals.getPrimaryPrincipal();
        // 绑定权限
        this.shiroService.bindAuthorization(userVO);

        List<SysRoleVO> roleList = userVO.getRoleList();
        List<SysPermissionVO> permissionList = userVO.getPermissionList();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 授权-角色
        info.addRoles(roleList.stream().map(i -> i.getCode()).collect(Collectors.toList()));

        // 授权-权限
        info.addStringPermissions(
                permissionList.stream().filter(i -> !i.getType().equals(PermissionEnum.MODULE.getCode()) && !StringUtil.isEmpty(i.getCode()))
                        .map(i -> i.getCode()).collect(Collectors.toList())
        );

        return info;
    }

    @Override
    public void setName(String name) {
        super.setName("systemRealm");
    }

    /**
     * 清理授权缓存
     */
    public void clearAuth() {
        super.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}