package com.extlight.extensions.file.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.IoUtil;
import com.extlight.common.utils.StringUtil;
import com.extlight.extensions.file.constant.FileConfigExceptionEnum;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.constant.FileDataExceptionEnum;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.google.gson.Gson;
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
 * @ClassName: QiniuFileServiceImpl
 * @ProjectName: freedom-boot
 * @Description: 七牛云文件管理
 * @DateTime: 2019-08-03 11:14
 */
@Component
@Slf4j
public class QiniuFileServiceImpl implements FileService {

	@Autowired
	private FileConfigService fileConfigService;

	@Override
	public FileResponse upload(String fileName, byte[] data) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		try {
			Configuration cfg = new Configuration(Zone.zone2());
			UploadManager uploadManager = new UploadManager(cfg);
			Auth auth = this.createAuth();
			String upToken = auth.uploadToken(this.getBucket());

			Response response = uploadManager.put(data, fileName, upToken);
			int retry = 0;
			while(response.needRetry() && retry < 3) {
				response = uploadManager.put(data, fileName, upToken);
				retry++;
			}

			if (!response.isOK()) {
				return fileResponse;
			}

			fileResponse = new Gson().fromJson(response.bodyString(), FileResponse.class);
			fileResponse.setSuccess(true).setUrl(this.getDomain() + "/" + fileResponse.getKey());

		} catch(GlobalException e) {
			throw e;

		} catch (Exception ex) {
			log.error("========【七牛云管理】文件 fileName: {} 文件上传失败=============", fileName);
			ex.printStackTrace();
		}

		return fileResponse;
	}

	@Override
	public FileResponse download(FileDataVO fileDataVO) throws GlobalException {
		FileResponse fileResponse = new FileResponse();
		try {
			URL url = new URL("http://" + fileDataVO.getUrl());
			byte[] data = IoUtil.toByteArray(url.openStream());
			if (data == null || data.length == 0) {
				ExceptionUtil.throwEx(FileDataExceptionEnum.ERROR_FILE_DOWNLOAD);
			}
			fileResponse.setSuccess(true).setData(data);

		} catch (Exception e) {
			log.error("========【默认管理】文件 url: {} 文件下载失败=============", fileDataVO.getUrl());
			e.printStackTrace();
		}
		return fileResponse;
	}

	@Override
	public FileResponse remove(FileDataVO fileDataVO) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		try {
			Configuration cfg = new Configuration(Zone.zone2());
			Auth auth = this.createAuth();
			BucketManager bucketManager = new BucketManager(auth, cfg);

			String bucket = this.getBucket();
			Response response = bucketManager.delete(bucket, fileDataVO.getFileKey());
			int retry = 0;
			while(response.needRetry() && retry < 3) {
				response = bucketManager.delete(bucket, fileDataVO.getFileKey());
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
			log.error("========【七牛云管理】文件 fileName: {} 文件删除失败=============", fileDataVO.getName());
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
