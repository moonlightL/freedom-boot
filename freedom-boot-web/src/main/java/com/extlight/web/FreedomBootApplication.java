package com.extlight.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author MoonlightL
 * @ClassName: FreedomBootApplication
 * @ProjectName freedom-boot
 * @Description: 启动类
 * @Date 2019/5/30 13:40
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.extlight.*"})
@MapperScan(basePackages = {"com.extlight.*.mapper", "com.extlight.extensions.*.mapper"})
@Slf4j
public class FreedomBootApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FreedomBootApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FreedomBootApplication.class, args);
        log.info("访问地址：http://127.0.0.1:{}", context.getEnvironment().getProperty("server.port"));
    }

}