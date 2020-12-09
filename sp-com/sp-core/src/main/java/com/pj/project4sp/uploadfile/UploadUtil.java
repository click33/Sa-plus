package com.pj.project4sp.uploadfile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.pj.current.config.SystemObject;

/**
 * 文件上传工具类(基于应用服务器的文件上传)
 * @author kong
 *
 */
@Component
public class UploadUtil {

	/** 注入配置  */
	public static UploadConfig uploadConfig;
	@Autowired
	public void setUploadConfig(UploadConfig uploadConfig) {
		UploadUtil.uploadConfig = uploadConfig;
	}
	
	/** 将文件名保存在服务器硬盘上，并把文件对应的http地址返回给前台  */
	public static String saveFile(MultipartFile file, String flieTypeFolder) {
		
		// 1、计算路径  
		// 根据日期计算需要保存的文件夹 
		String currDateFolder = getCurrDateFolder();		
		// 文件名 
		String fileName = getMarking28() + '.' + getSuffixName(file.getOriginalFilename());				
		// 需要保存到的文件夹地址 
		String fileFolder = new File(uploadConfig.rootFolder).getAbsolutePath() + "/" +
				uploadConfig.httpPrefix + flieTypeFolder + currDateFolder + "/";	
		// 对外暴露的http路径
		String httpUrl = getDoMain() + uploadConfig.httpPrefix + flieTypeFolder + currDateFolder + "/" + fileName;	
		
		// 2、如果文件夹不存在，则先创建 
		File dirFile = new File(fileFolder);
		if(dirFile.exists() == false) {
			dirFile.mkdirs();
		}

		// 3、开始转存文件 
		try {
			File outFile = new File(fileFolder + fileName);
	        file.transferTo(outFile);		
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
		// 4、将文件外网地址返回给前台
		return httpUrl;
	}
	
	/** 验证文件大小  */
	static void checkFileSize(MultipartFile file) {
		// 文件大小(B)
		long size = file.getSize();
        if (size > uploadConfig.maxSize) {
        	throw new RuntimeException("文件大小超出限制");
        }
	}
	
	/** 
	 * 验证指定文件名是否存在于指定后缀列表中 
	 * 参数：文件名、后缀列表	
	 * case：checkSubffix("123.jpg", "jpg,png,gif")	验证通过  
	 */
	static void checkSubffix(String fileName, String suffixList) {
		// 获取后缀，并转为小写 
		String ext = getSuffixName(fileName).toLowerCase();	
		// 去空格，加逗号   
		String yxSuffix = suffixList.replace(" ", "") + ",";		
		if(yxSuffix.indexOf(ext + ",") == -1) {
			throw new RuntimeException("文件后缀验证未通过：" + ext);
		}
	}
	
	/** 返回随机生成的唯一标示28位唯一标示符 */
	static String getMarking28() {
		return System.currentTimeMillis() + "" + new Random().nextInt(Integer.MAX_VALUE);
	}

	/** 取文件后缀 */
	static String getSuffixName(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/** 返回今天的日期文件夹  */
	static String getCurrDateFolder() {
		String currDateFolder = new SimpleDateFormat("/yyyy/MM-dd").format(new Date()); 
		return currDateFolder;
	}
	
	/** 返回本服务器域名信息  */
	static String getDoMain() {
		return SystemObject.config.getDomain();
	}
	
}
