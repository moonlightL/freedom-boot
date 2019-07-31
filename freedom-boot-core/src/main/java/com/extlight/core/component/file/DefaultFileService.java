package com.extlight.core.component.file;

import com.extlight.common.exception.GlobalException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * @Author MoonlightL
 * @Title: DefaultFileService
 * @ProjectName: freedom-boot
 * @Description: 默认
 * @DateTime: 2019/7/31 16:45
 */
@Component("defaultFileService")
public class DefaultFileService implements FileService {



    @Override
    public boolean upload(File file) throws GlobalException {
        return false;
    }

    @Override
    public boolean upload(InputStream inputStream) throws GlobalException {
        return false;
    }

    @Override
    public void download(String url) throws GlobalException {

    }

    @Override
    public int getCode() {
        return ModeEnum.DEFAULT.getCode();
    }
}
