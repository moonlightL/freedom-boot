package com.extlight.extensions.file.model;

import com.extlight.common.base.BaseResponse;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * @Author MoonlightL
 * @ClassName: FileData
 * @ProjectName: freedom-boot
 * @Description: 文件数据
 * @DateTime: 2019-08-02 00:04:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "t_file_data")
public class FileData extends BaseResponse {


	/**
	 * 主键
	 */
	@Id
	private Long id;

	/**
	 * 文件名称
	 */
	private String filename;

	/**
	 * 文件原始名称
	 */
	private String originalFilename;

	/**
	 * 文件路径
	 */
	private String url;

	/**
	 * 缩略图路径
	 */
	private String thumbnailUrl;

	/**
	 * 文件 key (第三方返回)
	 */
	private String fileKey;

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
