package com.extlight.extensions.task.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: TaskJobVO
 * @ProjectName: freedom-boot
 * @Description: 任务作业响应对象
 * @DateTime: 2019-08-14 17:44:56
 */
@Getter
@Setter
@ToString
public class TaskJobVO implements Serializable {

	private static final long serialVersionUID =1L;


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
	 * 状态 0：关闭 1：开启 2：暂停
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
