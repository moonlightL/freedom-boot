package com.extlight.extensions.file.component.file;

import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.IoUtil;
import com.extlight.extensions.file.constant.FileConstant;
import com.extlight.extensions.file.model.vo.FileDataVO;
import com.extlight.extensions.file.service.FileConfigService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
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
 * @Author: moonlight
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
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = this.getAuth();
		String upToken = auth.uploadToken(this.getBucket());

		try {
			Response response = uploadManager.put(data, fileName, upToken);
			int retry = 0;
			while(response.needRetry() && retry < 3) {
				response = uploadManager.put(data, fileName, upToken);
				retry++;
			}

			if (!response.isOK()) {
				return null;
			}

			fileResponse = new Gson().fromJson(response.bodyString(), FileResponse.class);
			fileResponse.setSuccess(true).setUrl(this.getDomain() + "/" + fileResponse.getKey());

		} catch (QiniuException ex) {
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
			fileResponse.setSuccess(true).setData(IoUtil.read(url.openStream()));

		} catch (Exception e) {
			log.error("========【默认管理】文件 url: {} 文件下载失败=============", fileDataVO.getUrl());
			e.printStackTrace();
		}
		return fileResponse;
	}

	@Override
	public FileResponse remove(FileDataVO fileDataVO) throws GlobalException {
		FileResponse fileResponse = new FileResponse();

		Configuration cfg = new Configuration(Zone.zone2());
		Auth auth = this.getAuth();
		BucketManager bucketManager = new BucketManager(auth, cfg);

		try {
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

		} catch (QiniuException ex) {
			log.error("========【七牛云管理】文件 fileName: {} 文件删除失败=============", fileDataVO.getName());
			ex.printStackTrace();
		}

		return fileResponse;
	}

	@Override
	public int getCode() {
		return ModeEnum.QI_NIU.getCode();
	}



	private Auth getAuth() {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();

		String accessKey = fileConfigMap.get(FileConstant.QN_ACCESS_KEY);
		String secretKey = fileConfigMap.get(FileConstant.QN_SECRET_KEY);
		Auth auth = Auth.create(accessKey, secretKey);

		return auth;
	}

	private String getBucket() {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
		return fileConfigMap.get(FileConstant.QN_BUCKET);
	}


	private String getDomain() {
		Map<String, String> fileConfigMap = this.fileConfigService.getFileConfigMap();
		return fileConfigMap.get(FileConstant.QN_DOMAIN);
	}

}
