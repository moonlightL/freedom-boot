package com.extlight.extensions.task.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.extensions.task.component.ScheduleJobService;
import com.extlight.extensions.task.constant.TaskJobExceptionEnum;
import com.extlight.extensions.task.mapper.TaskJobMapper;
import com.extlight.extensions.task.model.TaskJob;
import com.extlight.extensions.task.model.dto.TaskJobDTO;
import com.extlight.extensions.task.model.vo.TaskJobVO;
import com.extlight.extensions.task.service.TaskJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author MoonlightL
 * @ClassName: TaskJobServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 任务作业 ServiceImpl
 * @DateTime: 2019-08-14 17:44:56
 */
@Service
public class TaskJobServiceImpl extends BaseServiceImpl<TaskJob, TaskJobVO> implements TaskJobService {

    @Autowired
    private TaskJobMapper taskJobMapper;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    public BaseMapper<TaskJob> getBaseMapper() {
        return this.taskJobMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(TaskJob.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            TaskJobDTO taskJobDTO = (TaskJobDTO) params;
            // TODO 填充查询条件
        }

        return example;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int save(TaskJob taskJob) throws GlobalException {

        TaskJob dbJob = this.taskJobMapper.findByJobName(taskJob.getJobName());
        if (dbJob != null) {
            ExceptionUtil.throwEx(TaskJobExceptionEnum.ERROR_REPEAT_JOB_NAME);
        }

        int num = super.save(taskJob);
        if (num > 0) {
            this.scheduleJobService.addTaskJob(taskJob);
        }

        return num;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int remove(Long id) throws GlobalException {

        int num = super.remove(id);
        if (num > 0) {
            this.scheduleJobService.removeTaskJob(id);
        }

        return num;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int update(TaskJob taskJob) throws GlobalException {

        int num = super.update(taskJob);
        if (num > 0) {
            if (taskJob.getState() == 1) {
                this.scheduleJobService.resumeTaskJob(taskJob.getId());
            } else {
                this.scheduleJobService.pauseTaskJob(taskJob.getId());
            }
        }

        return num;
    }
}