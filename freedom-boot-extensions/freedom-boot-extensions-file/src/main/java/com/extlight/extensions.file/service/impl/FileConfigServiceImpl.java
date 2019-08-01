package com.extlight.extensions.file.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.extensions.file.mapper.FileConfigMapper;
import com.extlight.extensions.file.model.FileConfig;
import com.extlight.extensions.file.model.dto.FileConfigDTO;
import com.extlight.extensions.file.model.vo.FileConfigVO;
import com.extlight.extensions.file.service.FileConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author MoonlightL
 * @ClassName: FileConfigServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 文件配置 ServiceImpl
 * @DateTime: 2019-08-02 00:04:21
 */
@Service
public class FileConfigServiceImpl extends BaseServiceImpl<FileConfig, FileConfigVO> implements FileConfigService {

    @Autowired
    private FileConfigMapper fileConfigMapper;

    @Override
    public BaseMapper<FileConfig> getBaseMapper() {
        return this.fileConfigMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(FileConfig.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            FileConfigDTO fileConfigDTO = (FileConfigDTO) params;
            // TODO 填充查询条件
        }

        return example;
    }

}