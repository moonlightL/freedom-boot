package com.extlight.extensions.generator.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author MoonlightL
 * @Title: GeneratorDTO
 * @ProjectName freedom-boot
 * @Description: 生成器请求对象
 * @Date 2019/7/8 20:22
 */
@Setter
@Getter
@ToString
public class GeneratorParam {

    /**
     * 表名前缀
     */
    private String tablePrefix;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 作者
     */
    private String author;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 项目名称
     */
    private String projectName;

}
