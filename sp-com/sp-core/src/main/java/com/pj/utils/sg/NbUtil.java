package com.pj.utils.sg;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import cn.hutool.core.util.IdUtil;

/**
 * 最nb的工具类
 * @author kong
 *
 */
public class NbUtil {

	
	
	// 时间处理

	/**
	 * 返回指定时间的YYYY-MM-dd hh:mm:ss 字符串格式
	 */
	public static String getDtString(Date d){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
	}
	
	/**
	 * 返回系统当前时间的YYYY-MM-dd hh:mm:ss 字符串格式
	 */
	public static String getNow(){
		return getDtString(new Date());
	}
	/**
	 * 将一个字符串转换为日期格式（YYYY-MM-dd HH:mm:ss）
	 */
	public static Date getDt(String d) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 返回当前时间的指定形式 如（YYYY_MM_dd_HH_mm_ss）
	 * @return
	 */
	public static String getNowString(String geshi) {
		return new SimpleDateFormat(geshi).format(new Date());
	}
	/** 指定日期，指定格式 */
	public static String getDateString(Date date, String geshi) {
		return new SimpleDateFormat(geshi).format(date);
	}
	
	/**
	 * 获取指定日期的1号
	 */
	public static Date getYueOne(Date date) {
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        return calendar.getTime();  
	}
	
	
	
	//基本字符处理

	/**
	 * 该字符串是否为null或者空串
	 */
	public static boolean isNull(String str) {
		return (str == null || str.equals(""));
	}
	/**
	 * 该字符串是否为null或者空串
	 */
	public static boolean isOneNull(String ...str) {
		for (String string : str) {
			 if ((string == null || string.equals(""))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 如果一个字符串为(null,"","null")，则转化为指定值
	 */
	public static String toStr(String str, String toStr) {
		if (str == null || str.equals("") || str.equals("null")) {
			return toStr;
		}
		return str;
	}

	/**
	 * 如果该货不能转成一个数字，则返回指定值
	 */
	public static Integer toInt(String str, Integer toInt) {
		try {
			return new Integer(str);
		} catch (Exception e) {
			return toInt;
		}
	}

	private static Pattern patternNumberPattern = Pattern.compile("[0-9]*");
	/**
	 * 验证一个str是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
	    return patternNumberPattern.matcher(str).matches();   
	}

	private static Pattern patternNumberPhone = Pattern.compile("[1]\\d{10}");
	/**
	 * 验证一个str是否为手机号 
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str){
		if(str == null) {
			return false;
		}
	    return patternNumberPhone.matcher(str).matches();   
	}

	
	/** 判断一个数是否在0、1、2、3...10、20、30...100、200、300... 数列里面 */	
	private static Pattern patternSeries = Pattern.compile("[0-9]0*");
	public static boolean isSeries(int num) {
	    return patternSeries.matcher(num + "").matches();  
	}
	
	
	/**
	 * 将一个字符串ISO-8859-1转码UTF-8
	 */
	public static String toUtf8(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将字符串转化为指定数据类型
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getObjectByClass(String str, Class<T> cs){
		Object value = null;
		if(str == null){
			value = null;
		}else if (cs.equals(String.class)) {
			value = str;
		} else if (cs.equals(int.class)||cs.equals(Integer.class)) {
			value = new Integer(str);
        } else if (cs.equals(long.class)||cs.equals(Long.class)) {
        	value = new Long(str);
        } else if (cs.equals(short.class)||cs.equals(Short.class)) {
        	value = new Short(str);
        } else if (cs.equals(float.class)||cs.equals(Float.class)) {
        	value = new Float(str);
        } else if (cs.equals(double.class)||cs.equals(Double.class)) {
        	value = new Double(str);
        } else if (cs.equals(boolean.class)||cs.equals(Boolean.class)) {
        	value = new Boolean(str);
        }else{
        	throw new RuntimeException("超纲的类型：" + cs + "，未能转换值：" + str);
        }
		return (T)value;
	}
	
	/** 返回随机数 */
	public static int getRandom(int min, int max){
		max = max + 1;
	    Random random = new Random();
	    return random.nextInt(max) % (max - min + 1) + min;
	}
	
	/** 返回随机字符串 */
	public static String getRandomString(int length){
	     String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(62);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
	
	
	/**
	 * 返回唯一标示28位唯一标示符
	 */
	public static String getMarking28() {
		return System.currentTimeMillis()+""+new Random().nextInt(Integer.MAX_VALUE);
	}

	/** 取文件后缀 */
    public static String getSuffixName(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
	
    
	/** 指定Properties配置文件，读取成为Map, 返回null代表无此配置文件 */
    public static Map<String, String> readPropToMap(String propertiesPath){
    	Map<String, String> map = new HashMap<>();
		try {
	    	InputStream is = NbUtil.class.getClassLoader().getResourceAsStream(propertiesPath);	
	    	if(is == null){
	    		return null;
	    	}
			Properties prop = new Properties();
			prop.load(is);
			for (String key : prop.stringPropertyNames()) {
				map.put(key, prop.getProperty(key));
			}
		} catch (IOException e) {
			throw new RuntimeException("配置文件(" + propertiesPath + ")加载失败", e);
		}
		return map;
    }
    
    
	/** 初始化对象的属性，根据Map，支持直接为类static字段赋值 */
    public static Object initPropByMap(Map<String, String> map, Object obj){
    	Class<?> cs = null;
    	if(obj instanceof Class){
    		cs = (Class<?>)obj;		// 已经是类
    		obj = null;
    	}else{
    		cs = obj.getClass();	// 实例对象
    	}
    	
    	for (Field field : cs.getDeclaredFields()) {
			String value = map.get(field.getName());
			if (value == null) {
				continue; // 为空代表没配置此项
			}
			try {
				Object valueConvert = getObjectByClass(value, field.getType());
				field.set(obj, valueConvert);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("属性取值出错：" + field.getName(), e);
			}
		}
    	return obj;
    }
    
	/** 返回一个6位数手机验证码  */
	public static String getcolde() {
		return Double.toString(((Math.random()*9+1)*100000)).substring(0, 6);
	}
    
	
	// 返回雪花算法id
	public static String getSnowflakeId() {
		return IdUtil.getSnowflake(1, 1).nextIdStr();
	}
    
	
	
	
}
