package com.extlight.extensions.task.component;

import com.extlight.common.component.module.Module;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: TaskModule
 * @ProjectName: freedom-boot
 * @Description: 任务模块
 * @DateTime: 2019/8/23 10:45
 */
@Component
public class TaskModule implements Module {


	@Override
	public String getCode() {
		return "TASK";
	}

	@Override
	public String getName() {
		return "任务模块";
	}
}
