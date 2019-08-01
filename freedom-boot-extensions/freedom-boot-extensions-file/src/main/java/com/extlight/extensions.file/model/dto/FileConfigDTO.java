package com.extlight.extensions.file.model.dto;

import com.extlight.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: FileConfigDTO
 * @ProjectName: freedom-boot
 * @Description: 文件配置请求对象
 * @DateTime: 2019-08-02 00:04:21
 */
@Setter
@Getter
@ToString
public class FileConfigDTO extends BaseRequest {


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
	 * 类型 1：默认 2：七牛 3：oss
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
