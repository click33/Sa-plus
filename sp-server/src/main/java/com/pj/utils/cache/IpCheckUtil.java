package com.pj.utils.cache;

import com.pj.utils.sg.AjaxError;
import com.pj.utils.sg.WebNbUtil;

import cn.dev33.satoken.spring.SpringMVCUtil;

/**
 * IP限流util
 * @author kong
 *
 */
public class IpCheckUtil {

	/**
	 *  持久化的key 
	 */
	static String key = "sys_ck_ip:";
	
	
	/**
	 * 指定ip的访问点设置为
	 * @param ip
	 */
	public static void setNow(String ip){
		RedisUtil.set(key + ip, System.currentTimeMillis() + "");
	}
	public static void setNow(){
		setNow(getMyIp());
	}
	
	/**
	 * 返回指定ip上一次接入是几秒前
	 * @param ip
	 * @return
	 */
	public static long getTs(String ip){
		String str = RedisUtil.get(key + ip);
		if(str == null) {
			str = "0";
		}
		return (System.currentTimeMillis() - Long.parseLong(str)) / 1000;
	}
	public static long getTs(){
		return getTs(getMyIp());
	}
	
	/**
	 * 检查指定ip是否属于频繁访问 ，如果是，则抛出异常 , 参数：ip、频繁秒数 
	 * @param ip
	 * @param s
	 */
	public static void checkIp(String ip, int s) {
		if(IpCheckUtil.getTs(ip) < s){
			throw AjaxError.get("操作频繁，请稍后尝试");
		}
	}
	public static void checkIp(int s) {
		checkIp(getMyIp(), s);
	}
	
	
	
	// 返回我的ip
	private static String getMyIp() {
		String ip = WebNbUtil.getIP(SpringMVCUtil.getRequest());
		return ip;
	}
	
}