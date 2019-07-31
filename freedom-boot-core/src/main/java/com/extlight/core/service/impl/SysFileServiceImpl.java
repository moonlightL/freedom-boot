package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.core.mapper.SysFileMapper;
import com.extlight.core.model.SysFile;
import com.extlight.core.model.dto.SysFileDTO;
import com.extlight.core.model.vo.SysFileVO;
import com.extlight.core.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author MoonlightL
 * @ClassName: SysFileServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 文件 ServiceImpl
 * @DateTime: 2019-07-31 17:42:54
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile, SysFileVO> implements SysFileService {

    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public BaseMapper<SysFile> getBaseMapper() {
        return this.sysFileMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysFile.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            SysFileDTO sysFileDTO = (SysFileDTO) params;
            // TODO 填充查询条件
        }

        return example;
    }

}