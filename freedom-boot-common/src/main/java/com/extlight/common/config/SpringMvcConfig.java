package com.extlight.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author MoonlightL
 * @ClassName: SpringmvcConfig
 * @ProjectName freedom-boot
 * @Description: 公共的 Spring mvc 配置
 * @Date 2019/6/18 17:32
 */
public class SpringMvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINESE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
        ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
        List<View> list = new ArrayList();
        list.add(mappingJackson2JsonView());
        cnvr.setDefaultViews(list);
        return cnvr;
    }

    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView(){
        return new MappingJackson2JsonView();
    }

}
