package com.extlight.core.model;

import com.extlight.common.base.BaseResponse;
import com.extlight.common.component.mybatis.CreateTime;
import com.extlight.common.component.mybatis.UpdateTime;
import com.extlight.core.model.vo.SysLogVO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysLog
 * @ProjectName freedom-boot
 * @Description: 系统日志
 * @Date 2019-07-09 13:53:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_sys_log")
public class SysLog extends BaseResponse<SysLogVO> {

	/**
	 * 主键
	 */
	@Id
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
	@CreateTime
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@UpdateTime
	private LocalDateTime updateTime;
}
