package com.pj.current.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Data;

/**
 * 项目自定义配置 
 * @author kong
 *
 */
@Data
@Component
@ConfigurationProperties(prefix="spring.myconfig")	
public class MyConfig {

	/**
	 * md5的盐 
	 */
	private String md5Salt;		
	
	/** 
	 * 是否明文存储密码 
	 */
	private Boolean isPw;		
	
	/** 
	 * 本项目部署到的服务器域名（文件上传、微信支付等等模块  要用到） 
	 */
	private String domain;			
	
	/**
	 * 是否彩色SQL日志 
	 */
	private Boolean colorSql = true;		

	/**
	 * 是否抛出SQL（将sql报错抛出到前端，方便调试，请只在开发环境打开，在生产模式请关闭）
	 */
	private Boolean throwSql = true;		
	
	/**
	 * 是否把API请求日志输出在控制台 
	 */
	private Boolean logToFile = true;

	/**
	 * 是否把API请求日志记录在数据库 
	 */
	private Boolean logToDb = true;
	
}
