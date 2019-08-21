package com.extlight.extensions.terminal.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.exception.GlobalException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author MoonlightL
 * @Title: TerminalController
 * @ProjectName: freedom-boot
 * @Description: 终端控制器
 * @DateTime: 2019/8/21 18:10
 */
@Controller
@RequestMapping("/terminal/ssh")
public class TerminalController extends BaseController {

	/**
	 * 列表页面
	 *
	 * @param resultMap
	 * @return
	 * @throws GlobalException
	 */
	@GetMapping("/listUI.html")
	@RequiresPermissions("terminal:ssh:listUI")
	public String listUI(Map<String, Object> resultMap) throws GlobalException {
		return render(LIST_PAGE, resultMap);
	}
}
