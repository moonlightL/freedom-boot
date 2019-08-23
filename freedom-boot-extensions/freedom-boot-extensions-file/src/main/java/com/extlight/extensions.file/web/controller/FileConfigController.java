package com.extlight.extensions.file.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.component.log.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.extensions.file.component.FileModule;
import com.extlight.extensions.file.service.FileConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author MoonlightL
 * @ClassName: FileConfigController
 * @ProjectName: freedom-boot
 * @Description: 文件配置控制器
 * @DateTime: 2019-08-02 00:04:21
 */
@Controller
@RequestMapping("/file/config")
public class FileConfigController extends BaseController {

    @Autowired
    private FileConfigService fileConfigService;

    /**
     * 列表页面
     *
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("file:config:listUI")
    public String listUI(Map<String, Object> resultMap) throws GlobalException {
        Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
        resultMap.put("fileConfigMap", fileConfigMap);
        return render(LIST_PAGE, resultMap);
    }


    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("file:config:save")
    @ResponseBody
    @ActionLog(value = "保存", module = FileModule.class, actionType = ActionEnum.SAVE)
    public Result save(@RequestParam Map<String, String> map) throws GlobalException {
        return this.fileConfigService.save(map) ? Result.success() : Result.fail();
    }

}
