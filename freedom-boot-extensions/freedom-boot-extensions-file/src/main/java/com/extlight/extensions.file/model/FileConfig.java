package com.extlight.extensions.file.model;

import com.extlight.common.base.BaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: FileConfig
 * @ProjectName: freedom-boot
 * @Description: 文件配置
 * @DateTime: 2019-08-02 00:04:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_file_config")
public class FileConfig extends BaseResponse {


	/**
	 * 主键
	 */
	@Id
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
