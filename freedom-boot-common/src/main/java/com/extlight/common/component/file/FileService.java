package com.extlight.common.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.StringUtil;

/**
 * @Author MoonlightL
 * @ClassName: FileService
 * @ProjectName: freedom-boot
 * @Description: 文件Service
 * @DateTime 2019/7/31 16:36
 */
public interface FileService {

    /**
     * 协议
     */
    String PROTOCOL = "http";

    /**
     * 上传文件
     * @param fileRequest
     * @return
     * @throws GlobalException
     */
    FileResponse upload(FileRequest fileRequest) throws GlobalException;

    /**
     * 下载文件
     * @param fileRequest
     * @return
     * @throws GlobalException
     */
    FileResponse download(FileRequest fileRequest) throws GlobalException;

    /**
     * 删除文件
     * @param fileRequest
     * @return
     * @throws GlobalException
     */
    FileResponse remove(FileRequest fileRequest) throws GlobalException;

    default String parseUrl(String url) {

        if (StringUtil.isBlank(url)) {
            return null;
        }

        if (!url.startsWith(PROTOCOL)) {
            url = PROTOCOL + "://" + url;
        }

        return url;
    }

    /**
     * 文件管理方式，参考：FileManageEnum 枚举
     * @return
     */
    int getCode();

}
