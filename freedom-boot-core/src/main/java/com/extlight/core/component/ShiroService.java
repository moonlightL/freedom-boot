package com.extlight.core.component;

import com.extlight.core.constant.SystemContant;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.SysRole;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysPermissionService;
import com.extlight.core.service.SysRoleService;
import com.extlight.core.web.config.shiro.CoreRealm;
import com.extlight.core.web.config.shiro.ShiroSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: ShiroService
 * @ProjectName freedom-boot
 * @Description: ShiroService
 * @Date 2019/7/15 19:34
 */
@Component
public class ShiroService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SessionDAO sessionDAO;

    /**
     *  session 列表
     * @return
     */
    public List<ShiroSession> listSession() {
        Collection<Session> sessionList =  this.sessionDAO.getActiveSessions();
        List<ShiroSession> result = new ArrayList<>(sessionList.size());

        for (Session session : sessionList) {
            ShiroSession shiroSession = (ShiroSession) session;
            result.add(shiroSession);
        }

        return result;
    }

    /**
     * 清除 session
     * @param sessionId
     */
    public void clearSession(String sessionId) {
        Session session = this.sessionDAO.readSession(sessionId);
        if (session != null) {
            this.sessionDAO.delete(session);
        }
    }

    /**
     * 获取当前主体
     * @return
     */
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前用户 id
     * @return
     */
    public Long getUserId() {
        SysUserVO sysUserVO = (SysUserVO) SecurityUtils.getSubject().getPrincipal();
        return sysUserVO.getId();
    }


    /**
     * 封装权限
     * @param sysUserVO
     */
    public void bindAuthorization(SysUserVO sysUserVO) {
        this.setRoles(sysUserVO);
        this.setPermissions(sysUserVO);
    }


    private void setRoles(SysUserVO sysUserVO) {
        List<SysRole> roleList;
        if (SystemContant.SUPER_ADMIN.equals(sysUserVO.getUsername()) && sysUserVO.getSuperAdmin()) {
            roleList = this.sysRoleService.listAll();
        } else {
            roleList = this.sysRoleService.findRoleListByUserId(sysUserVO.getId());
        }

        sysUserVO.setRoleList(roleList);

    }

    private void setPermissions(SysUserVO sysUserVO) {
        List<SysPermission> permissionList;
        if (SystemContant.SUPER_ADMIN.equals(sysUserVO.getUsername()) && sysUserVO.getSuperAdmin()) {
            permissionList = this.sysPermissionService.listAll();
        } else {
            permissionList = this.sysPermissionService.findPermissionListByUserId(sysUserVO.getId());
        }

        sysUserVO.setPermissionList(permissionList);
    }

    /**
     *  清除权限缓存
     */
    public void clearAuthorization(){
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        CoreRealm realm = (CoreRealm) rsm.getRealms().iterator().next();
        realm.clearAuth();
    }

}
