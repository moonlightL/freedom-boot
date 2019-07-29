package com.extlight.extensions.monitor.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: MoonlightL
 * @ClassName: OnlineVO
 * @ProjectName: freedom-boot
 * @Description:
 * @DateTime: 2019-07-21 19:52
 */
@Getter
@Setter
@ToString
public class OnlineVO {

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
     * 访问 ip
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

    private Date startTimestamp;

    private Date lastAccessTime;
}
