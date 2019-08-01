package com.extlight.extensions.file.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ThreadUtil;
import com.extlight.extensions.file.component.file.FileService;
import com.extlight.extensions.file.component.file.FileServiceFactory;
import com.extlight.extensions.file.component.file.ModeEnum;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.mapper.FileDataMapper;
import com.extlight.extensions.file.model.FileData;
import com.extlight.extensions.file.model.dto.FileDataDTO;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: FileDataServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 文件数据 ServiceImpl
 * @DateTime: 2019-08-02 00:04:21
 */
@Service
public class FileDataServiceImpl extends BaseServiceImpl<FileData, FileDataVO> implements FileDataService {

    @Autowired
    private FileDataMapper fileDataMapper;

    @Autowired
    private FileServiceFactory fileServiceFactory;

    @Override
    public BaseMapper<FileData> getBaseMapper() {
        return this.fileDataMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(FileData.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            FileDataDTO fileDataDTO = (FileDataDTO) params;
            // TODO 填充查询条件
        }

        return example;
    }

    @Override
    public byte[] downloadFile(Long id) {

        FileDataVO fileDataVO = super.getById(id);

        FileService fileService = this.getFileService();

        return fileService.download(fileDataVO.getUrl());
    }

    @Override
    public String uploadFile(String fileName, String contentType, byte[] data) {
        FileService fileService = this.getFileService();
        String url = fileService.upload(fileName, data);

        if (StringUtils.isBlank(url)) {
            return null;
        }

        FileData fileData = new FileData();
        fileData.setName(fileName)
                .setContentType(contentType)
                .setUrl(url)
                .setThumbnailUrl(fileData.getUrl())
                .setOperatorId(ThreadUtil.get())
                .setCode(fileService.getCode());

        super.save(fileData);

        return fileData.getUrl();
    }

    @Override
    public boolean removeFile(Long id) {
        FileDataVO fileDataVO = super.getById(id);
        if (fileDataVO == null) {
            throw new GlobalException(FileDataExceptionEnum.ERROR_FILE_DELETED);
        }

        FileService fileService = this.getFileService();
        boolean removeFlag = fileService.remove(fileDataVO.getUrl());

        boolean result = removeFlag;
        if (removeFlag) {
            result = (super.remove(id) > 0);
        }

        return result;
    }

    @Override
    public boolean removeFileBatch(List<Long> idList) {
        List<Boolean> tmp = new ArrayList<>(idList.size());
        for (Long id : idList) {
            tmp.add(this.removeFile(id));
        }

        return tmp.stream().filter(i -> i == false).count() == 0;
    }

    private FileService getFileService() {
        // TODO 测试
        int code = ModeEnum.DEFAULT.getCode();
        // 下载文件
        FileService fileService = this.fileServiceFactory.getInstance(code);

        return fileService;
    }
}