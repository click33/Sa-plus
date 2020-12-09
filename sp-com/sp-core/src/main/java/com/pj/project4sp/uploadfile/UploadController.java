package com.pj.project4sp.uploadfile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pj.utils.sg.AjaxJson;

/**
 * 文件上传控制器 (基于应用服务器的文件上传)
 * @author kong
 *
 */
@RestController
@RequestMapping("/upload/")
public class UploadController {

	/** 上传图片 */
	@RequestMapping("image")
	public AjaxJson image(MultipartFile file){
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端 
		UploadUtil.checkFileSize(file); 						
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.imageSuffix); 	
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.imageFolder);
		return AjaxJson.getSuccessData(httpUrl);
	}

	/** 上传视频  */
	@RequestMapping("video")
	public AjaxJson video(MultipartFile file){
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端 
		UploadUtil.checkFileSize(file); 			
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.videoSuffix); 
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.videoFolder);	
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	/** 上传音频   */
	@RequestMapping("audio")
	public AjaxJson audio(MultipartFile file){
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端 
		UploadUtil.checkFileSize(file); 	
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.audioSuffix); 	
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.audioFolder);			
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	/** 上传apk   */
	@RequestMapping("apk")
	public AjaxJson apk(MultipartFile file){
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端 
		UploadUtil.checkFileSize(file); 						
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.apkSuffix); 	
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.apkFolder);		
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	/** 上传任意文件   */
	@RequestMapping("file")
	public AjaxJson file(MultipartFile file){
		// 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端 
		UploadUtil.checkFileSize(file); 					
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.fileSuffix); 	
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.fileFolder);			
		return AjaxJson.getSuccessData(httpUrl);
	}
	
}
