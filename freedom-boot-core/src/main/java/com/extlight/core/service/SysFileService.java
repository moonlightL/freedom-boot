package com.extlight.core.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.model.SysFile;
import com.extlight.core.model.vo.SysFileVO;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysFileService
 * @ProjectName: freedom-boot
 * @Description: 文件 Service
 * @DateTime: 2019-07-31 17:42:54
 */
public interface SysFileService extends BaseService<SysFile, SysFileVO> {

    /**
     * 保存文件
     * @param fileName
     * @param contentType
     * @param data
     * @return
     * @throws GlobalException
     */
    String uploadFile(String fileName, String contentType, byte[] data) throws GlobalException;

    /**
     * 下载文件
     * @param id
     * @return
     * @throws GlobalException
     */
    byte[] downloadFile(Long id) throws GlobalException;

	/**
	 * 删除文件
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	boolean removeFile(Long id) throws GlobalException;

	/**
	 * 批量删除文件
	 * @param idList
	 * @return
	 * @throws GlobalException
	 */
	boolean removeFileBatch(List<Long> idList) throws GlobalException;
}

