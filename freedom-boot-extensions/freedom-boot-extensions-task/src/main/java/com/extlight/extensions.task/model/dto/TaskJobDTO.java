package com.extlight.extensions.task.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: TaskJobDTO
 * @ProjectName: freedom-boot
 * @Description: 任务作业请求对象
 * @DateTime: 2019-08-14 17:44:56
 */
@Setter
@Getter
@ToString
public class TaskJobDTO extends BaseRequest {


	/**
	 * 主键
	 */
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
