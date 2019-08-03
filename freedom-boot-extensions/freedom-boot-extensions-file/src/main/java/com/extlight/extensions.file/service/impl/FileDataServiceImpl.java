package com.extlight.extensions.file.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ThreadUtil;
import com.extlight.extensions.file.component.file.FileResponse;
import com.extlight.extensions.file.component.file.FileService;
import com.extlight.extensions.file.component.file.FileServiceFactory;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.mapper.FileDataMapper;
import com.extlight.extensions.file.model.FileData;
import com.extlight.extensions.file.model.dto.FileDataDTO;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.extlight.extensions.file.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private FileConfigService fileConfigService;

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
            // 填充查询条件
            // 只能查看当前用户上传的文件
            criteria.andEqualTo("operatorId", ThreadUtil.get());
        }

        return example;
    }

    @Override
    public String uploadFile(String fileName, String contentType, byte[] data) {
        FileService fileService = this.getFileService();
        FileResponse fileResponse = fileService.upload(fileName, data);

        if (!fileResponse.isSuccess()) {
            return null;
        }

        FileData fileData = new FileData();
        fileData.setName(fileName)
                .setContentType(contentType)
                .setUrl(fileResponse.getUrl())
                .setThumbnailUrl(fileData.getUrl())
                .setFileKey(fileResponse.getKey())
                .setOperatorId(ThreadUtil.get())
                .setCode(fileService.getCode());

        super.save(fileData);

        return fileData.getUrl();
    }

    @Override
    public byte[] downloadFile(Long id) {

        FileDataVO fileDataVO = super.getById(id);

        FileService fileService = this.getFileService();

        FileResponse fileResponse = fileService.download(fileDataVO);

        if (!fileResponse.isSuccess()) {
            return null;
        }

        return fileResponse.getData();
    }

    @Override
    public boolean removeFile(Long id) {
        FileDataVO fileDataVO = super.getById(id);
        if (fileDataVO == null) {
            throw new GlobalException(FileDataExceptionEnum.ERROR_FILE_DELETED);
        }

        FileService fileService = this.getFileService();
        FileResponse fileResponse = fileService.remove(fileDataVO);

        boolean result = fileResponse.isSuccess();
        if (result) {
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

    /**
     * 获取 FileService 实现
     * @return
     */
    private FileService getFileService() {

        Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
        int code = Integer.valueOf(fileConfigMap.get(FileConstant.MANAGE_MODE));
        FileService fileService = this.fileServiceFactory.getInstance(code);

        return fileService;
    }
}