package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.core.constant.SysFileExceptionEnum;
import com.extlight.core.model.dto.SysFileDTO;
import com.extlight.core.model.vo.SysFileVO;
import com.extlight.core.service.SysFileService;
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
 * @ClassName: SysFileController
 * @ProjectName: freedom-boot
 * @Description: 文件控制器
 * @DateTime: 2019-07-31 17:42:54
 */
@Controller
@RequestMapping("/core/file")
public class SysFileController extends BaseController {

    @Autowired
    private SysFileService sysFileService;

    /**
     * 新增页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/saveUI.html")
    @RequiresPermissions("core:file:save")
    public String saveUI(Map<String, Object> resultMap) throws GlobalException {
        return render(SAVE_PAGE, resultMap);
    }

    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("core:file:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        resultMap.put("downloadUrl", "/core/file/download/{id}.html");
        return render(LIST_PAGE, resultMap);
    }

    /**
     * 文件下载
     * @param id
     * @throws GlobalException
     */
    @GetMapping("/download/{id}.html")
    @RequiresPermissions("core:file:download")
    @ActionLog(value = "文件下载", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.DOWNLOAD)
    public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysFileVO vo = this.sysFileService.getById(id);
        if (vo == null) {
            throw new GlobalException(SysFileExceptionEnum.ERROR_FILE_NOT_EXIST);
        }

        byte[] data = this.sysFileService.downloadFile(id);
        super.download(data, vo.getName(), request, response);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("core:file:save")
    @ResponseBody
    @ActionLog(value="文件上传", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.SAVE)
    public Result save(MultipartFile[] files) throws GlobalException {
        if (files.length == 0) {
            throw new GlobalException(SysFileExceptionEnum.ERROR_UPLOAD_FILE_IS_EMPTY);
        }

        List<String> filePathList = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            byte[] data = null;
            try {
                data = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            filePathList.add(this.sysFileService.uploadFile(file.getOriginalFilename(), file.getContentType(), data));
        }

        return Result.success(filePathList);
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("core:file:remove")
    @ResponseBody
    @ActionLog(value="删除", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        int num;
        if (idArr.length == 1) {
            num = this.sysFileService.remove(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            num = this.sysFileService.removeBatch(idList);
        }
        return num > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("core:file:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) SysFileDTO params) throws GlobalException {
        PageInfo<SysFileVO> pageInfo = this.sysFileService.page(params);
        return Result.success(pageInfo);
    }

}
