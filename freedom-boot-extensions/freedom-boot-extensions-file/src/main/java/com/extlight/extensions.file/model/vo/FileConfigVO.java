package com.extlight.extensions.file.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: FileConfigVO
 * @ProjectName: freedom-boot
 * @Description: 文件配置响应对象
 * @DateTime: 2019-08-02 00:04:21
 */
@Getter
@Setter
@ToString
public class FileConfigVO implements Serializable {


	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 配置名称
	 */
	private String configName;

	/**
	 * 配置值
	 */
	private String configValue;

	/**
	 * 类型 1：本地 2：七牛 3：oss
	 */
	private Integer configType;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

}
