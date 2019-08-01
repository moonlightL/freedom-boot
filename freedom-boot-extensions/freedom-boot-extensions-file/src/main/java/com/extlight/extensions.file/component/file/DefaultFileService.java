package com.extlight.extensions.file.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.core.web.config.CoreConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @Author MoonlightL
 * @Title: DefaultFileService
 * @ProjectName: freedom-boot
 * @Description: 默认
 * @DateTime: 2019/7/31 16:45
 */
@Component("defaultFileService")
@Slf4j
public class DefaultFileService implements FileService {

    @Autowired
    private CoreConfig coreConfig;

    @Override
    public String upload(String fileName, byte[] data) throws GlobalException {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        File dest = new File(this.coreConfig.getFileDir(), fileName);

        try {
            FileUtils.copyToFile(bis, dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            log.error("========文件fileName: {} 文件上传失败=============", fileName);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public byte[] download(String url) throws GlobalException {
        byte[] data = new byte[0];

        try {
            data = FileUtils.readFileToByteArray(new File(url));
        } catch (IOException e) {
            log.error("========文件url: {} 文件下载失败=============", url);
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public boolean remove(String url) throws GlobalException {

        File file = new File(url);
        if (!file.exists()) {
            return true;
        }

        try {
            FileUtils.forceDelete(file);
            return true;
        } catch (IOException e) {
            log.error("========文件url: {} 文件删除失败=============", url);
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getCode() {
        return ModeEnum.DEFAULT.getCode();
    }
}
