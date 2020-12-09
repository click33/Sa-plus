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
@ConfigurationProperties(prefix = "spring.upload-config")
public class UploadConfig {

	public String rootFolder = "/upload-file";			// 文件保存的根目录，所有文件都保存在这个目录下  
	public String httpPrefix = "/upload";						// http路由前缀，用于向前台暴露文件url  
	
	public String imageFolder = "/image";						// 图片保存文件夹 
	public String videoFolder = "/video";						// 视频保存文件夹 
	public String audioFolder = "/audio";						// 音频保存地址 
	public String apkFolder = "/apk";							// apk保存地址 
	public String fileFolder = "/file";							// file保存地址 (任意类型文件)
	
	public String imageSuffix = "jpg,jpeg,png,gif,ico,bmp,tiff,raw";			// 图片允许的后缀 
	public String videoSuffix = "mp4,avi,rmvb,mov,flv";						// 视频允许的后缀 
	public String audioSuffix = "mp3,aac,wav,wma,cda,flac,m4a,mid,mka,mp2,mpa,mpc,ape,ofr,ogg,ra,wv,tta,ac3,dts";			// 音频允许的后缀 
	public String apkSuffix = "apk";						// apk允许的后缀  
	public String fileSuffix = "jpg";						// file允许的后缀 (为防止上传恶意文件，这里必须手动指定可上传的类型) 
	
	public long maxSize = 1024 * 1024 * 1024 ;	// 文件最大大小,单位/B , 此为1G 
	
}
