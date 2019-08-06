package com.extlight.extensions.file.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.extensions.file.component.file.FileManageEnum;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.model.dto.FileDataDTO;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.extlight.extensions.file.service.FileDataService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @Author MoonlightL
 * @ClassName: FileDataController
 * @ProjectName: freedom-boot
 * @Description: 文件数据控制器
 * @DateTime: 2019-08-02 00:04:21
 */
@Controller
@RequestMapping("/file/data")
public class FileDataController extends BaseController {

    @Autowired
    private FileDataService fileDataService;

    @Autowired
    private FileConfigService fileConfigService;

    /**
     * 新增页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/saveUI.html")
    @RequiresPermissions("file:data:save")
    public String saveUI(Map<String,Object> resultMap) throws GlobalException {
        return render(SAVE_PAGE, resultMap);
    }


    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("file:data:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
        resultMap.put("fileManage", FileManageEnum.getMessageByCode(Integer.valueOf(fileConfigMap.get(FileConstant.MANAGE_MODE))));
        resultMap.put("downloadUrl", super.getPrefix() + "/download/{id}.html");
        return render(LIST_PAGE, resultMap);
    }

    /**
     * 文件下载
     * @param id
     * @throws GlobalException
     */
    @GetMapping("/download/{id}.html")
    @RequiresPermissions("file:data:download")
    @ActionLog(value = "文件下载", moduleName = ModuleEnum.FILE, actionType = ActionEnum.DOWNLOAD)
    public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FileDataVO vo = this.fileDataService.getById(id);
        if (vo == null) {
            ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_NOT_EXIST);
        }

        byte[] data = this.fileDataService.downloadFile(id);
        super.download(data, vo.getName(), request, response);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("file:data:save")
    @ResponseBody
    @ActionLog(value="文件上传", moduleName = ModuleEnum.FILE, actionType = ActionEnum.SAVE)
    public Result save(MultipartFile[] files) throws GlobalException {
        if (files.length == 0) {
            ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_NOT_EXIST);
        }

        List<String> filePathList = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            byte[] data = null;
            try {
                data = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String url = this.fileDataService.uploadFile(file.getOriginalFilename(), file.getContentType(), data);
            filePathList.add(url);
        }

        return Result.success(filePathList);
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("file:data:remove")
    @ResponseBody
    @ActionLog(value="删除", moduleName = ModuleEnum.FILE, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        boolean flag;
        if (idArr.length == 1) {
            flag = this.fileDataService.removeFile(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            flag = this.fileDataService.removeFileBatch(idList);
        }
        return flag ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("file:data:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) FileDataDTO params) throws GlobalException {
        PageInfo<FileDataVO> pageInfo = this.fileDataService.page(params);
        return Result.success(pageInfo);
    }

}
