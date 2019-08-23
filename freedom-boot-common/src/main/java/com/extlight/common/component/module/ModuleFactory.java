package com.extlight.common.component.module;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @Title: ModuleFactory
 * @ProjectName: freedom-boot
 * @Description:  Module 工厂
 * @DateTime: 2019/8/23 10:35
 */
@Component
public class ModuleFactory implements ApplicationContextAware {

	private static Map<String, Module> moduleServiceMap;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, Module> serviceMap = applicationContext.getBeansOfType(Module.class);
		moduleServiceMap = new HashMap<>(serviceMap.size());
		serviceMap.forEach((k, v) -> moduleServiceMap.put(v.getCode(), v));
	}

	/**
	 * 获取实例
	 * @param code
	 * @return
	 */
	public static Module getInstance(String code) {
		return moduleServiceMap.get(code);
	}

	/**
	 * 获取模块列表
	 * @return
	 */
	public static Collection<Module> getModuleList() {
		return moduleServiceMap.values();
	}
}
