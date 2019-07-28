package com.extlight.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @Author MoonlightL
 * @Title: BaseResponse
 * @ProjectName freedom-boot
 * @Description: 公共响应封装
 * @Date 2019/7/1 10:05
 */
@Setter
@Getter
@Accessors(chain = true)
public class BaseResponse implements Serializable {

    public <V> V toVO(Class<V> clazz) {
        try {
            V vo = clazz.newInstance();
            BeanUtils.copyProperties(this, vo);
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
