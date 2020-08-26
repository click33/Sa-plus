package com.pj.project4sp.uploadfile;

import javax.servlet.http.HttpServletRequest;

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
	public AjaxJson image(MultipartFile file, HttpServletRequest request){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.image_suffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.image_folder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}

	
	// 上传视频 
	@RequestMapping("video")
	public AjaxJson video(MultipartFile file, HttpServletRequest request){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.video_suffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.video_folder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	

	// 上传音频 
	@RequestMapping("audio")
	public AjaxJson audio(MultipartFile file, HttpServletRequest request){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.audio_suffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.audio_folder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	
	// 上传apk 
	@RequestMapping("apk")
	public AjaxJson apk(MultipartFile file, HttpServletRequest request){
		UploadUtil.checkFileSize(file); 						// 验证文件大小 
		UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.apk_suffix); 	// 验证后缀
		String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.apk_folder);			// 保存到硬盘 
		return AjaxJson.getSuccessData(httpUrl);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
