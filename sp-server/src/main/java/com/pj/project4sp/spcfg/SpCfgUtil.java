package com.pj.project4sp.spcfg;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * DB活动配置操作工具类
 * 
 * @author kong
 *
 */
@Component
public class SpCfgUtil {

	private static SpCfgService sysCfgService;
	@Autowired
	public void setSysCfgService(SpCfgService sysCfgService) {
		SpCfgUtil.sysCfgService = sysCfgService;
	}

	
	// ====================== 快捷读取 DB 配置信息 ========================== 

	/**
	 * 获取指定【cfgName】的配置，指定key项，并转化为String值 , 取不到值时，给默认值【defaultValue】
	 * @param cfgName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static String getCfgBy(String cfgName, String key, String defaultValue) {
		// 1、获取配置字符串 
		String cfgJson = sysCfgService.getCfgValue(cfgName);
		// 2、转换成Map
		Map<String, Object> maps = (Map)JSON.parse(cfgJson);
		// 3、取值
		Object value = maps.get(key);
		if (value == null) {
			return defaultValue;
		}
		return value.toString();
	}
	
	/**
	 * 获取server端指定配置信息 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getServerCfg(String key, String defaultValue) {
		return SpCfgUtil.getCfgBy("server_cfg", key, defaultValue);
	}
	
	/**
	 * 获取App端指定配置信息 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getAppCfg(String key, String defaultValue) {
		return SpCfgUtil.getCfgBy("app_cfg", key, defaultValue);
	}
	
	
	// ====================== 获取指定配置 ========================== 

	/** 获取app端全部配置信息 */
	public static String getAppCfg() {
		return sysCfgService.getCfgValue("AppCfg");
	}

	// --- app  
	/** 获取配置信息：系统名称  */
	public static String appName() {
		return SpCfgUtil.getAppCfg("appName", "");
	}
	/** 获取配置信息：版本号  */
	public static String appVersionNo() {
		return SpCfgUtil.getAppCfg("appVersionNo", "");
	}

	// --- server  
	/** 预留信息  */
	public static String reserveInfo() {
		return SpCfgUtil.getServerCfg("reserveInfo", "");
	}
	/** 是否在发生sql异常时，抛出sql，方便调试   */
	public static boolean throwOutSql() {
		return SpCfgUtil.getServerCfg("throwOutSql", "2").equals("1");
	}

	
	
	
	
}
