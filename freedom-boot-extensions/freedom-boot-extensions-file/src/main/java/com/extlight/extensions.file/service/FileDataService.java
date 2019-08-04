package com.extlight.extensions.file.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.file.model.FileData;
import com.extlight.extensions.file.model.vo.FileDataVO;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: FileDataService
 * @ProjectName: freedom-boot
 * @Description: 文件数据 Service
 * @DateTime: 2019-08-02 00:04:21
 */
public interface FileDataService extends BaseService<FileData, FileDataVO> {

	/**
	 * 上传文件
	 * @param originalFilename
	 * @param contentType
	 * @param data
	 * @return
	 */
	String uploadFile(String originalFilename, String contentType, byte[] data) throws GlobalException;

	/**
	 * 下载文件
	 * @param id
	 * @return
	 */
	byte[] downloadFile(Long id) throws GlobalException;

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	boolean removeFile(Long id) throws GlobalException;

	/**
	 * 删除文件
	 * @param idList
	 * @return
	 */
	boolean removeFileBatch(List<Long> idList) throws GlobalException;
}

