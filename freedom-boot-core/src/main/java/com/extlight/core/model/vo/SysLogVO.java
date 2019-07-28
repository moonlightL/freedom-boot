package com.extlight.core.model.vo;

import com.extlight.common.constant.ActionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @Title: SysLogVO
 * @ProjectName freedom-boot
 * @Description: 系统日志响应对象
 * @Date 2019-07-09 13:53:07
 */
@Getter
@Setter
@ToString
public class SysLogVO implements Serializable {


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
	 * 所在地
	 */
	private String location;

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

	/**
	 * 登录次数
	 */
	private Integer loginCount;

	private String actionName;

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
		this.actionName = ActionEnum.getMessageByCode(actionType.intValue());
	}

}
