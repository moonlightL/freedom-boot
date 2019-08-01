package com.extlight.common.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: moonlight
 * @ClassName: HttpUtil
 * @ProjectName: freedom-boot
 * @Description: Http 工具类
 * @DateTime: 2019-08-02 00:54
 */
public class HttpUtil {


	private HttpUtil() {}

	/**
	 * 是否是 ajax 请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return !StringUtils.isEmpty(request.getHeader("x-requested-with")) && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
	}
}
