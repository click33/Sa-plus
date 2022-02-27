package com.pj.utils.sg;

import com.pj.utils.cache.RedisUtil;

import cn.dev33.satoken.spring.SpringMVCUtil;

/**
 * IP限流util
 * @author kong
 *
 */
public class IpCheckUtil {

	/**
	 *  持久化的key 前缀 
	 */
	static String key = "sys_ck_ip:";
	
	static long timeout = 86400;
	
	/**
	 * 对当前ip对某个资源的最后访问时间设为Now 
	 * @param ip
	 */
	public static void setNow(String res){
		RedisUtil.setBySECONDS(key + res + ":" + getMyIp(), System.currentTimeMillis() + "", timeout);
	}
	
	/**
	 * 返回当前ip上一次访问某个资源是几秒前 
	 * @param ip
	 * @return
	 */
	public static long getTs(String res){
		String str = RedisUtil.get(key + res + ":" + getMyIp());
		if(str == null) {
			str = "0";
		}
		return (System.currentTimeMillis() - Long.parseLong(str)) / 1000;
	}
	
	/**
	 * 检查当前ip访问某个资源 是否属于频繁访问 ，如果是，则抛出异常
	 * @param res 指定资源
	 * @param s 限定多少秒内连续访问才算频繁 
	 */
	public static void checkRes(String res, int s) {
		if(IpCheckUtil.getTs(res) < s){
			throw AjaxError.get("操作频繁，请稍后尝试");
		}
	}
	
	/**
	 * 1、校验当前ip是否访问某个资源频繁，2、将最后访问时间点设为now() 
	 * @param res 具体资源 
	 * @param s 限定属于频繁的秒数  
	 */
	public static void checkResToNow(String res, int s) {
		checkRes(res, s);
		setNow(res);
	}
	
	// 返回当前访问者 IP 
	private static String getMyIp() {
		String ip = IpUtil.getIP(SpringMVCUtil.getRequest());
		return ip;
	}
	
}