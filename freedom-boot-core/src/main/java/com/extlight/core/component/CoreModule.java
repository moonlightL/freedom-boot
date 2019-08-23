package com.extlight.core.component;

import com.extlight.common.component.module.Module;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: CoreModule
 * @ProjectName: freedom-boot
 * @Description: 系统模块
 * @DateTime: 2019/8/23 10:39
 */
@Component
public class CoreModule implements Module {


	@Override
	public String getCode() {
		return "SYSTEM";
	}

	@Override
	public String getName() {
		return "系统模块";
	}
}
