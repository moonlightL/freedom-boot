package com.extlight.extensions.monitor.service;

import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.monitor.model.Server;

/**
 * @Author: MoonlightL
 * @ClassName: MonitorService
 * @ProjectName: freedom-boot
 * @Description: 监控器 Service
 * @DateTime: 2019-07-21 19:43
 */
public interface MonitorService {

    /**
     * 获取服务器信息
     * @return
     * @throws GlobalException
     */
    Server getServiceInfo() throws GlobalException;

}
