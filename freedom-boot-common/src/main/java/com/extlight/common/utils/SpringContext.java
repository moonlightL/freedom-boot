package com.extlight.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @ClassName: SpringContext
 * @ProjectName: freedom-boot
 * @Description: Spring 上下文
 * @DateTime: 2019/8/14 19:14
 */
@Component
public class SpringContext implements ApplicationContextAware {

	public static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.applicationContext = applicationContext;
	}

	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<?> clazz) {
		return (T) applicationContext.getBean(clazz);
	}
}
