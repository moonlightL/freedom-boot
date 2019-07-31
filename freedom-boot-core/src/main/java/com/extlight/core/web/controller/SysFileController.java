package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.core.constant.SysFileExceptionEnum;
import com.extlight.core.model.SysFile;
import com.extlight.core.model.dto.SysFileDTO;
import com.extlight.core.model.vo.SysFileVO;
import com.extlight.core.service.SysFileService;
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
    @RequiresPermissions("core:sysFile:save")
    public String saveUI(Map<String,Object> resultMap) throws GlobalException {
        return render(SAVE_PAGE, resultMap);
    }

    /**
     * 修改页面
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/updateUI/{id}.html")
    @RequiresPermissions("core:sysFile:update")
    public String updateUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
            SysFileVO vo = this.sysFileService.getById(id);
        if (vo == null) {
            throw new GlobalException(SysFileExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", false);
        return render(UPDATE_PAGE, resultMap);
    }

    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("core:sysFile:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        return render(LIST_PAGE, resultMap);
    }

    /**
     * 详情页面
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/detailUI/{id}.html")
    @RequiresPermissions("core:sysFile:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
            SysFileVO vo = this.sysFileService.getById(id);
        if (vo == null) {
            throw new GlobalException(SysFileExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", true);
        return render(DETAIL_PAGE, resultMap);
    }


    @PostMapping("/save.json")
    @RequiresPermissions("core:file:save")
    @ResponseBody
    @ActionLog(value="新增", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) SysFileDTO sysFileDto) throws GlobalException {
        SysFile sysFile = sysFileDto.toDo(SysFile.class);
        return this.sysFileService.save(sysFile) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("core:sysFile:remove")
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

    @PostMapping("/update.json")
    @RequiresPermissions("core:sysFile:update")
    @ResponseBody
    @ActionLog(value="编辑", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) SysFileDTO sysFileDTO) throws GlobalException {
        SysFileVO dbData = this.sysFileService.getById(sysFileDTO.getId());
        if (dbData == null) {
            throw new GlobalException(SysFileExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        SysFile sysFile = sysFileDTO.toDo(SysFile.class);
        return this.sysFileService.update(sysFile) > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("core:sysFile:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) SysFileDTO params) throws GlobalException {
        PageInfo<SysFileVO> pageInfo = this.sysFileService.page(params);
        return Result.success(pageInfo);
    }

}
