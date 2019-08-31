package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.log.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.core.component.CoreModule;
import com.extlight.core.constant.SysPermissionExceptionEnum;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.dto.SysPermissionDTO;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.service.SysPermissionService;
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
 * @ClassName: SysPermissionController
 * @ProjectName freedom-boot
 * @Description: 系统权限控制器
 * @Date 2019/7/1 17:30
 */
@Controller
@RequestMapping("/core/permission")
public class SysPermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;

    //#########################################【页面请求】##################################################

    /**
     * 新增页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/saveUI.html")
    @RequiresPermissions("core:permission:save")
    public String saveUI(Map<String,Object> resultMap) throws GlobalException {
        List<SysPermissionVO> parentList = this.sysPermissionService.findHierarchyPermissionList();
        resultMap.put("parentList", this.decorateName(parentList, "┣─"));
        return render(SAVE_PAGE, resultMap);
    }


    private List<SysPermissionVO> decorateName(List<SysPermissionVO> list, String prefix) {

        List<SysPermissionVO> result = new ArrayList<>();
        for (SysPermissionVO sysPermissionVO : list) {
            result.add(sysPermissionVO);
            for (SysPermissionVO child : sysPermissionVO.getChildren()) {
                child.setName(" " + prefix + child.getName());
                result.add(child);
            }
        }

        return result;
    }

    /**
     * 修改页面
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/updateUI/{id}.html")
    @RequiresPermissions("core:permission:update")
    public String updateUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysPermission target = this.sysPermissionService.getById(id);
        if (target == null) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
        }

        resultMap.put("vo", target.toVoModel());
        resultMap.put("readOnly", false);

        List<SysPermissionVO> parentList = this.sysPermissionService.findHierarchyPermissionList();
        resultMap.put("parentList", this.decorateName(parentList, "┣─"));
        return render(UPDATE_PAGE, resultMap);
    }

    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("core:permission:listUI")
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
    @RequiresPermissions("core:permission:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysPermission target = this.sysPermissionService.getById(id);
        if (target == null) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
        }

        resultMap.put("vo", target.toVoModel());
        resultMap.put("readOnly", true);

        List<SysPermissionVO> parentList = this.sysPermissionService.findHierarchyPermissionList();
        resultMap.put("parentList", this.decorateName(parentList, "┣─"));

        return render(DETAIL_PAGE, resultMap);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("core:permission:save")
    @ResponseBody
    @ActionLog(value="新增权限", module = CoreModule.class, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) SysPermissionDTO sysPermissionDTO) throws GlobalException {
        SysPermission sysPermission = sysPermissionDTO.toDoModel();
        return this.sysPermissionService.save(sysPermission) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("core:permission:remove")
    @ResponseBody
    @ActionLog(value="删除权限", module = CoreModule.class, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        int num;
        if (idArr.length == 1) {
            num = this.sysPermissionService.remove(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            num = this.sysPermissionService.removeBatch(idList);
        }
        return num > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/update.json")
    @RequiresPermissions("core:permission:update")
    @ResponseBody
    @ActionLog(value="编辑权限", module = CoreModule.class, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) SysPermissionDTO sysPermissionDTO) throws GlobalException {
        SysPermission target = this.sysPermissionService.getById(sysPermissionDTO.getId());
        if (target == null) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
        }

        SysPermission sysPermission = sysPermissionDTO.toDoModel();
        return this.sysPermissionService.update(sysPermission) > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("core:permission:listUI")
    @ResponseBody
    public Result list(SysPermissionDTO sysPermissionDTO) throws GlobalException {
        PageInfo<SysPermission> pageInfo = this.sysPermissionService.pageAll(sysPermissionDTO);
        return Result.success(pageInfo);
    }


    @PostMapping("/moduleList.json")
    @RequiresPermissions("core:permission:listUI")
    @ResponseBody
    public Result moduleList() throws GlobalException {
        List<SysPermissionVO> moduleList = this.sysPermissionService.findModuleList();
        return Result.success(moduleList);
    }

}
