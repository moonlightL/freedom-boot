package com.extlight.extensions.task.component;

import com.extlight.common.utils.SpringContext;
import com.extlight.extensions.task.constant.TaskJobConstant;
import com.extlight.extensions.task.model.TaskJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

/**
 * @Author MoonlightL
 * @ClassName: ScheduleJob
 * @ProjectName: freedom-boot
 * @Description: 定时任务
 * @DateTime: 2019/8/14 17:52
 */
@Slf4j
public class ScheduleJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		// 获取参数
		JobDetail jobDetail = context.getJobDetail();
		TaskJob taskJob = (TaskJob) jobDetail.getJobDataMap().get(TaskJobConstant.RUN_JOB);

		try {
			// 获取执行类
			Object bean = SpringContext.getBean(taskJob.getBeanName());
			Method method;

			// 获取执行方法
			if (taskJob.getParams() == null || taskJob.getParams().length() == 0) {
				method = bean.getClass().getDeclaredMethod(taskJob.getMethodName());
			} else {
				method = bean.getClass().getDeclaredMethod(taskJob.getMethodName(), String.class);
			}

			method.setAccessible(true);

			// 执行任务
			if (taskJob.getParams() == null || taskJob.getParams().length() == 0) {
				method.invoke(bean);
			} else {
				method.invoke(bean, taskJob.getParams());
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("===========定时任务执行失败: {}============", e.getMessage());
		}
	}
}
