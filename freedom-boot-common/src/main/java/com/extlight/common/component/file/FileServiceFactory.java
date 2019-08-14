package com.extlight.common.component.file;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: FileServiceFactory
 * @ProjectName: freedom-boot
 * @Description: FileService 工厂
 * @DateTime: 2019/7/31 16:41
 */
@Component
public class FileServiceFactory implements ApplicationContextAware {

    private static Map<Integer, FileService> fileServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, FileService> serviceMap = applicationContext.getBeansOfType(FileService.class);
        fileServiceMap = new HashMap<>(serviceMap.size());
        serviceMap.forEach((k, v) -> fileServiceMap.put(v.getCode(), v));
    }

    /**
     * 获取实例
     * @param code
     * @return
     */
    public FileService getInstance(Integer code) {
        return fileServiceMap.get(code);
    }
}
