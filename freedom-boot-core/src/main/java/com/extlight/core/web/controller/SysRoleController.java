package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.log.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.JsonUtil;
import com.extlight.core.component.CoreModule;
import com.extlight.core.constant.SysRoleExceptionEnum;
import com.extlight.core.model.SysRole;
import com.extlight.core.model.dto.SysRoleDTO;
import com.extlight.core.model.vo.TreeNode;
import com.extlight.core.service.SysPermissionService;
import com.extlight.core.service.SysRoleService;
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
 * @ClassName: SysRoleController
 * @ProjectName freedom-boot
 * @Description: 系统角色控制器
 * @Date 2019/7/1 15:49
 */
@Controller
@RequestMapping("/core/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

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
    @RequiresPermissions("core:role:save")
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
    @RequiresPermissions("core:role:update")
    public String updateUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysRole target = this.sysRoleService.getById(id);
        if (target == null) {
            ExceptionUtil.throwExToPage(SysRoleExceptionEnum.ERROR_ROLE_NOT_EXIST);
        }

        resultMap.put("vo", target.toVoModel());
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
    @RequiresPermissions("core:role:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        resultMap.put("assignPermissionUrl", "/core/role/assignPermissionUI/{roleId}.html");
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
    @RequiresPermissions("core:role:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysRole target = this.sysRoleService.getById(id);
        if (target == null) {
            ExceptionUtil.throwExToPage(SysRoleExceptionEnum.ERROR_ROLE_NOT_EXIST);
        }

        resultMap.put("vo", target.toVoModel());
        resultMap.put("readOnly", true);
        return render(DETAIL_PAGE, resultMap);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("core:role:save")
    @ResponseBody
    @ActionLog(value="新增角色", module = CoreModule.class, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) SysRoleDTO sysRoleDTO) throws GlobalException {
        SysRole sysRole = sysRoleDTO.toDoModel();
        return this.sysRoleService.save(sysRole) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("core:role:remove")
    @ResponseBody
    @ActionLog(value="删除角色", module = CoreModule.class, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        int num;
        if (idArr.length == 1) {
            num = this.sysRoleService.remove(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            num = this.sysRoleService.removeBatch(idList);
        }
        return num > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/update.json")
    @RequiresPermissions("core:role:update")
    @ResponseBody
    @ActionLog(value="编辑角色", module = CoreModule.class, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) SysRoleDTO sysRoleDTO) throws GlobalException {
        SysRole target = this.sysRoleService.getById(sysRoleDTO.getId());
        if (target == null) {
            ExceptionUtil.throwEx(SysRoleExceptionEnum.ERROR_ROLE_NOT_EXIST);
        }

        SysRole sysRole = sysRoleDTO.toDoModel();
        return this.sysRoleService.update(sysRole) > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("core:role:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) SysRoleDTO params) throws GlobalException {
        PageInfo<SysRole> pageInfo = this.sysRoleService.page(params);
        return Result.success(pageInfo);
    }


//#########################################【特殊方法】##################################################

    /**
     * 分配权限页面
     * @param roleId
     * @return
     * @throws GlobalException
     */
    @GetMapping("/assignPermissionUI/{roleId}.html")
    @RequiresPermissions("core:role:assign:permission")
    public String assignPermissionUI(@PathVariable Long roleId, Map<String,Object> resultMap) throws GlobalException {

        SysRole target = this.sysRoleService.getById(roleId);
        if (target == null) {
            return "404";
        }

        resultMap.put("target", target.toVoModel());
        resultMap.put("action", super.getPrefix() + "/assignPermission.json");

        List<TreeNode> permissionList = this.sysPermissionService.findPermissionNodesByRoleId(roleId);
        resultMap.put("zTreeNodes", JsonUtil.toStr(permissionList, false));
        return render("assignPermissionUI", resultMap);
    }

    /**
     * 分配权限
     * @param roleId
     * @param permissionIdStr
     * @return
     */
    @PostMapping("/assignPermission.json")
    @RequiresPermissions("core:role:assign:permission")
    @ResponseBody
    @ActionLog(value="分配权限", module = CoreModule.class, actionType = ActionEnum.OTHER)
    public Result assignPermission(Long roleId, String permissionIdStr) {
        return this.sysRoleService.asignPermission(roleId, permissionIdStr) > 0 ? Result.success() : Result.fail();
    }
}
