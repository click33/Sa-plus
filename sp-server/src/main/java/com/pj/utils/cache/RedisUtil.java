package com.pj.utils.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis工具类
 * @author kongyongshun
 *
 */
@Component
public class RedisUtil {


	public static long ttl = 24* 7;	//默认超时时间，单位周,此为一周


	/**
	 * 对象专用
	 */
	public static RedisTemplate<String, Object> redisTemplate;
	@Autowired
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisUtil.redisTemplate = redisTemplate;
	}

	/**
	 * string专用
	 */
	static StringRedisTemplate stringRedisTemplate;
	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplates) {
		RedisUtil.stringRedisTemplate = stringRedisTemplates;
	}



	/* * * * * * * * * * * * * * * * String操作 * * * * * * * * * * * * * * * * * * * * * * * * */


	// 默认7*24小时
	public static void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value, ttl, TimeUnit.HOURS);
	}

	// 写入，并设置时长，单位 Hours
	public static void setByHour(String key, String value, int timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.HOURS);
	}
	
	// 写入，并设置时长，单位 分钟 MINUTES
	public static void setByMINUTES(String key, String value, int timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
	}

	// 读取
	public static String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}


	// 删除
	public static void del(String key) {
		stringRedisTemplate.delete(key);
	}


	/* * * * * * * * * * * * * * * * List集合操作 * * * * * * * * * * * * * * * * * * * * * * * * */

	// 查询
	public static List<Object> forListGet(String key){
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	// 追加键值
	public static void forListAdd(String key, Object... args){
		redisTemplate.opsForList().rightPushAll(key,  args);
	}

	// 移除所有键值
	public static void forListRemove(String key){
		List<Object> list = forListGet(key);
		for (int i = 0; i < list.size(); i++) {
			redisTemplate.opsForList().remove(key, -1,list.get(i));
		}
	}


	/* * * * * * * * * * * * * * * * Object值操作 * * * * * * * * * * * * * * * * * * * * * * * * */

	// 写入值
	public static void forValueSet(String key, Object value){
		redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.HOURS);
	}

	// 读取值
	@SuppressWarnings("unchecked")
	public static <T>T forValueGet(String key, Class<T> cs){
		return (T) redisTemplate.opsForValue().get(key);
	}









}
