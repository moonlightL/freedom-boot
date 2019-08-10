package com.extlight.common.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.CacheUtil;
import com.extlight.common.utils.DateUtil;
import com.extlight.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * @Author MoonlightL
 * @Title: LocalFileServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 默认
 * @DateTime: 2019/7/31 16:45
 */
@Component
@Slf4j
public class LocalFileServiceImpl implements FileService {

    private static final String CONFIG_KEY = "fileConfigMap";

    @Override
    public FileResponse upload(FileRequest fileRequest) throws GlobalException {

        FileResponse fileResponse = new FileResponse();

        try {
            String uploadDir = this.getUploadDir(fileRequest);

            ByteArrayInputStream bis = new ByteArrayInputStream(fileRequest.getData());

            uploadDir = uploadDir + "/files/" + DateUtil.toStr(new Date(), "yyyy/MM/dd");
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File dest = new File(uploadDir, fileRequest.getFileName());
            FileUtils.copyToFile(bis, dest);

            fileResponse.setSuccess(true).setUrl(dest.getAbsolutePath());
            return fileResponse;

        } catch (GlobalException e) {
            throw e;

        } catch (Exception e) {
            log.error("========【默认管理】文件 fileName: {} 文件上传失败=============", fileRequest.getFileName());
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public FileResponse download(FileRequest fileRequest) throws GlobalException {
        FileResponse fileResponse = new FileResponse();
        try {
            byte[] data = FileUtils.readFileToByteArray(new File(fileRequest.getUrl()));
            fileResponse.setSuccess(true).setData(data);

        } catch (Exception e) {
            log.error("========【默认管理】文件 url: {} 文件下载失败=============", fileRequest.getUrl());
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public FileResponse remove(FileRequest fileRequest) throws GlobalException {
        FileResponse fileResponse = new FileResponse();
        File file = new File(fileRequest.getUrl());
        if (!file.exists()) {
            return fileResponse;
        }

        try {
            FileUtils.forceDelete(file);
            fileResponse.setSuccess(true);
            return fileResponse;

        } catch (Exception e) {
            log.error("========【默认管理】文件 url: {} 文件删除失败=============", fileRequest.getUrl());
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public int getCode() {
        return FileManageEnum.LOCAL.getCode();
    }

    private String getUploadDir(FileRequest fileRequest) {
        String uploadDir = fileRequest.getUploadDir();
        if (StringUtil.isBlank(uploadDir)) {

            Map<String, String> fileConfigMap = CacheUtil.get(CONFIG_KEY);
            if (fileConfigMap != null
                && fileConfigMap.containsKey(GlobalFileConstant.UPLOAD_DIR)
                && !StringUtil.isBlank(fileConfigMap.get(GlobalFileConstant.UPLOAD_DIR))) {
                uploadDir = fileConfigMap.get(GlobalFileConstant.UPLOAD_DIR);
            } else {
                uploadDir = System.getProperty("user.home");
            }

        }

        return uploadDir;
    }

}
