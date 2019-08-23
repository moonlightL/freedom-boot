package com.extlight.extensions.file.component;

import com.extlight.common.component.module.Module;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @Title: FileModule
 * @ProjectName: freedom-boot
 * @Description: 文件模块
 * @DateTime: 2019/8/23 10:44
 */
@Component
public class FileModule implements Module {


	@Override
	public String getCode() {
		return "FILE";
	}

	@Override
	public String getName() {
		return "文件模块";
	}
}
