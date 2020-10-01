package com.pj.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志输出工具类
 */
@Slf4j
public class LogUtil {

	
	/**
	 * 输出
	 */
	public static void info(String str){
		//System.err.println(str);
		//System.out.println(str);
		log.info(str);
	}
	
	
	// 输出日志并且抛出异常
    public static void throwRuntimeException( String str, String logStr) throws RuntimeException{
        if(logStr != null){
            log.info(logStr);
        }
        throw new RuntimeException(str);
    }
    
	
}
