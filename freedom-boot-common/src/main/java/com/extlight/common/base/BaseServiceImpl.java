package com.extlight.common.base;

import com.extlight.common.exception.GlobalException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: BaseServiceImpl
 * @ProjectName freedom-boot
 * @Description: Service 基类实现
 * @Date 2019/5/31 11:12
 */
public abstract class BaseServiceImpl<T extends BaseResponse> implements BaseService<T> {

    private static final String DEFAULT_SORT = "ASC";

    /**
     *  由子类实现
     * @return
     */
    public abstract BaseMapper<T> getBaseMapper();

    private Class<T> doClass;

    public BaseServiceImpl() {
        doClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
    public T getById(Long id) throws GlobalException {
        return this.getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public List<T> listAll() throws GlobalException {
        return this.getBaseMapper().selectAll();
    }

    @Override
    public List<T> list(BaseRequest params) throws GlobalException {
        return this.selectByExample(this.getExample(params));
    }

    @Override
    public PageInfo<T> pageAll(BaseRequest params) throws GlobalException {
        List<T> data = this.list(params);
        Page<T> page = new Page<>(1, data.size(), false);
        page.addAll(data);
        page.setTotal(this.count(null));
        return new PageInfo<>(page);
    }

    @Override
    public PageInfo<T> page(BaseRequest params) throws GlobalException {

        Example example = this.getExample(params);

        if (StringUtils.isEmpty(params.getSortName())) {
            params.setSortName("id");
        }

        if (DEFAULT_SORT.equals(params.getSortOrder())) {
            example.orderBy(params.getSortName()).asc();
        } else {
            example.orderBy(params.getSortName()).desc();
        }

        List<T> list = this.listByExample(example, params.getPageNum(), params.getPageSize(), true);
        return new PageInfo<>(list);
    }

    @Override
    public List<T> listByExample(Example example, int pageNum, int pageSize, boolean count) throws GlobalException {

        if (pageNum > 0 && pageSize > 0) {
            PageHelper.startPage(pageNum,pageSize, count);
        }

        return this.selectByExample(example);
    }

    @Override
    public int count(BaseRequest params) throws GlobalException {
        return this.getBaseMapper().selectCountByExample(this.getExample(params));
    }

    private List<T> selectByExample(Example example) {
        return this.getBaseMapper().selectByExample(example);
    }
}
