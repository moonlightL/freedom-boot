package com.extlight.extensions.file.component;

import com.extlight.common.component.file.FileManageEnum;
import com.extlight.common.component.file.FileRequest;
import com.extlight.common.component.file.FileResponse;
import com.extlight.common.component.file.FileService;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.IoUtil;
import com.extlight.common.utils.JsonUtil;
import com.extlight.common.utils.StringUtil;
import com.extlight.extensions.file.constant.FileConfigExceptionEnum;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.service.FileConfigService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;

/**
 * @Author: MoonlightL
 * @ClassName: QiniuFileService
 * @ProjectName: freedom-boot
 * @Description: 七牛云文件管理，参考：https://developer.qiniu.com/kodo/sdk/1239/java#5
 * @DateTime: 2019-08-03 11:14
 */
@Component
@Slf4j
public class QiniuFileService implements FileService {

	private static final Integer RETRY_NUM = 3;

	@Autowired
	private FileConfigService fileConfigService;

	@Override
	public FileResponse upload(FileRequest fileRequest) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		byte[] data = fileRequest.getData();
		String fileName = fileRequest.getFilename();

		try {
			// Zone.zone2() 根据自己情况选择
			Configuration cfg = new Configuration(Zone.zone2());
			UploadManager uploadManager = new UploadManager(cfg);
			Auth auth = this.createAuth();
			String upToken = auth.uploadToken(this.getBucket());

			Response response = uploadManager.put(data, fileName, upToken);
			int retry = 0;
			while(response.needRetry() && retry < RETRY_NUM) {
				response = uploadManager.put(data, fileName, upToken);
				retry++;
			}

			if (!response.isOK()) {
				return fileResponse;
			}

			fileResponse = JsonUtil.parseObj(response.bodyString(), FileResponse.class);
			fileResponse.setSuccess(true).setUrl(this.parseUrl(this.getDomain() + "/" + fileResponse.getKey()));

		} catch(GlobalException e) {
			throw e;

		} catch (Exception ex) {
			log.error("========【七牛云管理】文件 fileName: {} 文件上传失败=============", fileName);
			ex.printStackTrace();
		}

		return fileResponse;
	}

	@Override
	public FileResponse download(FileRequest fileRequest) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		String urlStr = fileRequest.getUrl();

		try {
			URL url = new URL(urlStr);
			byte[] data = IoUtil.toByteArray(url.openStream());
			if (data == null || data.length == 0) {
				ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_DOWNLOAD);
			}
			fileResponse.setSuccess(true).setData(data);

		} catch (Exception e) {
			log.error("========【默认管理】文件 url: {} 文件下载失败=============", urlStr);
			e.printStackTrace();
		}
		return fileResponse;
	}

	@Override
	public FileResponse remove(FileRequest fileRequest) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		String fileKey = fileRequest.getFileKey();

		try {
			Configuration cfg = new Configuration(Zone.zone2());
			Auth auth = this.createAuth();
			BucketManager bucketManager = new BucketManager(auth, cfg);

			String bucket = this.getBucket();
			Response response = bucketManager.delete(bucket, fileKey);
			int retry = 0;
			while(response.needRetry() && retry < RETRY_NUM) {
				response = bucketManager.delete(bucket, fileKey);
				retry++;
			}

			if (!response.isOK()) {
				return fileResponse;
			}

			fileResponse.setSuccess(true);
			return fileResponse;

		} catch (GlobalException e) {
			throw e;

		} catch (Exception ex) {
			log.error("========【七牛云管理】文件 fileName: {} 文件删除失败=============", fileRequest.getFilename());
			ex.printStackTrace();
		}

		return fileResponse;
	}

	@Override
	public int getCode() {
		return FileManageEnum.QI_NIU.getCode();
	}


	/**
	 * 创建认证
	 * @return
	 */
	private Auth createAuth() throws GlobalException {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();

		String accessKey = fileConfigMap.get(FileConstant.QN_ACCESS_KEY);
		String secretKey = fileConfigMap.get(FileConstant.QN_SECRET_KEY);

		if (StringUtil.isBlank(accessKey) || StringUtil.isBlank(secretKey)) {
			ExceptionUtil.throwEx(FileConfigExceptionEnum.ERROR_QN_CONFIG_IS_EMPTY);
		}

		Auth auth = Auth.create(accessKey, secretKey);

		return auth;
	}

	/**
	 * 获取 bucket
	 * @return
	 */
	private String getBucket() throws GlobalException {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();

		String bucket = fileConfigMap.get(FileConstant.QN_BUCKET);
		if (StringUtil.isBlank(bucket)) {
			ExceptionUtil.throwEx(FileConfigExceptionEnum.ERROR_QN_CONFIG_IS_EMPTY);
		}

		return bucket;
	}

	/**
	 * 获取域名
	 * @return
	 */
	private String getDomain() throws GlobalException {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();

		String domain = fileConfigMap.get(FileConstant.QN_DOMAIN);
		if (StringUtil.isBlank(domain)) {
			ExceptionUtil.throwEx(FileConfigExceptionEnum.ERROR_QN_CONFIG_IS_EMPTY);
		}

		return domain;
	}

}
