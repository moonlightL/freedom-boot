package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @Title: SysLogDTO
 * @ProjectName freedom-boot
 * @Description: 系统日志请求对象
 * @Date 2019-07-09 13:53:07
 */
@Setter
@Getter
@ToString
public class SysLogDTO extends BaseRequest {


	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 操作类型 1：增加 2：删除 3：修改 4：其他
	 */
	private Integer actionType;

	/**
	 * 方法名称
	 */
	private String methodName;

	/**
	 * 参数
	 */
	private String methodParam;

	/**
	 * 操作用户 id
	 */
	private Long userId;

	/**
	 * 操作 ip
	 */
	private String ip;

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


	private String startTime;

	private String endTime;

}
