package com.extlight.extensions.generator.model;

import com.extlight.common.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @Author MoonlightL
 * @Title: Table
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/7/8 12:25
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "information_schema.tables")
public class GenTable extends BaseResponse {

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
}
