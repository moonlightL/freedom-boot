package com.extlight.core.web.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: ShiroConfig
 * @ProjectName freedom-boot
 * @Description: Shiro 配置
 * @Date 2019/7/4 20:27
 */
@Configuration
@EnableCaching
public class ShiroConfig {

    /**
     * 安全管理器
     * @return
     */
    @Bean(name = "securityManager")
    @DependsOn("flywayConfig")
    public DefaultWebSecurityManager securityManager(ShiroProperties properties, net.sf.ehcache.CacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm
        securityManager.setRealm(this.systemRealm());
        // 设置 session 管理器
        securityManager.setSessionManager(this.sessionManager(properties));
        // 设置记住我管理器
        securityManager.setRememberMeManager(rememberMeManager(properties));
        // 设置缓存管理器
        securityManager.setCacheManager(this.ehCacheManager(cacheManager));

        return securityManager;
    }

// ################################ Realm 配置 ######################################

    /**
     * Realm
     * @return
     */
    @Bean(name = "coreRealm")
    public CoreRealm systemRealm() {
        CoreRealm realm = new CoreRealm();
        // 设置加密器
        realm.setCredentialsMatcher(this.hashedCredentialsMatcher());
        // 设置授权数据缓存
        realm.setAuthenticationCacheName("shiroRealmCache");

        return realm;
    }

    /**
     * 加密
     * @return
     */
    @Bean
    public CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密方式
        credentialsMatcher.setHashAlgorithmName("md5");
        // 加密次数
        credentialsMatcher.setHashIterations(1);

        return credentialsMatcher;
    }

// ################################ 缓存配置 ######################################

    /**
     * 缓存管理
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        // 缓存配置文件
//        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");

        return ehCacheManager;
    }

// ################################ session 管理配置 ######################################

    /**
     * online 管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager(ShiroProperties shiroProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置 session 过期时间
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeout());
        // 开启删除无效 session 功能
        sessionManager.setDeleteInvalidSessions(true);
        // 开启定时检测有效 session 功能
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置检测有效 session 间隔时间，设置了改属性就无需设置 SessionValidationScheduler
        sessionManager.setSessionValidationInterval(shiroProperties.getInterval());
        // 设置 sessionDao
        sessionManager.setSessionDAO(this.sessionDAO());
        // 设置 session 工厂
        sessionManager.setSessionFactory(this.sessionFactory());
        // 设置监听器
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(this.sessionListener());
        sessionManager.setSessionListeners(listeners);
        // 去除 url 上的 sessionid
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        return sessionManager;
    }

    /**
     * sessionId 生成器
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * sessionDao 配置
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        ShiroSessionDao sessionDAO = new ShiroSessionDao();
        // 设置 sessionId 生成器
        sessionDAO.setSessionIdGenerator(this.sessionIdGenerator());

        return sessionDAO;
    }

    /**
     * Session 工厂配置
     * @return
     */
    @Bean
    public SessionFactory sessionFactory() {
        return new ShiroSessionFactory();
    }

// ################################ 记住我配置 ######################################

    /**
     * cookie 配置
     * @return
     */
    @Bean
    public Cookie rememberMeCookie(ShiroProperties shiroProperties) {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setPath("/");
        // 只能通过http访问，javascript无法访问
        cookie.setHttpOnly(true);
        // Cookie 的过期时间，单位为秒，如果设置为-1表示浏览器关闭，则 Cookie 消失
        cookie.setMaxAge(shiroProperties.getCookieTimeout());

        return cookie;
    }

    /**
     * rememberMe 管理器
     */
    @Bean
    public RememberMeManager rememberMeManager(ShiroProperties shiroProperties) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        manager.setCipherKey(cipherKey);
        manager.setCookie(this.rememberMeCookie(shiroProperties));
        return manager;
    }

// ################################ 监听器配置 ######################################

    @Bean
    public SessionListener sessionListener(){
        return new ShiroSessionListener();
    }

// ################################ 注解支持配置 ######################################

    /**
     * 开启 AOP 注解功能
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);

        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(ShiroProperties shiroProperties, net.sf.ehcache.CacheManager cacheManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(this.securityManager(shiroProperties, cacheManager));

        return advisor;
    }

// ################################ 过滤器配置 ######################################

    /**
     * 过滤器
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroProperties shiroProperties, net.sf.ehcache.CacheManager cacheManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(this.securityManager(shiroProperties, cacheManager));

        // 配置过滤器
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/core/login/**","anon");
        filterMap.put("/core/captcha**","anon");
        filterMap.put("/error","anon");
        filterMap.put("/assets/**","anon");
        filterMap.put("/business/**","anon");
        filterMap.put("/plugins/**","anon");
        filterMap.put("/home/index","user");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        shiroFilterFactoryBean.setLoginUrl("/");

        return shiroFilterFactoryBean;
    }

// ################################ 前端页面标签配置 ######################################

    /**
     * Thymeleaf 对 Shiro 标签的封装支持
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
