package com.extlight.core.web.config.shiro;

import lombok.Getter;
import lombok.ToString;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: ShiroSession
 * @ProjectName freedom-boot
 * @Description: 自定义 Shiro Session
 * @Date 2019/7/17 14:58
 */
@Getter
@ToString
public class ShiroSession extends SimpleSession {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     *  访问 ip
     */
    private String ip;

    /**
     * 访问浏览器
     */
    private String browser;

    /**
     * 访问系统
     */
    private String os;

    /**
     * online 状态 参考：SessionEnum 枚举
     */
    private Integer state;

    /**
     * 是否变化（除 lastAccessTime 以外其他字段发生改变时为true）
     */
    private boolean isChanged;

    public ShiroSession() {
        super();
        this.setChanged(true);
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.setChanged(true);
    }

    public void setUsername(String username) {
        this.username = username;
        this.setChanged(true);
    }

    public void setIp(String ip) {
        this.ip = ip;
        this.setChanged(true);
    }

    public void setBrowser(String browser) {
        this.browser = browser;
        this.setChanged(true);
    }

    public void setOs(String os) {
        this.os = os;
        this.setChanged(true);
    }

    public void setState(Integer state) {
        this.state = state;
        this.setChanged(true);
    }

    @Override
    public void setId(Serializable id) {
        super.setId(id);
        this.setChanged(true);
    }

    @Override
    public void setStopTimestamp(Date stopTimestamp) {
        super.setStopTimestamp(stopTimestamp);
        this.setChanged(true);
    }

    @Override
    public void setExpired(boolean expired) {
        super.setExpired(expired);
        this.setChanged(true);
    }

    @Override
    public void setTimeout(long timeout) {
        super.setTimeout(timeout);
        this.setChanged(true);
    }

    @Override
    public void setHost(String host) {
        super.setHost(host);
        this.setChanged(true);
    }

    @Override
    public void setAttributes(Map<Object, Object> attributes) {
        super.setAttributes(attributes);
        this.setChanged(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.setChanged(true);
    }

    @Override
    protected void expire() {
        super.expire();
        this.setChanged(true);
    }

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
        this.setChanged(true);
    }

    @Override
    public Object removeAttribute(Object key) {
        this.setChanged(true);
        return super.removeAttribute(key);
    }
}
