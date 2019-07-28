package com.extlight.core.web.config.shiro;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * @Author MoonlightL
 * @Title: ShiroSession
 * @ProjectName freedom-boot
 * @Description: 自定义 Shiro Session
 * @Date 2019/7/17 14:58
 */
@Setter
@Getter
@ToString
public class ShiroSession extends SimpleSession {

    /**
     * sessionId
     */
    private String sessionId;

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

}
