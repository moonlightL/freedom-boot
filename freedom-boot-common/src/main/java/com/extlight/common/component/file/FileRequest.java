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

	private String uploadDir;

	private String fileName;

	private byte[] data;

	private InputStream inputStream;

	private String url;

	private String fileKey;
}
