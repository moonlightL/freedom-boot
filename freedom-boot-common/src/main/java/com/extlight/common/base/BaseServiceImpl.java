package com.extlight.common.base;

import com.extlight.common.exception.GlobalException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MoonlightL
 * @Title: BaseServiceImpl
 * @ProjectName freedom-boot
 * @Description:
 * @date 2019/5/31 11:12
 */
public abstract class BaseServiceImpl<T extends BaseResponse, V> implements BaseService<T, V> {

    private static final String DEFAULT_SORT = "ASC";

    /**
     *  由子类实现
     * @return
     */
    public abstract BaseMapper<T> getBaseMapper();

    private Class<T> doClass;

    private Class<V> voClass;

    public BaseServiceImpl() {
        doClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        voClass = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * 不同模块，查询业务不同，由对应子类完成查询条件
     * @param params
     * @return
     */
    protected abstract Example getExample(BaseRequest params);

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int save(T model) throws GlobalException {
        return this.getBaseMapper().insertSelective(model);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int remove(Long id) throws GlobalException {
        return this.getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int removeBatch(List<Long> idList) throws GlobalException {
        Example example = new Example(doClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", idList);
        return this.getBaseMapper().deleteByExample(example);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int update(T model) throws GlobalException {
        return this.getBaseMapper().updateByPrimaryKeySelective(model);
    }

    @Override
    public V getById(Long id) throws GlobalException {
        T bo = this.getBaseMapper().selectByPrimaryKey(id);
        if (bo == null) {
            return null;
        }
        return  bo.toVO(voClass);
    }

    @Override
    public List<V> list() throws GlobalException {
        List<T> list = this.getBaseMapper().selectAll();
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<V> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toVO(voClass)));
        return result;
    }

    @Override
    public List<V> list(BaseRequest params) throws GlobalException {
        Example example = this.getExample(params);
        List<T> list = this.getBaseMapper().selectByExample(example);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        List<V> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toVO(voClass)));
        return result;
    }

    @Override
    public PageInfo<V> page(BaseRequest params) throws GlobalException {

        Example example = this.getExample(params);

        if (StringUtils.isEmpty(params.getSortName())) {
            params.setSortName("id");
        }

        if (DEFAULT_SORT.equals(params.getSortOrder())) {
            example.orderBy(params.getSortName()).asc();
        } else {
            example.orderBy(params.getSortName()).desc();
        }

        List<V> list = this.listByExample(example, params.getPageNum(), params.getPageSize(), false);
        // 必须通过 Page 封装集合，否则 PageInfo 属性设置会出错
        Page<V> result = new Page<>(params.getPageNum(), params.getPageSize(), false);
        result.addAll(list);
        result.setTotal(this.getBaseMapper().selectCountByExample(example));
        return new PageInfo<>(result);
    }

    @Override
    public List<V> listByExample(Example example, int pageNum, int pageSize, boolean count) throws GlobalException {

        if (pageNum != 0 && pageSize != 0) {
            PageHelper.startPage(pageNum,pageSize, count);
        }

        List<T> list = this.getBaseMapper().selectByExample(example);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        List<V> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toVO(voClass)));
        return result;
    }

    @Override
    public int count(BaseRequest params) throws GlobalException {
        Example example = this.getExample(params);
        return this.getBaseMapper().selectCountByExample(example);
    }
}
