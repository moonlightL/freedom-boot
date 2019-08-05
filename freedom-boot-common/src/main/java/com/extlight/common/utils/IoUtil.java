package com.extlight.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: MoonlightL
 * @ClassName: IoUtil
 * @ProjectName: freedom-boot
 * @Description: 流工具
 * @DateTime: 2019-08-03 13:59
 */
public class IoUtil {

	/**
	 * 输入流转字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream inputStream) throws IOException {
		return IOUtils.toByteArray(inputStream);
	}
}
