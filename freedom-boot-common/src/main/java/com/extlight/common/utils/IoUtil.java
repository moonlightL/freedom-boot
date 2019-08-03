package com.extlight.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: moonlight
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
	public static byte[] read(InputStream inputStream) throws IOException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int num = inputStream.read(buffer);
			while (num != -1) {
				baos.write(buffer, 0, num);
				num = inputStream.read(buffer);
			}
			baos.flush();
			return baos.toByteArray();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
