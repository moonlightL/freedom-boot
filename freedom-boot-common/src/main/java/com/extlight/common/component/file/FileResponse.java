package com.extlight.common.component.file;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: MoonlightL
 * @ClassName: FileResponse
 * @ProjectName: freedom-boot
 * @Description: 文件返回值
 * @DateTime: 2019-08-03 12:13
 */
@Getter
@Setter
@Accessors(chain = true)
public class FileResponse {

	private boolean isSuccess;

	/**
	 * 七牛云返回的 key
	 */
	private String key;

	/**
	 * 七牛云返回的 hash
	 */
	private String hash;

	/**
	 * 文件路径
	 */
	private String url;

	/**
	 * 文件数据字节数组
	 */
	private byte[] data;
}
