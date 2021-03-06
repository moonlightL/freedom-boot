package com.extlight.core.web.config;

import com.extlight.common.config.SpringMvcConfig;
import com.extlight.core.web.interceptor.PermissionInterceptor;
import com.extlight.core.web.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 * @Author MoonlightL
 * @ClassName: CoreSpringMvcConfig
 * @ProjectName freedom-boot
 * @Description: 系统模块专属 springmvc 配置
 * @Date 2019/7/11 11:12
 */
@Configuration
public class CoreSpringMvcConfig extends SpringMvcConfig {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private PermissionInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**","/business/**","/plugins/**")
                .excludePathPatterns("/","/core/login/**", "/error");


        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**","/business/**","/plugins/**")
                .excludePathPatterns("/","/core/login/**", "/error");
    }
}
