package com.extlight.extensions.task.component;

import com.extlight.common.utils.ExceptionUtil;
import com.extlight.extensions.task.constant.TaskJobConstant;
import com.extlight.extensions.task.constant.TaskJobExceptionEnum;
import com.extlight.extensions.task.model.TaskJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author MoonlightL
 * @ClassName: ScheduleJobService
 * @ProjectName: freedom-boot
 * @Description: 任务调度 Service
 * @DateTime: 2019/8/14 18:05
 */
@Component
public class ScheduleJobService {

	@Autowired
	private Scheduler scheduler;

	/**
	 *  添加定时任务
	 * @return
	 * @param taskJob
	 */
	public boolean addTaskJob(TaskJob taskJob) {
		boolean result = false;
		try {
			// 作业明细
			JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(this.getJobKey(taskJob.getId())).build();
			jobDetail.getJobDataMap().put(TaskJobConstant.RUN_JOB, taskJob);

			// 触发器
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(this.getTriggerKey(taskJob.getId())).withSchedule(cronScheduleBuilder).build();

			// 调度器
			this.scheduler.scheduleJob(jobDetail, cronTrigger);

			// 暂停
			if (taskJob.getState() == 0) {
				result = this.pauseTaskJob(taskJob.getId());
			}

			result = true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_SCHEDULE_JOB_FAIL);
		}

		return result;
	}

	/**
	 * 运行定时任务
	 * @param taskJob
	 * @return
	 */
	public boolean runTaskJob(TaskJob taskJob) {
		boolean result = false;
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(TaskJobConstant.JOB_NAME, taskJob);

		try {
			this.scheduler.triggerJob(this.getJobKey(taskJob.getId()), dataMap);
			result = true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_TRIGGER_JOB_FAIL);
		}
		return result;
	}

	/**
	 * 重新调度定时器
	 * @param jobId
	 * @return
	 */
	public boolean rescheduleTaskJob(Long jobId) {
		boolean result = false;
		try {
			this.scheduler.rescheduleJob(this.getTriggerKey(jobId), this.getCronTrigger(jobId));
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_RESCHEDULE_JOB_FAIL);
		}
		return result;
	}

	/**
	 * 暂定定时器
	 * @param jobId
	 * @return
	 */
	public boolean pauseTaskJob(Long jobId) {
		boolean result = false;
		try {
			this.scheduler.pauseJob(this.getJobKey(jobId));
			result = true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_PAUSE_JOB_FAIL);
		}
		return result;
	}

	/**
	 * 恢复定时器
	 * @param jobId
	 * @return
	 */
	public boolean resumeTaskJob(Long jobId) {
		boolean result = false;
		try {
			this.scheduler.resumeJob(this.getJobKey(jobId));
			result = true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_RESUME_JOB_FAIL);
		}
		return result;
	}

	/**
	 * 刪除定时器
	 * @param jobId
	 * @return
	 */
	public boolean removeTaskJob(Long jobId) {
		boolean result = false;
		try {
			TriggerKey triggerKey = this.getTriggerKey(jobId);
			// 停止触发器
			this.scheduler.pauseTrigger(triggerKey);
			// 移除触发器
			this.scheduler.unscheduleJob(triggerKey);
			// 删除任务
			this.scheduler.deleteJob(this.getJobKey(jobId));
			result = true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_DELETE_JOB_FAIL);
		}
		return result;
	}

	/**
	 * 获取jobKey
	 */
	public JobKey getJobKey(Long jobId) {
		return JobKey.jobKey(TaskJobConstant.JOB_NAME + jobId);
	}

	/**
	 * 获取触发器key
	 */
	public TriggerKey getTriggerKey(Long jobId) {
		return TriggerKey.triggerKey(TaskJobConstant.TRIGGER_NAME + jobId);
	}


	/**
	 * 获取表达式触发器
	 */
	public CronTrigger getCronTrigger(Long jobId) throws SchedulerException {
		return (CronTrigger) scheduler.getTrigger(this.getTriggerKey(jobId));
	}

}
