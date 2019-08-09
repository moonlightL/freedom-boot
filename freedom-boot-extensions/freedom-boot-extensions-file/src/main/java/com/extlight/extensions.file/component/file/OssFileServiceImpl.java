package com.extlight.extensions.file.component.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.IoUtil;
import com.extlight.common.utils.StringUtil;
import com.extlight.extensions.file.constant.FileConfigExceptionEnum;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * @Author: MoonlightL
 * @ClassName: OssFileServiceImpl
 * @ProjectName: freedom-boot
 * @Description: OSS 文件管理，参考：https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.834.4ae66328XPkGZq
 * @DateTime: 2019-08-03 23:10
 */
@Component
@Slf4j
public class OssFileServiceImpl implements FileService {

	@Autowired
	private FileConfigService fileConfigService;

	@Override
	public FileResponse upload(String fileName, byte[] data) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		OSS ossClient = null;

		try {
			ossClient = this.buildOssClient();
			ossClient.putObject(this.getBucket(), fileName, new ByteArrayInputStream(data));

			fileResponse.setSuccess(true).setUrl(this.getBucket() + "." + this.getEndpoint() + "/" + fileName);

		} catch (GlobalException e) {
			throw e;

		} catch (Exception e) {
			log.error("========【OSS 管理】文件 fileName: {} 文件上传失败=============", fileName);
			e.printStackTrace();
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}

		return fileResponse;
	}

	@Override
	public FileResponse download(FileDataVO fileDataVO) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		OSS ossClient = null;

		try {
			ossClient = this.buildOssClient();
			OSSObject ossObject = ossClient.getObject(this.getBucket(), fileDataVO.getName());
			byte[] data = IoUtil.toByteArray(ossObject.getObjectContent());
			if (data == null || data.length == 0) {
				ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_DOWNLOAD);
			}

			fileResponse.setSuccess(true).setData(data);

		} catch (Exception e) {
			log.error("========【OSS 管理】文件 fileName: {} 文件下载失败=============", fileDataVO.getName());
			e.printStackTrace();
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}

		return fileResponse;
	}

	@Override
	public FileResponse remove(FileDataVO fileDataVO) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		OSS ossClient = null;
		try {
			ossClient = this.buildOssClient();
			ossClient.deleteObject(this.getBucket(), fileDataVO.getName());

			fileResponse.setSuccess(true);

		} catch (GlobalException e) {
			throw e;

		} catch (Exception e) {
			log.error("========【OSS 管理】文件 fileName: {} 文件删除失败=============", fileDataVO.getName());
			e.printStackTrace();
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}

		return fileResponse;
	}

	@Override
	public int getCode() {
		return FileManageEnum.OSS.getCode();
	}

	/**
	 * 构建 oss 客户端
	 * @return
	 * @throws GlobalException
	 */
	private OSS buildOssClient() throws GlobalException {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
		// Region 请按实际情况填写
		String endpoint = fileConfigMap.get(FileConstant.OSS_ENDPOINT);
		// 建议使用RAM账号进行API访问或日常运维，访问 https://ram.console.aliyun.com 创建RAM账号
		String accessKeyId = fileConfigMap.get(FileConstant.OSS_ACCESS_KEY);
		String accessKeySecret = fileConfigMap.get(FileConstant.OSS_SECRET_KEY);

		if (StringUtil.isBlank(endpoint)
			|| StringUtil.isBlank(accessKeyId)
			|| StringUtil.isBlank(accessKeySecret)) {
			ExceptionUtil.throwEx(FileConfigExceptionEnum.ERROR_OSS_CONFIG_IS_EMPTY);
		}

		// 创建OSSClient实例
		OSS ossClient = new OSSClientBuilder().build("http://" + endpoint, accessKeyId, accessKeySecret);

		return ossClient;
	}

	/**
	 * 获取 endpoint
	 * @return
	 * @throws GlobalException
	 */
	private String getEndpoint() throws GlobalException {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
		String endpoint = fileConfigMap.get(FileConstant.OSS_ENDPOINT);
		if (StringUtil.isBlank(endpoint)) {
			ExceptionUtil.throwEx(FileConfigExceptionEnum.ERROR_OSS_CONFIG_IS_EMPTY);
		}

		return endpoint;
	}

	/**
	 * 获取 bucket
	 * @return
	 */
	private String getBucket() throws GlobalException {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
		String bucket = fileConfigMap.get(FileConstant.OSS_BUCKET);
		if (StringUtil.isBlank(bucket)) {
			ExceptionUtil.throwEx(FileConfigExceptionEnum.ERROR_OSS_CONFIG_IS_EMPTY);
		}

		return bucket;
	}
}
