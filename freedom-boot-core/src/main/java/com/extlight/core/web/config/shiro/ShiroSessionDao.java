package com.extlight.core.web.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * @Author MoonlightL
 * @ClassName: ShiroSessionDao
 * @ProjectName freedom-boot
 * @Description: 自定义 SessionDao
 * @Date 2019/7/17 14:41
 */
@Slf4j
public class ShiroSessionDao extends EnterpriseCacheSessionDAO {


    public ShiroSessionDao() {
        super();
    }

    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {

        // 会话过期或停止不用刷新
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }

        if (session instanceof ShiroSession) {
            ShiroSession shiroSession = (ShiroSession) session;
            if (!shiroSession.isChanged()) {
                return;
            }

            shiroSession.setChanged(false);
        }

        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }
}
