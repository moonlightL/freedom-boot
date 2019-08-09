package com.extlight.common.utils;

/**
 * @Author: MoonlightL
 * @ClassName: ThreadUtil
 * @ProjectName: freedom-boot
 * @Description: 线程工具类
 * @DateTime: 2019-08-02 00:43
 */
public class ThreadUtil {


	private static final ThreadLocal<Long> USER_ID_MAP = new ThreadLocal<>();

	private ThreadUtil() {}


	public static void set(Long value) {
		USER_ID_MAP.set(value);
	}

	public static Long get() {
		return USER_ID_MAP.get();
	}

	public static void remove() {
		USER_ID_MAP.remove();
	}

}
