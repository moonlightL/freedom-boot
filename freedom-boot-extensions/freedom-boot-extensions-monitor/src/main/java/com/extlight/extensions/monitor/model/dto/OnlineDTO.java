package com.extlight.extensions.monitor.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: MoonlightL
 * @ClassName: OnlineDTO
 * @ProjectName: freedom-springboot
 * @Description:
 * @DateTime: 2019-07-21 19:50
 */
@Setter
@Getter
@ToString
public class OnlineDTO extends BaseRequest {

    /**
     * 用户名
     */
    private String username;
}
