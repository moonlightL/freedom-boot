package com.extlight.extensions.file.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Author MoonlightL
 * @Title: DefaultFileServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 默认
 * @DateTime: 2019/7/31 16:45
 */
@Component("defaultFileService")
@Slf4j
public class DefaultFileServiceImpl implements FileService {

    @Autowired
    private FileConfigService fileConfigService;

    @Override
    public FileResponse upload(String fileName, byte[] data) throws GlobalException {

        FileResponse fileResponse = new FileResponse();

        try {

            Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();

            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            File dest = new File(fileConfigMap.get(FileConstant.UPLOAD_DIR), fileName);

            FileUtils.copyToFile(bis, dest);

            fileResponse.setSuccess(true).setUrl(dest.getAbsolutePath());
            return fileResponse;

        } catch (Exception e) {
            log.error("========【默认管理】文件 fileName: {} 文件上传失败=============", fileName);
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public FileResponse download(FileDataVO fileDataVO) throws GlobalException {
        FileResponse fileResponse = new FileResponse();
        String url = fileDataVO.getUrl();
        try {
            byte[] data = FileUtils.readFileToByteArray(new File(url));
            fileResponse.setSuccess(true).setData(data);

        } catch (IOException e) {
            log.error("========【默认管理】文件 url: {} 文件下载失败=============", url);
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public FileResponse remove(FileDataVO fileDataVO) throws GlobalException {
        FileResponse fileResponse = new FileResponse();
        String url = fileDataVO.getUrl();
        File file = new File(url);
        if (!file.exists()) {
            return fileResponse;
        }

        try {
            FileUtils.forceDelete(file);
            fileResponse.setSuccess(true);
            return fileResponse;

        } catch (IOException e) {
            log.error("========【默认管理】文件 url: {} 文件删除失败=============", url);
            e.printStackTrace();
        }

        return fileResponse;
    }

    @Override
    public int getCode() {
        return ModeEnum.DEFAULT.getCode();
    }
}
