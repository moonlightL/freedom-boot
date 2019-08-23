package com.extlight.extensions.generator.component;

import com.extlight.common.component.module.Module;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: GeneratorModule
 * @ProjectName: freedom-boot
 * @Description: 生成器模块
 * @DateTime: 2019/8/23 10:41
 */
@Component
public class GeneratorModule implements Module {


	@Override
	public String getCode() {
		return "GENERATOR";
	}

	@Override
	public String getName() {
		return "生成器模块";
	}
}
