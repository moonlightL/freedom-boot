package com.extlight.extensions.file.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.extensions.file.model.FileConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: FileConfigMapper
 * @ProjectName: freedom-boot
 * @Description: 文件配置 Mapper
 * @DateTime: 2019-08-02 00:04:21
 */
public interface FileConfigMapper extends BaseMapper<FileConfig> {

	/**
	 * 修改文件配置
	 * @param fjleConfigList
	 */
	void updateByConfigName(@Param("list") List<FileConfig> fjleConfigList);
}
