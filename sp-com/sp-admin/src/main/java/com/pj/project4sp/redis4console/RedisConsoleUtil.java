package com.pj.project4sp.redis4console;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.pj.utils.sg.SoMap;


/**
 * redis 控制台相关操作 util 
 */
@Component
public class RedisConsoleUtil {

	
	static String db_n;


	public static long ttl = 24* 7;	//默认超时时间，单位周,此为一周


//	// 对象专用
//	public static RedisTemplate<String, Object> redisTemplate;
//	@Autowired
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void setRedisTemplate(RedisTemplate redisTemplate) {
//		RedisConsoleUtil.redisTemplate = redisTemplate;
//	}

	// string专用
	static StringRedisTemplate stringRedisTemplate;
	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplates, @Value("${spring.redis.database}") String db_n) {
		RedisConsoleUtil.stringRedisTemplate = stringRedisTemplates;
		RedisConsoleUtil.db_n = db_n;
//		System.err.println(db_n);
	}

	
	
	// 获取reids信息 
	public static SoMap getInfo() {
		// 加载所有信息  
		Properties info = stringRedisTemplate.getRequiredConnectionFactory().getConnection().info();
//		System.out.println(info);
		Map<String, String> map = new HashMap<>();
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
		
		return so;
	}
	
	

	// 获取keys列表 
	public static List<String> getKeys(String k) {

		// 如果为空，则查询所有 
		if(k == null || k.equals("")) {
			k = "*";
		}
		
		// 如果小于1万，直接返回 
		long max = 10000;
		Set<String> keys_set = stringRedisTemplate.keys(k);
		List<String> keys = new ArrayList<String>();
		keys.addAll(keys_set);
		
		// 按照字典排序 
		Collections.sort(keys, new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);  
                return com.compare(o1, o2);  
  
            }  
        });
		
		//  如果小于一万，则只返回一万 
		if(keys.size() <= max) {
			return keys;
		}
		
		//  如果大于一万，则只返回一万 
		int i = 0;
		List<String> keys2 = new ArrayList<String>();
		for (String key : keys) {
			keys2.add(key);
			i++;
			if(i >= max) {
				break;
			}
		}
		
		return keys2;
	}


	// 获取单个的详情 
	public static SoMap getByKey(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);	// 键值 
		long expire = stringRedisTemplate.getExpire(key);		// 过期时间 
		
		SoMap soMap = new SoMap()
				.set("key", key)
				.set("value", value)
				.set("ttl", expire);
		return soMap;
	}
	
	
	// 写入一个值 
	public static void setBySECONDS(String key, String value, long timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}
	

	// 删除一个值 
	public static void del(String key) {
		stringRedisTemplate.delete(key);
	}


	// 修改一个值的value 
	public static void updateValue(String key, String value) {
		long expire = stringRedisTemplate.getExpire(key);
		System.err.println(expire);
		if(expire == -1) {	// 1 = 永久 
			System.err.println("撒大大撒所");
			stringRedisTemplate.opsForValue().set(key, value);
			return;
		}
		if(expire == -2) {	// -2 = 无此键 
			return;
		}
		stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
	}

	// 修改一个值的ttl 
	public static void updateTTL(String key, long ttl) {
		if(ttl <= 0) {
			String value = stringRedisTemplate.opsForValue().get(key);
			stringRedisTemplate.opsForValue().set(key, value);
			return;
		}
		stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);
	}
	
	
	
	
	
	// 根据info获取当前 key总数 
	private static long getKeyCount(Map<String, String> map) {
		long keys_count = 0;
		try {
			// 计算 key 总数
			String db_name = db_n;
			String db_info = map.get("db" + db_name);
			if(db_info != null) {
				String[] arr = db_info.split(",");
				for (String item : arr) {
					String[] arr2 = item.split("=");
					if(arr2[0].equals("keys")) {
						keys_count = Long.valueOf(arr2[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keys_count;
	}
	
	
	

	
	
	
	
}
