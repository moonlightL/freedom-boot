package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.core.constant.CoreExceptionEnum;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.dto.SysPermissionDTO;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.service.SysPermissionService;
import com.github.pagehelper.Page;
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
@RequestMapping("/system/permission")
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
    @RequiresPermissions("system:permission:save")
    public String saveUI(Map<String,Object> resultMap) throws GlobalException {
        List<SysPermissionVO> parentList = this.sysPermissionService.findParentPermissionList();
        resultMap.put("parentList", parentList);
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
    @RequiresPermissions("system:permission:update")
    public String updateUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysPermissionVO vo = this.sysPermissionService.getById(id);
        if (vo == null) {
            throw new GlobalException(CoreExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", false);

        List<SysPermissionVO> parentList = this.sysPermissionService.findParentPermissionList();
        resultMap.put("parentList", parentList);
        return render(UPDATE_PAGE, resultMap);
    }

    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("system:permission:listUI")
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
    @RequiresPermissions("system:permission:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysPermissionVO vo = this.sysPermissionService.getById(id);
        if (vo == null) {
            throw new GlobalException(CoreExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", true);

        List<SysPermissionVO> parentList = this.sysPermissionService.findParentPermissionList();
        resultMap.put("parentList", parentList);

        return render(DETAIL_PAGE, resultMap);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("system:permission:save")
    @ResponseBody
    @ActionLog(value="新增权限", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) SysPermissionDTO sysPermissionDTO) throws GlobalException {
        SysPermission sysPermission = sysPermissionDTO.toDo(SysPermission.class);
        return this.sysPermissionService.save(sysPermission) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("system:permission:remove")
    @ResponseBody
    @ActionLog(value="删除权限", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.REMOVE)
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
    @RequiresPermissions("system:permission:update")
    @ResponseBody
    @ActionLog(value="编辑权限", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) SysPermissionDTO sysPermissionDTO) throws GlobalException {
        SysPermissionVO dbData = this.sysPermissionService.getById(sysPermissionDTO.getId());
        if (dbData == null) {
            throw new GlobalException(CoreExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        SysPermission sysPermission = sysPermissionDTO.toDo(SysPermission.class);
        return this.sysPermissionService.update(sysPermission) > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("system:permission:listUI")
    @ResponseBody
    public Result list() throws GlobalException {
        List<SysPermissionVO> list = this.sysPermissionService.list();
        Page<SysPermissionVO> page = new Page<>(1, list.size(), false);
        int count = 0;
        if (!list.isEmpty()) {
            page.addAll(list);
            count = this.sysPermissionService.count(null);
        }
        page.setTotal(count);
        return Result.success(new PageInfo<>(page));
    }


}
