package com.extlight.common.component.module;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author MoonlightL
 * @Title: ModuleFactory
 * @ProjectName: freedom-boot
 * @Description:  Module 工厂
 * @DateTime: 2019/8/23 16:56
 */
@Component
public class ModuleFactory implements ApplicationContextAware {

	private static Map<String, Module> extensionMap;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Module> serviceMap = applicationContext.getBeansOfType(Module.class);
		extensionMap = new HashMap<>(serviceMap.size());
		serviceMap.forEach((k, v) -> extensionMap.put(v.getCode(), v));
	}

	/**
	 * 获取实例
	 * @param code
	 * @return
	 */
	public static Module getInstance(String code) {
		return extensionMap.get(code);
	}

	/**
	 * 获取插件列表
	 * @return
	 */
	public static List<Module> getModuleList() {
		return extensionMap.values().stream().collect(Collectors.toList());
	}
}
