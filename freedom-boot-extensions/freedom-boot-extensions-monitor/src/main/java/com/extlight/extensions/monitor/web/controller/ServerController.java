package com.extlight.extensions.monitor.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.monitor.service.MonitorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        resultMap.put("server", this.monitorService.getServiceInfo());
        return render("listUI", resultMap);
    }
}
