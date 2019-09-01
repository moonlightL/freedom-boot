package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.PageUtil;
import com.extlight.core.constant.SysLogExceptionEnum;
import com.extlight.core.model.SysLog;
import com.extlight.core.model.dto.SysLogDTO;
import com.extlight.core.service.SysLogService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * @Author MoonlightL
 * @ClassName: SysLogController
 * @ProjectName freedom-boot
 * @Description: 系统日志控制器
 * @Date 2019-07-09 13:53:07
 */
@Controller
@RequestMapping("/core/log")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    //#########################################【页面请求】##################################################

    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("core:log:listUI")
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
    @RequiresPermissions("core:log:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysLog target = this.sysLogService.getById(id);
        if (target == null) {
            ExceptionUtil.throwEx(SysLogExceptionEnum.ERROR_LOG_NOT_EXIST);
        }

        resultMap.put("vo", target.toVoModel());
        return render("detailUI", resultMap);
    }


    //#########################################【AJAX 请求】##################################################

    @GetMapping("/list.json")
    @RequiresPermissions("core:log:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) SysLogDTO params) throws GlobalException {
        PageInfo<SysLog> pageInfo = this.sysLogService.page(params);
        return Result.success(PageUtil.toVO(pageInfo));
    }
}
