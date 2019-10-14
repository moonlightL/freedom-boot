package com.extlight.core.model;

import com.extlight.common.base.BaseResponse;
import com.extlight.common.component.mybatis.CreateTime;
import com.extlight.common.component.mybatis.UpdateTime;
import com.extlight.core.model.vo.SysDepartmentVO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysDepartment
 * @ProjectName: freedom-boot
 * @Description: 部门表
 * @DateTime: 2019-10-14 13:33:04
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_sys_department")
public class SysDepartment extends BaseResponse<SysDepartmentVO> {

	private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
		@Id
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
		@CreateTime
		private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
		@UpdateTime
		private LocalDateTime updateTime;

}
