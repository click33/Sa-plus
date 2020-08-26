package com.pj.project4sp.uploadfile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 文件上传配置类  (基于应用服务器的文件上传)
 * @author kong
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.upload-config")	// 配置前缀 
public class UploadConfig {

	
	
	public String root_folder = "/upload-file";			// 文件保存的根目录，所有文件都保存在这个目录下  
	public String http_prefix = "/upload";						// http路由前缀，用于向前台暴露文件url  
	
	public String image_folder = "/image";						// 图片保存文件夹 
	public String video_folder = "/video";						// 视频保存文件夹 
	public String audio_folder = "/audio";						// 音频保存地址 
	public String apk_folder = "/apk";							// apk保存地址 
	
	public String image_suffix = "jpg,jpeg,png,gif,ico,bmp,tiff,raw";			// 图片允许的后缀 
	public String video_suffix = "mp4,avi,rmvb,mov,flv";						// 视频允许的后缀 
	public String audio_suffix = "mp3,aac,wav,wma,cda,flac,m4a,mid,mka,mp2,mpa,mpc,ape,ofr,ogg,ra,wv,tta,ac3,dts";			// 音频允许的后缀 
	public String apk_suffix = "apk";					// apk允许的后缀  
	
	public long max_size = 1024 * 1024 * 1024 ;	// 文件最大大小,单位/B , 此为1G 
	
	
	
	

}
