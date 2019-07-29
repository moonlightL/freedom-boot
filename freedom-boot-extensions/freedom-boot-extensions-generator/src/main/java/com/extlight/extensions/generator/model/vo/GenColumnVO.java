package com.extlight.extensions.generator.model.vo;

import com.extlight.common.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author MoonlightL
 * @ClassName: GenColumnVO
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/7/8 12:29
 */
@Getter
@Setter
@ToString
public class GenColumnVO extends BaseResponse {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 字段索引类型
     */
    private String columnKey;

    /**
     * 额外信息
     */
    private String extra;

    /**
     * 属性类型
     */
    private String attrType;

    /**
     * 属性名称
     */
    private String attrName;
}
