package com.extlight.extensions.file.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.file.mapper.FileConfigMapper;
import com.extlight.extensions.file.model.FileConfig;
import com.extlight.extensions.file.model.dto.FileConfigDTO;
import com.extlight.extensions.file.model.vo.FileConfigVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.extlight.extensions.file.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author MoonlightL
 * @ClassName: FileConfigServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 文件配置 ServiceImpl
 * @DateTime: 2019-08-02 00:04:21
 */
@Service
public class FileConfigServiceImpl extends BaseServiceImpl<FileConfig, FileConfigVO> implements FileConfigService {

    private static final String CONFIG_KEY = "fileConfigMap";

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

    @Override
    public Map<String, String> getFileConfigMap() throws GlobalException {
        Map<String, String> result = CacheUtil.get(CONFIG_KEY);

        if (result == null) {
            List<FileConfigVO> list = super.list();
            result = new HashMap<>(list.size());
            Map<String, String> finalResult = result;
            list.stream().forEach(i -> finalResult.put(i.getConfigName(), i.getConfigValue()));
            CacheUtil.put(CONFIG_KEY, result);
        }

        return result;
    }

    @Override
    public boolean save(Map<String, String> paramMap) throws GlobalException {

        if (paramMap.isEmpty()) {
            return true;
        }

        List<FileConfig> fjleConfigList = new ArrayList<>(paramMap.size());

        paramMap.entrySet().forEach(i -> {
            FileConfig fileConfig = new FileConfig();
            fileConfig.setConfigName(i.getKey())
                      .setConfigValue(i.getValue());
            fjleConfigList.add(fileConfig);
        });

        this.fileConfigMapper.updateByConfigName(fjleConfigList);

        // 清除缓存
        CacheUtil.remove(CONFIG_KEY);

        return true;
    }


}