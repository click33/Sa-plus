package com.pj.project4sp.redis4console;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.annotation.SaCheckPermission;

/**
 * redis相关操作 
 * @author kong 
 *
 */
@RestController
@RequestMapping("/RedisConsole/")
public class RedisConsoleController {

	/** 获取一些基本预览信息  */
	@RequestMapping("getPreInfo")
	@SaCheckPermission(AuthConst.REDIS_CONSOLE)
	public AjaxJson getPreInfo() {
		SoMap so = RedisConsoleUtil.getInfo();
		return AjaxJson.getSuccessData(so);
	}

	/** 查询key集合   */
	@RequestMapping("getKeys")
	@SaCheckPermission(AuthConst.REDIS_CONSOLE)
	public AjaxJson getKeys(String k) {
		List<String> keys = RedisConsoleUtil.getKeys(k);
		return AjaxJson.getSuccessData(keys);
	}
	
	/** 查询某个值的详细信息  */
	@RequestMapping("getByKey")
	@SaCheckPermission(AuthConst.REDIS_CONSOLE)
	public AjaxJson getByKey(String key) {
		SoMap soMap = RedisConsoleUtil.getByKey(key);
		return AjaxJson.getSuccessData(soMap);
	}
	
	/** 添加一个键值  */
	@RequestMapping("set")
	@SaCheckPermission({AuthConst.REDIS_CONSOLE, AuthConst.DEV})
	public AjaxJson set(String key, String value, long ttl) {
		RedisConsoleUtil.setBySeconds(key, value, ttl);
		return AjaxJson.getSuccess();
	}

	/** 删除一个键值  */
	@RequestMapping("del")
	@SaCheckPermission({AuthConst.REDIS_CONSOLE, AuthConst.DEV})
	public AjaxJson del(String key) {
		RedisConsoleUtil.del(key);
		return AjaxJson.getSuccess();
	}
	
	/** 修改一个值的value  */
	@RequestMapping("updateValue")
	@SaCheckPermission({AuthConst.REDIS_CONSOLE, AuthConst.DEV})
	public AjaxJson updateValue(String key, String value) {
		RedisConsoleUtil.updateValue(key, value);
		return AjaxJson.getSuccess();
	}
	
	/** 修改一个值的ttl  */
	@RequestMapping("updateTtl")
	@SaCheckPermission({AuthConst.REDIS_CONSOLE, AuthConst.DEV})
	public AjaxJson updateTtl(String key, long ttl) {
		RedisConsoleUtil.updateTtl(key, ttl);
		return AjaxJson.getSuccess();
	}
	
	/** 删除多个键值  */
	@RequestMapping("deleteByKeys")
	@SaCheckPermission({AuthConst.REDIS_CONSOLE, AuthConst.DEV})
	public AjaxJson deleteByKeys() {
		List<String> keys = SoMap.getRequestSoMap().getListByComma("keys", String.class);
		for (String k : keys) {
			RedisConsoleUtil.del(k);
		}
		return AjaxJson.getSuccess();
	}
	
}
