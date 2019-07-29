package com.extlight.common.base;

import com.extlight.common.exception.GlobalException;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: BaseService
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/5/31 11:06
 */
public interface BaseService<T extends BaseResponse, V> {

    /** 添加对象
     * @param model
     * @return
     * @throws GlobalException
     */
    int save(T model) throws GlobalException;

    /**
     * 删除对象
     * @param id
     * @return
     * @throws GlobalException
     */
    int remove(Long id) throws GlobalException;

    /**
     * 批量删除
     * @param idList
     * @return
     * @throws GlobalException
     */
    int removeBatch(List<Long> idList) throws GlobalException;


    /**
     * 修改对象
     * @param model
     * @return
     * @throws GlobalException
     */
    int update(T model) throws GlobalException;

    /**
     * 根据 id 获取对象
     * @param id
     * @return
     * @throws GlobalException
     */
    V getById(Long id) throws GlobalException;

    /**
     * 查询所有列表
     * @return
     * @throws GlobalException
     */
    List<V> list() throws GlobalException;

    /**
     * 条件查询列表
     * @param params  搜索参数
     * @return
     * @throws GlobalException
     */
    List<V> list(BaseRequest params) throws GlobalException;

    /**
     * 获取分页数据
     * @param params  搜索参数
     * @return
     * @throws GlobalException
     */
    PageInfo<V> page(BaseRequest params) throws GlobalException;


    /**
     * 条件查询
     * @param example  条件
     * @param pageNum  当前页
     * @param pageSize 页面大小
     * @param count    是否查询记录数
     * @return
     * @throws GlobalException
     */
    List<V> listByExample(Example example, int pageNum, int pageSize, boolean count) throws GlobalException;

    /**
     * 查询数量
     * @param params
     * @return
     * @throws GlobalException
     */
    int count(BaseRequest params) throws GlobalException;
}
