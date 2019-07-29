package com.extlight.extensions.generator.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author MoonlightL
 * @ClassName: GenTableDTO
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/7/8 12:25
 */
@Setter
@Getter
@ToString
public class GenTableDTO extends BaseRequest {

    /**
     * 表名称
     */
    private String tableName;
}
