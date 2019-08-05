package com.extlight.common.utils;

import com.extlight.common.exception.GlobalException;
import com.extlight.common.exception.GlobalExceptionMap;

/**
 * @Author: MoonlightL
 * @ClassName: ExceptionUtil
 * @ProjectName: freedom-boot
 * @Description: 异常工具类
 * @DateTime: 2019-08-04 11:52
 */
public class ExceptionUtil {

	private ExceptionUtil() {}

	/**
	 * 抛异常
	 * @param globalExceptionMap
	 */
	public static void throwEx(GlobalExceptionMap globalExceptionMap) {
		throw new GlobalException(globalExceptionMap);
	}

	/**
	 * 抛异常
	 * @param code
	 * @param message
	 */
	public static void throwEx(int code, String message) {
		throw new GlobalException(code, message);
	}
}
