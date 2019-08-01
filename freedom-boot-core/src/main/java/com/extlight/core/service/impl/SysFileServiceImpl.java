package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.component.ShiroService;
import com.extlight.core.component.file.FileService;
import com.extlight.core.component.file.FileServiceFactory;
import com.extlight.core.component.file.ModeEnum;
import com.extlight.core.constant.SysFileExceptionEnum;
import com.extlight.core.mapper.SysFileMapper;
import com.extlight.core.model.SysFile;
import com.extlight.core.model.dto.SysFileDTO;
import com.extlight.core.model.vo.SysFileVO;
import com.extlight.core.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysFileServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 文件 ServiceImpl
 * @DateTime: 2019-07-31 17:42:54
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile, SysFileVO> implements SysFileService {

    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private FileServiceFactory fileServiceFactory;

    @Autowired
    private ShiroService shiroService;

    @Override
    public BaseMapper<SysFile> getBaseMapper() {
        return this.sysFileMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysFile.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            SysFileDTO sysFileDTO = (SysFileDTO) params;
            // TODO 填充查询条件
        }

        return example;
    }

    @Override
    public String uploadFile(String fileName, String contentType, byte[] data) throws GlobalException {

        FileService fileService = this.getFileService();
        String url = fileService.upload(fileName, data);

        if (StringUtils.isBlank(url)) {
            return null;
        }

        SysFile sysFile = new SysFile();
        sysFile.setName(fileName)
               .setContentType(contentType)
               .setUrl(url)
               .setThumbnailUrl(sysFile.getUrl())
               .setOperatorId(this.shiroService.getUserId())
               .setCode(fileService.getCode());

        super.save(sysFile);

        return sysFile.getUrl();
    }

    @Override
    public byte[] downloadFile(Long id) throws GlobalException {

        SysFileVO sysFileVO = super.getById(id);

        FileService fileService = this.getFileService();

        return fileService.download(sysFileVO.getUrl());
    }

    @Override
    public boolean removeFile(Long id) throws GlobalException {

        SysFileVO sysFileVO = super.getById(id);
        if (sysFileVO == null) {
            throw new GlobalException(SysFileExceptionEnum.ERROR_FILE_DELETED);
        }

        FileService fileService = this.getFileService();
        boolean removeFlag = fileService.remove(sysFileVO.getUrl());

        boolean result = removeFlag;
        if (removeFlag) {
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