package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import com.extlight.core.model.SysDepartment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysDepartmentDTO
 * @ProjectName: freedom-boot
 * @Description: 部门表请求对象
 * @DateTime: 2019-10-14 13:33:04
 */
@Setter
@Getter
@ToString
public class SysDepartmentDTO extends BaseRequest<SysDepartment> {


	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 父级 id
	 */
	private Long pid;

	/**
	 * 状态 1：可用 0：禁用
	 */
	private Integer state;

	/**
	 * 描述
	 */
	private String descr;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

}
