package com.extlight.common.utils;

/**
 * @Author: moonlight
 * @ClassName: ThreadUtil
 * @ProjectName: freedom-boot
 * @Description: 线程工具类
 * @DateTime: 2019-08-02 00:43
 */
public class ThreadUtil {


	private static final ThreadLocal<Long> userIdMap = new ThreadLocal<>();

	private ThreadUtil() {}


	public static void set(Long value) {
		userIdMap.set(value);
	}

	public static Long get() {
		return userIdMap.get();
	}

	public static void remove() {
		userIdMap.remove();
	}

}
