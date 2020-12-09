package com.pj.project4sp.redis4console;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.pj.utils.sg.AjaxError;
import com.pj.utils.so.SoMap;


/**
 * redis 控制台相关操作 util 
 * @author kong
 *
 */
@Component
public class RedisConsoleUtil {

	
	static String dbN;

	/** 默认超时时间，单位周,此为一周 */
	public static long ttl = 24* 7;	

	/** 最大加载数量 */
	static long loadMax = 10000;

	/** string专用 */
	static StringRedisTemplate stringRedisTemplate;
	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplates, @Value("${spring.redis.database}") String dbN) {
		RedisConsoleUtil.stringRedisTemplate = stringRedisTemplates;
		RedisConsoleUtil.dbN = dbN;
	}

	
	
	/** 获取reids信息  */
	public static SoMap getInfo() {
		// 加载所有信息  
		Properties info = stringRedisTemplate.getRequiredConnectionFactory().getConnection().info();
//		System.out.println(info);
		SoMap map = new SoMap();
		for (String key : info.stringPropertyNames()) {
			map.put(key, info.getProperty(key));
		}
		
		// 加载所有信息 
		SoMap so = new SoMap();
		so.set("keys_count", getKeyCount(map));	// key 总数 
		so.set("keyspace_hits", map.get("keyspace_hits"));	// 被命中个数 
		so.set("used_memory_human", map.get("used_memory_human"));	// 已经占用内存数量 
		so.set("used_memory_peak_human", map.get("used_memory_peak_human"));	// 内存消耗峰值 
		so.set("uptime_in_seconds", map.get("uptime_in_seconds"));	// redis 已经启动的秒数 
		so.set("isGtMax", so.getLong("keys_count") > loadMax);	// 是否已经超过了最大值 
		
		return so;
	}
	
	

	/** 获取keys列表   */
	public static List<String> getKeys(String k) {

		// 如果为空，则查询所有 
		if(k == null || k.equals("")) {
			k = "*";
		}
		
		// 检查是否超过上限 
		Set<String> keysSet = stringRedisTemplate.keys(k);
		AjaxError.throwBy(keysSet.size() > loadMax, 501, "key值数量超" + loadMax + "<br/>为性能考虑无法返回数据，请更换筛选条件");
		
		// 排序
		List<String> keys = new ArrayList<String>();
		keys.addAll(keysSet);
		
		// 按照字典排序 
		Collections.sort(keys, new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);  
                return com.compare(o1, o2);  
  
            }  
        });

		return keys;
	}


	/** 获取单个的详情 */
	public static SoMap getByKey(String key) {
		// 键值 
		String value = stringRedisTemplate.opsForValue().get(key);
		// 过期时间 
		long expire = stringRedisTemplate.getExpire(key);	
		
		SoMap soMap = new SoMap()
				.set("key", key)
				.set("value", value)
				.set("ttl", expire);
		return soMap;
	}
	
	/** 写入一个值  */
	public static void setBySeconds(String key, String value, long timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	/**  删除一个值  */
	public static void del(String key) {
		stringRedisTemplate.delete(key);
	}

	/** 修改一个值的value  */
	public static void updateValue(String key, String value) {
		long expire = stringRedisTemplate.getExpire(key);
//		System.err.println(expire);
		// 1 = 永久 
		if(expire == -1) {
			stringRedisTemplate.opsForValue().set(key, value);
			return;
		}
		// -2 = 无此键 
		if(expire == -2) {
			return;
		}
		stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
	}

	/** 修改一个值的ttl  */
	public static void updateTtl(String key, long ttl) {
		if(ttl <= 0) {
			String value = stringRedisTemplate.opsForValue().get(key);
			stringRedisTemplate.opsForValue().set(key, value);
			return;
		}
		stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);
	}
	
	/** 根据info获取当前 key总数  */
	private static long getKeyCount(SoMap map) {
		long keysCount = 0;
		try {
			// 计算 key 总数
			String dbName = dbN;
			String dbInfo = map.getString("db" + dbName);
			if(dbInfo != null) {
				String[] arr = dbInfo.split(",");
				for (String item : arr) {
					String[] arr2 = item.split("=");
					if("keys".equals(arr2[0])) {
						keysCount = Long.valueOf(arr2[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keysCount;
	}
	
	
	

	
	
	
	
}
