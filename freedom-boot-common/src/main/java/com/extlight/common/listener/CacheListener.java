package com.extlight.common.listener;

import com.extlight.common.utils.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author MoonlightL
 * @Title: CacheListener
 * @ProjectName: freedom-boot
 * @Description: 缓存监听器/任务
 * @DateTime: 2019/8/20 18:09
 */
@Component
@Slf4j
public class CacheListener {

	/**
	 *  清除 CacheUtil 中过期的 key
	 */
	@Scheduled(fixedRate = 5000)
	public void clearCache() {

		Set<String> keys = CacheUtil.keySet();
		if (!keys.isEmpty()) {
			keys.stream().filter(i -> CacheUtil.isExpire(i))
					.forEach(i -> CacheUtil.remove(i));
		}

	}
}
