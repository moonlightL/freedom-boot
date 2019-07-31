package com.extlight.core.component.file;

import com.extlight.common.exception.GlobalException;

import java.io.File;
import java.io.InputStream;

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
     * @param file
     * @return
     * @throws GlobalException
     */
    boolean upload(File file) throws GlobalException;

    /**
     * 上传文件
     * @param inputStream
     * @return
     * @throws GlobalException
     */
    boolean upload(InputStream inputStream) throws GlobalException;

    /**
     * 下载文件
     * @param url
     * @throws GlobalException
     */
    void download(String url) throws GlobalException;

    int getCode();
}
