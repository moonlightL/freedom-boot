package com.extlight.core.web.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author MoonlightL
 * @ClassName: ShiroSessionListener
 * @ProjectName freedom-boot
 * @Description: Shiro Session 监听器
 * @Date 2019/7/17 13:55
 */
@Slf4j
public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        log.info("=======onStart {}======", session.getId());
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        log.info("=======onStop {}======", session.getId());
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        log.info("=======onExpiration {}======", session.getId());
        sessionCount.decrementAndGet();
    }

    /**
     * 获取在线人数
     * @return
     */
    public int getOnlineCount() {
        return sessionCount.get();
    }
}
