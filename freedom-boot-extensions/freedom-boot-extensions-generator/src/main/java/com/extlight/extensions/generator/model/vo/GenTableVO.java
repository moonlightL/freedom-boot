package com.extlight.extensions.generator.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: Table
 * @ProjectName freedom-boot
 * @Description: 生成器 Table 响应对象
 * @Date 2019/7/8 12:25
 */
@Getter
@Setter
@ToString
public class GenTableVO implements Serializable {

    private static final long serialVersionUID =1L;

    /**
     * 库名
     */
    private String tableSchema;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 存储引擎
     */
    private String engine;

    /**
     * 备注
     */
    private String tableComment;

    /**
     * 类名
     */
    private String className;

    /**
     * 属性名
     */
    private String variableName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 表的主键
     */
    private GenColumnVO pk;

    /**
     * 列集合
     */
    private List<GenColumnVO> columnList;
}
