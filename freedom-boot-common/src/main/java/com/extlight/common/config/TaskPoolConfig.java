package com.extlight.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author MoonlightL
 * @Title: TaskPoolConfig
 * @ProjectName: freedom-boot
 * @Description: 线程池配置
 * @DateTime: 2019/8/21 10:14
 */
@Configuration
public class TaskPoolConfig {

	@Bean
	public Executor customTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数量
		executor.setCorePoolSize(10);
		// 设置最大线程数
		executor.setMaxPoolSize(15);
		// 设置缓冲队列，用来缓冲执行任务的队列
		executor.setQueueCapacity(200);
		// 设置空闲线程存活时间
		executor.setKeepAliveSeconds(60);
		// 设置线程池中线程名称前缀
		executor.setThreadNamePrefix("freedom-boot-task");
		// 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
		executor.setAwaitTerminationSeconds(60);
		// 线程池对拒绝任务的处理策略：这里采用了 CallerRunsPolicy 策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 初始化
		executor.initialize();

		return executor;
	}
}
