package com.extlight.extensions.terminal.component;

import com.extlight.common.component.module.Module;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: TerminalModule
 * @ProjectName: freedom-boot
 * @Description: 终端模块
 * @DateTime: 2019/8/23 10:46
 */
@Component
public class TerminalModule implements Module {


	@Override
	public String getCode() {
		return "TERMINAL";
	}

	@Override
	public String getName() {
		return "终端模块";
	}
}
