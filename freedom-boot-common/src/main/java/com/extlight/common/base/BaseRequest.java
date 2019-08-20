package com.extlight.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author MoonlightL
 * @ClassName: BaseRequest
 * @ProjectName freedom-boot
 * @Description: 公共请求参数封装
 * @Date 2019/6/27 13:45
 */
@Setter
@Getter
@Slf4j
public class BaseRequest<T> {

    private static final Integer DEFAULT_PAGE_NUM = 1;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前页
     */
    private Integer pageNum = DEFAULT_PAGE_NUM;

    /**
     * 页面大小
     */
    @Max(value = 100, message = "页面大小不能超过100", groups = BaseRequest.Query.class)
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序 asc/desc
     */
    private String sortOrder;

    /**
     *  bootstrap table 原始参数
     */
    private String search;

    private Class<T> doClass;

    public BaseRequest() {
        try {
            Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                doClass = (Class<T>) actualTypeArguments[0];
            }
        } catch (Exception e) {
            log.warn("==========BaseRequest 获取泛型参数异常：getClass: {}, error: {}==========", getClass(), e.getMessage());
        }
    }

    /**
     * 转成 DO 模型
     * @return
     */
    public  T toDoModel() {
        if (doClass == null) {
            return null;
        }

        try {
            T doModel = doClass.newInstance();
            BeanUtils.copyProperties(this, doModel);
            return doModel;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public interface Save {}

    public interface Remove {}

    public interface Update {}

    public interface Query {}
}
