package com.pj.project4sp.uploadfile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.SoMap;

/**
 * 文件上传控制器 (基于应用服务器的文件上传)
 * @author kong
 *
 */
@RestController
@RequestMapping("/upload/")
public class UploadController {

	
	// 测试
	@RequestMapping("test")
	public AjaxJson test(){
		System.out.println(SoMap.getRequestSoMap());
		return AjaxJson.getSuccess();
	}
	
	
	// 上传图片 
	@RequestMapping("image")
	public AjaxJson image(MultipartFile file){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.imageSuffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.imageFolder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}

	
	// 上传视频 
	@RequestMapping("video")
	public AjaxJson video(MultipartFile file){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.videoSuffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.videoFolder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	

	// 上传音频 
	@RequestMapping("audio")
	public AjaxJson audio(MultipartFile file){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.audioSuffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.audioFolder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	
	// 上传apk 
	@RequestMapping("apk")
	public AjaxJson apk(MultipartFile file){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.apkSuffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.apkFolder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	

	// 上传任意文件 
	@RequestMapping("file")
	public AjaxJson file(MultipartFile file){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.fileSuffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.fileFolder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
