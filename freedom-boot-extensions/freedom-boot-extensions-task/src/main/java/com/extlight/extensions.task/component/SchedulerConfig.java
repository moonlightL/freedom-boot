package com.extlight.extensions.task.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author MoonlightL
 * @ClassName: SchedulerConfig
 * @ProjectName: freedom-boot
 * @Description: 调度器配置
 * @DateTime: 2019/8/14 18:02
 */
@Configuration
public class SchedulerConfig {

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		// quartz 参数
		Properties prop = new Properties();
		prop.put("org.quartz.scheduler.instanceName", "FreedomBootScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");

		// 线程池配置
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", "20");
		prop.put("org.quartz.threadPool.threadPriority", "5");

		// JobStore 配置
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");

		// 集群配置
		prop.put("org.quartz.jobStore.isClustered", "false");
		prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
		prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

		prop.put("org.quartz.jobStore.misfireThreshold", "12000");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");

		factory.setQuartzProperties(prop);

		factory.setSchedulerName("FreedomBootScheduler");

		//延时启动
		factory.setStartupDelay(10);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");

		factory.setOverwriteExistingJobs(true);

		//设置自动启动，默认为true
		factory.setAutoStartup(true);

		return factory;
	}
}
