package com.extlight.common.component.file;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * @Author: moonlight
 * @ClassName: FileRequest
 * @ProjectName: freedom-boot
 * @Description: 文件请求
 * @DateTime: 2019-08-10 20:24
 */
@Getter
@Setter
@Accessors(chain = true)
public class FileRequest {

	/**
	 * 上传目录
	 */
	private String uploadDir;

	/**
	 * 文件名称
	 */
	private String filename;

	/**
	 * 文件原始名称
	 */
	private String originalFilename;

	/**
	 * 文件数据字节数组
	 */
	private byte[] data;

	/**
	 * 文件流
	 */
	private InputStream inputStream;

	/**
	 * 文件路径
	 */
	private String url;

	/**
	 * 七牛云返回的 key
	 */
	private String fileKey;
}
