package com.extlight.core.web.config.shiro;

import com.extlight.common.utils.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author MoonlightL
 * @ClassName: ShiroSessionFactory
 * @ProjectName freedom-boot
 * @Description: 自定义 SessionFactory
 * @Date 2019/7/17 14:59
 */
public class ShiroSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        ShiroSession shiroSession = new ShiroSession();

        if (initData != null && initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null) {
                UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
                shiroSession.setIp(IpUtil.getIpAddr(request));
                // 设置客户端浏览器
                String browser = userAgent.getBrowser().getName();
                shiroSession.setBrowser(browser);
                // 设置客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                shiroSession.setOs(os);
            }
        }
        return shiroSession;
    }
}
