package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.log.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.core.component.CoreModule;
import com.extlight.core.constant.SysDepartmentExceptionEnum;
import com.extlight.core.model.SysDepartment;
import com.extlight.core.model.dto.SysDepartmentDTO;
import com.extlight.core.service.SysDepartmentService;
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
 * @ClassName: SysDepartmentController
 * @ProjectName: freedom-boot
 * @Description: 部门表控制器 注意：需要修改 @ActionLog 的 module
 * @DateTime: 2019-10-14 13:33:04
 */
@Controller
@RequestMapping("/core/department")
public class SysDepartmentController extends BaseController {

    @Autowired
    private SysDepartmentService sysDepartmentService;

    /**
     * 新增页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/saveUI.html")
    @RequiresPermissions("sys:department:save")
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
    @RequiresPermissions("sys:department:update")
    public String updateUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysDepartment target = this.sysDepartmentService.getById(id);
        if (target == null) {
	        ExceptionUtil.throwEx(SysDepartmentExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
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
    @RequiresPermissions("sys:department:listUI")
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
    @RequiresPermissions("sys:department:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysDepartment target = this.sysDepartmentService.getById(id);
        if (target == null) {
	        ExceptionUtil.throwEx(SysDepartmentExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        resultMap.put("vo", target.toVoModel());
        resultMap.put("readOnly", true);
        return render(DETAIL_PAGE, resultMap);
    }

    //
    @PostMapping("/save.json")
    @RequiresPermissions("sys:department:save")
    @ResponseBody
    @ActionLog(value="新增", module = CoreModule.class, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) SysDepartmentDTO sysDepartmentDto) throws GlobalException {
        SysDepartment sysDepartment = sysDepartmentDto.toDoModel();
        return this.sysDepartmentService.save(sysDepartment) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("sys:department:remove")
    @ResponseBody
    @ActionLog(value="删除", module = CoreModule.class, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        int num;
        if (idArr.length == 1) {
            num = this.sysDepartmentService.remove(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            num = this.sysDepartmentService.removeBatch(idList);
        }
        return num > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/update.json")
    @RequiresPermissions("sys:department:update")
    @ResponseBody
    @ActionLog(value="编辑", module = CoreModule.class, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) SysDepartmentDTO sysDepartmentDTO) throws GlobalException {
        SysDepartment target = this.sysDepartmentService.getById(sysDepartmentDTO.getId());
        if (target == null) {
	        ExceptionUtil.throwEx(SysDepartmentExceptionEnum.ERROR_RESOURCE_NOT_EXIST);
        }

        SysDepartment sysDepartment = sysDepartmentDTO.toDoModel();
        return this.sysDepartmentService.update(sysDepartment) > 0 ? Result.success() : Result.fail();
    }

    @GetMapping("/list.json")
    @RequiresPermissions("sys:department:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) SysDepartmentDTO params) throws GlobalException {
        PageInfo<SysDepartment> pageInfo = this.sysDepartmentService.page(params);
        return Result.success(pageInfo);
    }

}
