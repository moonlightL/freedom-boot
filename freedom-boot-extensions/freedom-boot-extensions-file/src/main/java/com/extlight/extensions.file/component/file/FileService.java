package com.extlight.extensions.file.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.file.model.vo.FileDataVO;

/**
 * @Author MoonlightL
 * @Title: FileService
 * @ProjectName: freedom-boot
 * @Description: 文件Service
 * @DateTime 2019/7/31 16:36
 */
public interface FileService {

    /**
     * 上传文件
     * @param fileName
     * @param data
     * @return
     * @throws GlobalException
     */
    FileResponse upload(String fileName, byte[] data) throws GlobalException;

    /**
     * 下载文件
     * @param fileDataVO
     * @return
     * @throws GlobalException
     */
    FileResponse download(FileDataVO fileDataVO) throws GlobalException;

    /**
     * 删除文件
     * @param fileDataVO
     * @return
     * @throws GlobalException
     */
    FileResponse remove(FileDataVO fileDataVO) throws GlobalException;

    /**
     * 文件管理方式，参考：FileManageEnum 枚举
     * @return
     */
    int getCode();

}
