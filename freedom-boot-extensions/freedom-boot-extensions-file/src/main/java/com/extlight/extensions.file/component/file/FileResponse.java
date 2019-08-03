package com.extlight.extensions.file.component.file;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: moonlight
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

	private String key;

	private String hash;

	private String url;

	private byte[] data;
}
