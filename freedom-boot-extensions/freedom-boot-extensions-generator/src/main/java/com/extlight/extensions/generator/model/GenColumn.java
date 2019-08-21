package com.extlight.extensions.generator.model;

import com.extlight.common.base.BaseResponse;
import com.extlight.extensions.generator.model.vo.GenColumnVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @Author MoonlightL
 * @ClassName: GenColumn
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/7/8 12:29
 */
@Getter
@Setter
@ToString
@Table(name = "information_schema.columns")
public class GenColumn extends BaseResponse<GenColumnVO> {

    private static final long serialVersionUID = 1L;

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
}
