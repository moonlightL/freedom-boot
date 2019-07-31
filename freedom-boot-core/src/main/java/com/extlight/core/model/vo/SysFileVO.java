package com.extlight.core.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: SysFileVO
 * @ProjectName: freedom-boot
 * @Description: 文件响应对象
 * @DateTime: 2019-07-31 17:42:54
 */
@Getter
@Setter
@ToString
public class SysFileVO implements Serializable {


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
