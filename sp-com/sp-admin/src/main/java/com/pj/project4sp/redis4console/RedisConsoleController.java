package com.pj.project4sp.redis4console;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.SoMap;

import cn.dev33.satoken.stp.StpUtil;

/**
 * redis相关操作 
 * @author kong 
 *
 */
@RestController
@RequestMapping("/RedisConsole/")
public class RedisConsoleController {


	// 获取一些基本预览信息 
	@RequestMapping("getPreInfo")
	public AjaxJson getPreInfo() {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		SoMap so = RedisConsoleUtil.getInfo();
		return AjaxJson.getSuccessData(so);
	}

	
	// 查询key集合  
	@RequestMapping("getKeys")
	public AjaxJson getKeys(String k) {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		List<String> keys = RedisConsoleUtil.getKeys(k);
		return AjaxJson.getSuccessData(keys);
	}
	
	// 查询某个值的详细信息 
	@RequestMapping("getByKey")
	public AjaxJson getByKey(String key) {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		SoMap soMap = RedisConsoleUtil.getByKey(key);
		return AjaxJson.getSuccessData(soMap);
	}
	

	// 添加一个键值 
	@RequestMapping("set")
	public AjaxJson set(String key, String value, long ttl) {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		RedisConsoleUtil.setBySECONDS(key, value, ttl);
		return AjaxJson.getSuccess();
	}


	// 删除一个键值 
	@RequestMapping("del")
	public AjaxJson del(String key) {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		RedisConsoleUtil.del(key);
		return AjaxJson.getSuccess();
	}
	

	// 修改一个值的value 
	@RequestMapping("updateValue")
	public AjaxJson updateValue(String key, String value) {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		RedisConsoleUtil.updateValue(key, value);
		return AjaxJson.getSuccess();
	}
	
	
	// 修改一个值的ttl 
	@RequestMapping("updateTTL")
	public AjaxJson updateTTL(String key, long ttl) {
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		RedisConsoleUtil.updateTTL(key, ttl);
		return AjaxJson.getSuccess();
	}
	

	// 删除多个键值 
	@RequestMapping("deleteByKeys")
	public AjaxJson deleteByKeys(@RequestParam(value="key[]") List<String> key) {
//		System.err.println(key);
		StpUtil.checkPermission(AuthConst.p_redis_console);	// 鉴权 
		for (String k : key) {
			RedisConsoleUtil.del(k);
		}
		return AjaxJson.getSuccess();
	}
	

	
	
	
	
}
