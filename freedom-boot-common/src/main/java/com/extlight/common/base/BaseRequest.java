package com.extlight.common.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;

/**
 * @Author MoonlightL
 * @Title: BaseRequest
 * @ProjectName freedom-boot
 * @Description: 公共请求参数封装
 * @Date 2019/6/27 13:45
 */
@Setter
@Getter
public class BaseRequest {

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

    /**
     * 转成 DO 对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T toDo(Class<T> clazz) {
        try {
            T bo = clazz.newInstance();
            BeanUtils.copyProperties(this, bo);
            return bo;
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
