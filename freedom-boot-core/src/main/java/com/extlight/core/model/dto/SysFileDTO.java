package com.extlight.core.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysFileDTO
 * @ProjectName: freedom-boot
 * @Description: 文件请求对象
 * @DateTime: 2019-07-31 17:42:54
 */
@Setter
@Getter
@ToString
public class SysFileDTO extends BaseRequest {


	/**
	 * 主键
	 */
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
