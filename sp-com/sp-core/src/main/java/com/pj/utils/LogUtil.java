package com.pj.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志输出工具类
 */
@Slf4j
public class LogUtil {

	/**
	 * 输出 info 级别日志 
	 */
	public static void info(String str){
		log.info(str);
	}
	
}
