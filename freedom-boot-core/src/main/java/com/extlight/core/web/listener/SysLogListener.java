package com.extlight.core.web.listener;

import com.extlight.common.component.log.SysLogEvent;
import com.extlight.core.model.SysLog;
import com.extlight.core.service.SysLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @ClassName: SysLogListener
 * @ProjectName freedom-boot
 * @Description: 系统日志监听器
 * @Date 2019/7/9 14:43
 */
@Component
public class SysLogListener implements ApplicationListener<SysLogEvent> {

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void onApplicationEvent(SysLogEvent event) {

        SysLog sysLog = new SysLog();
        BeanUtils.copyProperties(event, sysLog);

        this.sysLogService.save(sysLog);
    }
}
