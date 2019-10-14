package com.extlight.core.web.interceptor;

import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.model.Result;
import com.extlight.common.utils.HttpUtil;
import com.extlight.common.utils.JsonUtil;
import com.extlight.common.utils.ThreadUtil;
import com.extlight.core.constant.SysUserExceptionEnum;
import com.extlight.core.constant.SystemContant;
import com.extlight.core.model.vo.SysUserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: MoonlightL
 * @ClassName: UserInterceptor
 * @ProjectName: freedom-boot
 * @Description: 用户拦截器
 * @DateTime: 2019-08-02 00:50
 */
@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Object obj = request.getSession().getAttribute(SystemContant.CURRENT_USER);
		if (obj == null) {
			if (HttpUtil.isAjax(request)) {
				this.print(response, JsonUtil.toStr(Result.fail(SysUserExceptionEnum.ERROR_LOGIN_EXPIRE), false));
			} else {
				response.sendRedirect("/");
			}
			return false;
		}

		SysUserVO sysUserVO = (SysUserVO) obj;
		if (!sysUserVO.getState()) {
			if (HttpUtil.isAjax(request)) {
				this.print(response, JsonUtil.toStr(Result.fail(GlobalExceptionEnum.ERROR_STATE_WRONG), false));
			} else {
				response.sendRedirect("/");
			}
			return false;
		}

		request.setAttribute("userId", sysUserVO.getId());
		ThreadUtil.set(sysUserVO.getId());

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		ThreadUtil.remove();
	}

	private void print(HttpServletResponse response, String result) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getOutputStream().write(result.getBytes("UTF-8"));
	}

}
