package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.core.mapper.SysDepartmentMapper;
import com.extlight.core.model.SysDepartment;
import com.extlight.core.model.dto.SysDepartmentDTO;
import com.extlight.core.service.SysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author MoonlightL
 * @ClassName: SysDepartmentServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 部门表 ServiceImpl
 * @DateTime: 2019-10-14 13:33:04
 */
@Service
public class SysDepartmentServiceImpl extends BaseServiceImpl<SysDepartment> implements SysDepartmentService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Override
    public BaseMapper<SysDepartment> getBaseMapper() {
        return this.sysDepartmentMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysDepartment.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            SysDepartmentDTO sysDepartmentDTO = (SysDepartmentDTO) params;
            // TODO 填充查询条件
        }

        return example;
    }

}