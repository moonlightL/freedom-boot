package com.extlight.extensions.file.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.component.file.*;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.ThreadUtil;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.mapper.FileDataMapper;
import com.extlight.extensions.file.model.FileData;
import com.extlight.extensions.file.model.dto.FileDataDTO;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.extlight.extensions.file.service.FileDataService;
import org.springframework.beans.BeanUtils;
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
    protected Example getExample(BaseRequest params) throws GlobalException {

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
    public String uploadFile(String originalFilename, String contentType, byte[] data) throws GlobalException {
        FileService fileService = this.getFileService();

        FileRequest fileRequest = new FileRequest();
        String[] names = originalFilename.split("\\.");
        String newFilename = names[0] + "_" + System.currentTimeMillis() + "." + names[1];
        fileRequest.setUploadDir(this.getUploadDir()).setFilename(newFilename).setData(data);

        FileResponse fileResponse = fileService.upload(fileRequest);

        if (!fileResponse.isSuccess()) {
            ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_UPLOAD);
        }

        FileData fileData = new FileData();
        fileData.setFilename(newFilename)
                .setOriginalFilename(originalFilename)
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
    public byte[] downloadFile(Long id) throws GlobalException {

        FileDataVO fileDataVO = super.getById(id);
        if (fileDataVO == null) {
            ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_DELETED);
        }

        FileService fileService = this.getFileService();

        FileRequest fileRequest = new FileRequest();
        BeanUtils.copyProperties(fileDataVO, fileRequest);
        FileResponse fileResponse = fileService.download(fileRequest);

        if (!fileResponse.isSuccess()) {
            ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_DOWNLOAD);
        }

        return fileResponse.getData();
    }

    @Override
    public boolean removeFile(Long id) throws GlobalException {
        FileDataVO fileDataVO = super.getById(id);
        if (fileDataVO == null) {
            ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_DELETED);
        }

        FileService fileService = this.getFileService();

        FileRequest fileRequest = new FileRequest();
        BeanUtils.copyProperties(fileDataVO, fileRequest);
        FileResponse fileResponse = fileService.remove(fileRequest);

        boolean result = fileResponse.isSuccess();
        if (result) {
            result = (super.remove(id) > 0);
        }

        return result;
    }

    @Override
    public boolean removeFileBatch(List<Long> idList) throws GlobalException {
        List<Boolean> tmp = new ArrayList<>(idList.size());
        for (Long id : idList) {
            tmp.add(this.removeFile(id));
        }

        return tmp.stream().filter(i -> !i).count() == 0;
    }

    /**
     * 获取 FileService 实现
     * @return
     */
    private FileService getFileService() {

        Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
        int code = Integer.valueOf(fileConfigMap.get(GlobalFileConstant.MANAGE_MODE));
        FileService fileService = this.fileServiceFactory.getInstance(code);

        return fileService;
    }

    /**
     * 获取文件上传目录
     * @return
     */
    private String getUploadDir() {
        Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
        return fileConfigMap.get(GlobalFileConstant.UPLOAD_DIR);
    }
}