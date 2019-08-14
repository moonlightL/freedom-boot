package com.extlight.extensions.task.model;

import com.extlight.common.base.BaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: TaskJob
 * @ProjectName: freedom-boot
 * @Description: 任务作业
 * @DateTime: 2019-08-14 17:44:56
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_task_job")
public class TaskJob extends BaseResponse {


	/**
	 * 主键
	 */
	@Id
	private Long id;

	/**
	 * 任务名称
	 */
	private String jobName;

	/**
	 * 任务组
	 */
	private String jobGroupName;

	/**
	 * bean 名称
	 */
	private String beanName;

	/**
	 * 方法名
	 */
	private String methodName;

	/**
	 * 参数
	 */
	private String params;

	/**
	 * cron 表达式
	 */
	private String cronExpression;

	/**
	 * 状态 0：关闭 1：开启
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

}
