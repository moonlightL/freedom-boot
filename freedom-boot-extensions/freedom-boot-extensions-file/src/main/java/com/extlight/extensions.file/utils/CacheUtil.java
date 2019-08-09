package com.extlight.extensions.file.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author MoonlightL
 * @Title: CacheUtil
 * @ProjectName: freedom-boot
 * @Description: 缓存工具类
 * @DateTime: 2019/8/9 14:11
 */
public class CacheUtil {

	private static final Map<String, Object> cacheMap = new ConcurrentHashMap<>();

	private CacheUtil() {}

	/**
	 * 存放
	 * @param key
	 * @param obj
	 * @param <T>
	 */
	public static <T> void put(String key, T obj) {
		cacheMap.put(key, obj);
	}

	/**
	 * 获取
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> T get(String key) {
		return (T) cacheMap.get(key);
	}

	/**
	 * 删除
	 * @param key
	 */
	public static void remove(String key) {
		cacheMap.remove(key);
	}
}
