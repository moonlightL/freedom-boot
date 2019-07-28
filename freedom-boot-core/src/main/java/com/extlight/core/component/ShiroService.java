package com.extlight.core.component;

import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.SysRoleVO;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysPermissionService;
import com.extlight.core.service.SysRoleService;
import com.extlight.core.web.config.shiro.ShiroSession;
import com.extlight.core.web.config.shiro.SystemRealm;
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
 * @Title: ShiroService
 * @ProjectName freedom-boot
 * @Description: ShiroService
 * @Date 2019/7/15 19:34
 */
@Component
public class ShiroService {

    private static final String SUPER_ADMIN = "admin";

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

    public Subject getSubject() {
        return SecurityUtils.getSubject();
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
        List<SysRoleVO> roleList;
        if (SUPER_ADMIN.equals(sysUserVO.getUsername()) && sysUserVO.getSuperAdmin()) {
            roleList = this.sysRoleService.list();
        } else {
            roleList = this.sysRoleService.findRoleListByUserId(sysUserVO.getId());
        }

        sysUserVO.setRoleList(roleList);

    }

    private void setPermissions(SysUserVO sysUserVO) {
        List<SysPermissionVO> permissionList;
        if (SUPER_ADMIN.equals(sysUserVO.getUsername()) && sysUserVO.getSuperAdmin()) {
            permissionList = this.sysPermissionService.list();
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
        SystemRealm realm = (SystemRealm) rsm.getRealms().iterator().next();
        realm.clearAuth();
    }

}
