package com.extlight.extensions.task.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.task.model.TaskJob;
import com.extlight.extensions.task.model.vo.TaskJobVO;

/**
 * @Author MoonlightL
 * @ClassName: TaskJobService
 * @ProjectName: freedom-boot
 * @Description: 任务作业 Service
 * @DateTime: 2019-08-14 17:44:56
 */
public interface TaskJobService extends BaseService<TaskJob, TaskJobVO> {

	/**
	 * 启动定时器
	 * @param taskJobId
	 * @return
	 * @throws GlobalException
	 */
	int starJob(Long taskJobId) throws GlobalException;

	/**
	 * 暂停定时器
	 * @param taskJobId
	 * @return
	 * @throws GlobalException
	 */
	int pauseJob(Long taskJobId) throws GlobalException;
}

