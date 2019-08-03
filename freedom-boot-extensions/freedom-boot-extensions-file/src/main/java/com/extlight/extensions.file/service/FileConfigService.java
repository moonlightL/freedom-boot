package com.extlight.extensions.file.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.file.model.FileConfig;
import com.extlight.extensions.file.model.vo.FileConfigVO;

import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: FileConfigService
 * @ProjectName: freedom-boot
 * @Description: 文件配置 Service
 * @DateTime: 2019-08-02 00:04:21
 */
public interface FileConfigService extends BaseService<FileConfig, FileConfigVO> {

	/**
	 * 获取文件配置
	 * @return
	 * @throws GlobalException
	 */
	Map<String, String> getFileConfigMap() throws GlobalException;

	/**
	 * 保存文件配置
	 * @param paramMap
	 * @return
	 * @throws GlobalException
	 */
	boolean save(Map<String, String> paramMap) throws GlobalException;
}

