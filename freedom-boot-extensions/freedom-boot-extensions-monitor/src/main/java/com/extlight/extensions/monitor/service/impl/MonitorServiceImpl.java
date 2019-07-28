package com.extlight.extensions.monitor.service.impl;

import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.monitor.service.MonitorService;
import com.extlight.extensions.monitor.model.Server;
import org.springframework.stereotype.Service;

/**
 * @Author: MoonlightL
 * @ClassName: MonitorServiceImpl
 * @ProjectName: freedom-springboot
 * @Description: 监控器 ServiceImpl
 * @DateTime: 2019-07-21 19:44
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Override
    public Server getServiceInfo() throws GlobalException {
        Server server = new Server();
        server.collectInfo();
        return server;
    }

}
