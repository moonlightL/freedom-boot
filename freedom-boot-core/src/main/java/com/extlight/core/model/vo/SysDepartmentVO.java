package com.extlight.core.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysDepartmentVO
 * @ProjectName: freedom-boot
 * @Description: 部门表响应对象
 * @DateTime: 2019-10-14 13:33:04
 */
@Getter
@Setter
@ToString
public class SysDepartmentVO implements Serializable {

	private static final long serialVersionUID =1L;


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
