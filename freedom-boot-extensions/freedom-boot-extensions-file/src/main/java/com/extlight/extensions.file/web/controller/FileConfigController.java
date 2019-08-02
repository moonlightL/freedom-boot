package com.extlight.extensions.file.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.extensions.file.constant.FileConfigExceptionEnum;
import com.extlight.extensions.file.model.FileConfig;
import com.extlight.extensions.file.model.dto.FileConfigDTO;
import com.extlight.extensions.file.model.vo.FileConfigVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
     * 新增页面
     *
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/saveUI.html")
    @RequiresPermissions("file:config:save")
    public String saveUI(Map<String, Object> resultMap) throws GlobalException {
        return render(SAVE_PAGE, resultMap);
    }

    /**
     * 修改页面
     *
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/updateUI/{id}.html")
    @RequiresPermissions("file:config:update")
    public String updateUI(@PathVariable("id") Long id, Map<String, Object> resultMap) throws GlobalException {
        FileConfigVO vo = this.fileConfigService.getById(id);
        if (vo == null) {
            throw new GlobalException(FileConfigExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", false);
        return render(UPDATE_PAGE, resultMap);
    }

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
        return render(LIST_PAGE, resultMap);
    }

    /**
     * 详情页面
     *
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/detailUI/{id}.html")
    @RequiresPermissions("file:config:query")
    public String detailUI(@PathVariable("id") Long id, Map<String, Object> resultMap) throws GlobalException {
        FileConfigVO vo = this.fileConfigService.getById(id);
        if (vo == null) {
            throw new GlobalException(FileConfigExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", true);
        return render(DETAIL_PAGE, resultMap);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("file:config:save")
    @ResponseBody
    @ActionLog(value = "新增", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) FileConfigDTO fileConfigDto) throws GlobalException {
        FileConfig fileConfig = fileConfigDto.toDo(FileConfig.class);
        return this.fileConfigService.save(fileConfig) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("file:config:remove")
    @ResponseBody
    @ActionLog(value = "删除", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        int num;
        if (idArr.length == 1) {
            num = this.fileConfigService.remove(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            num = this.fileConfigService.removeBatch(idList);
        }
        return num > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/update.json")
    @RequiresPermissions("file:config:update")
    @ResponseBody
    @ActionLog(value = "编辑", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) FileConfigDTO fileConfigDTO) throws GlobalException {
        FileConfigVO dbData = this.fileConfigService.getById(fileConfigDTO.getId());
        if (dbData == null) {
            throw new GlobalException(FileConfigExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        FileConfig fileConfig = fileConfigDTO.toDo(FileConfig.class);
        return this.fileConfigService.update(fileConfig) > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("file:config:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) FileConfigDTO params) throws GlobalException {
        PageInfo<FileConfigVO> pageInfo = this.fileConfigService.page(params);
        return Result.success(pageInfo);
    }

}
