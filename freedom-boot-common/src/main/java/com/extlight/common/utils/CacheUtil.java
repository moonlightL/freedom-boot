package com.extlight.common.utils;

import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author MoonlightL
 * @ClassName: CacheUtil
 * @ProjectName: freedom-boot
 * @Description: 缓存工具类
 * @DateTime: 2019/8/9 14:11
 */
public class CacheUtil {

	private static final Map<String, Cache> CACHE_MAP = new ConcurrentHashMap<>();

	private CacheUtil() {}

	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @param <T>
	 */
	public static <T> void put(String key, T value) {
		CACHE_MAP.put(key, new Cache(value));
	}

	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @param duration  时长，单位：毫秒
	 * @param <T>
	 */
	public static <T> void put(String key, T value, long duration) {
		CACHE_MAP.put(key, new Cache(value, duration));
	}

	/**
	 * 获取数据
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> T get(String key) {
		Cache<T> cache = CACHE_MAP.get(key);
		if (cache == null) {
			return null;
		}

		if (!cache.hasTime) {
			return cache.getData();
		}

		if (System.currentTimeMillis() < cache.getTimeout()) {
			return cache.getData();
		}

		// 过期清除
		CACHE_MAP.remove(key);
		return null;
	}

	/**
	 * 删除
	 * @param key
	 */
	public static void remove(String key) {
		CACHE_MAP.remove(key);
	}

	/**
	 *  key 集合
	 * @return
	 */
	public static Set<String> keySet() {
		return CACHE_MAP.keySet();
	}

	@Getter
	static class Cache<T> {

		/**
		 * 过期时间，单位：毫秒
		 */
		private long timeout;

		/**
		 * 是否有时间
		 */
		private boolean hasTime;

		/**
		 * 数据
		 */
		private T data;

		private Cache(T data) {
			this.data = data;
			this.hasTime = (this.timeout > 0);
		}

		private Cache(T data, long duration) {
			this.data = data;
			this.timeout = System.currentTimeMillis() + duration;
			this.hasTime = (this.timeout > 0);
		}
	}

}
