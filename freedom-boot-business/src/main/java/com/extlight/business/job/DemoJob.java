package com.extlight.business.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @ClassName: DemoJob
 * @ProjectName: freedom-boot
 * @Description: 测试任务
 * @DateTime: 2019/8/14 20:24
 */
@Component
@Slf4j
public class DemoJob {

	public void test() {
		log.info("===========hello freedom boot===========");
	}
}
