package com.extlight.extensions.monitor.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.extensions.monitor.model.Server;
import com.extlight.extensions.monitor.service.MonitorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: ServerController
 * @ProjectName freedom-boot
 * @Description: 服务器控制器
 * @Date 2019/5/31 16:17
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

    @Autowired
    private MonitorService monitorService;

    /**
     * 服务器信息列表
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("monitor:server:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        return render("listUI", resultMap);
    }

    /**
     * 获取服务器信息
     * @return
     * @throws GlobalException
     */
    @PostMapping("/getServerInfo.json")
    @RequiresPermissions("monitor:server:listUI")
    @ResponseBody
    public Result getServerInfo() throws GlobalException {
        Server serviceInfo = this.monitorService.getServiceInfo();
        return serviceInfo != null ? Result.success(serviceInfo) : Result.fail();
    }
}
