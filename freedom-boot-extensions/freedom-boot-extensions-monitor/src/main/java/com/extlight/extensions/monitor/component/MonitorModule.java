package com.extlight.extensions.monitor.component;

import com.extlight.common.component.module.Module;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: MonitorModule
 * @ProjectName: freedom-boot
 * @Description: 监控模块
 * @DateTime: 2019/8/23 10:43
 */
@Component
public class MonitorModule implements Module {


	@Override
	public String getCode() {
		return "MONITOR";
	}

	@Override
	public String getName() {
		return "监控模块";
	}
}
