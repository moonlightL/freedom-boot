package com.extlight.extensions.file.component.file;

import com.extlight.common.exception.GlobalException;

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
    String upload(String fileName, byte[] data) throws GlobalException;

    /**
     * 下载文件
     * @param url
     * @return
     * @throws GlobalException
     */
    byte[] download(String url) throws GlobalException;

    /**
     * 删除文件
     * @param url
     * @return
     * @throws GlobalException
     */
    boolean remove(String url) throws GlobalException;

    /**
     * 文件管理方式，参考：ModeEnum 枚举
     * @return
     */
    int getCode();

}
