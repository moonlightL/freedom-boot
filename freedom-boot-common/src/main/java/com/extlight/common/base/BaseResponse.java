package com.extlight.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author MoonlightL
 * @ClassName: BaseResponse
 * @ProjectName freedom-boot
 * @Description: 公共响应封装
 * @Date 2019/7/1 10:05
 */
@Setter
@Getter
@Accessors(chain = true)
@Slf4j
public class BaseResponse<V> implements Serializable {

    @Transient
    private Class<V> voClass;

    public BaseResponse() {
        try {
            Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                voClass = (Class<V>) actualTypeArguments[0];
            }
        } catch (Exception e) {
            log.warn("==========BaseResponse 获取泛型参数异常：getClass: {}, error: {}==========", getClass(), e.getMessage());
        }
    }

    /**
     * 转换成 VO 模型
     * @return
     */
    public V toVoModel() {

        if (voClass == null) {
            return null;
        }

        try {
            V vo = voClass.newInstance();
            BeanUtils.copyProperties(this, vo);
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
