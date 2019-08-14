package com.extlight.extensions.task.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.extensions.task.model.TaskJob;

/**
 * @Author MoonlightL
 * @ClassName: TaskJobMapper
 * @ProjectName: freedom-boot
 * @Description: 任务作业 Mapper
 * @DateTime: 2019-08-14 17:44:56
 */
public interface TaskJobMapper extends BaseMapper<TaskJob> {

	/**
	 * 通过名称查询任务作业
	 * @param jobName
	 * @return
	 */
	TaskJob findByJobName(String jobName);
}
