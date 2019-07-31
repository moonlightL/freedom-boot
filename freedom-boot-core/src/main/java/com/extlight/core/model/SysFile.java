package com.extlight.core.model;

import com.extlight.common.base.BaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysFile
 * @ProjectName: freedom-boot
 * @Description: 文件
 * @DateTime: 2019-07-31 17:42:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_sys_file")
public class SysFile extends BaseResponse {


	/**
	 * 主键
	 */
		@Id
		private Long id;

	/**
	 * 文件名称
	 */
		private String name;

	/**
	 * 文件路径
	 */
		private String url;

	/**
	 * 缩略图路径
	 */
		private String thumbnailUrl;

	/**
	 * 管理类型 1：默认 2：七牛 3：oss
	 */
		private Integer code;

	/**
	 * 内容类型
	 */
		private String contentType;

	/**
	 * 操作者 id
	 */
		private Long operatorId;

	/**
	 * 创建时间
	 */
		private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
		private LocalDateTime updateTime;

}
