package com.extlight.common.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * @Author MoonlightL
 * @ClassName: LogEvent
 * @ProjectName freedom-boot
 * @Description: 日志事件
 * @Date 2019/7/9 14:39
 */
@Setter
@Getter
@Accessors(chain = true)
public class SysLogEvent extends ApplicationEvent {

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 操作类型 1：增加 2：删除 3：修改 4：其他
     */
    private Integer actionType;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String methodParam;

    /**
     * 操作用户 id
     */
    private Long userId;

    /**
     * 操作 ip
     */
    private String ip;

    /**
     * 所在地
     */
    private String location;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public SysLogEvent(Object source) {
        super(source);
    }
}
