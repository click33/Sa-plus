package com.pj.gen.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Map< String, Object> 是最常用的一种Map类型，但是它写着麻烦 
 * <p>所以特封装此类，继承Map，进行一些扩展，可以让Map更灵活使用 
 * <p>最新：2020-9-12 去除setThis() 
 * @author kong
 */
public class SoMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public SoMap() {
	}
	

	/** 以下元素会在isNull函数中被判定为Null， */
	public static final Object[] NULL_ELEMENT_ARRAY = {null, "", "0", "0.0"};
	public static final List<Object> NULL_ELEMENT_LIST;

	
	static {
		NULL_ELEMENT_LIST = Arrays.asList(NULL_ELEMENT_ARRAY);
	}

	// ============================= 读值 =============================

	/** 获取一个值 */
	@Override
	public Object get(Object key) {
		if(key.equals("this")) {
			return this;
		}
		return super.get(key);
	}

	/** 如果为空，则返回默认值 */
	public Object get(Object key, Object defaultValue) {
		Object value = get(key);
		if(value == null || value.equals("")) {
			return defaultValue;
		}
		return value;
	}
	
	/** 转为String并返回 */
	public String getString(String key) {
		Object value = get(key);
		if(value == null) {
			return null;
		}
		return String.valueOf(value);
	}

	/** 如果为空，则返回默认值 */
	public String getString(String key, String defaultValue) {
		String value = getString(key);
		if(value == null || value.equals("")) {
			return defaultValue;
		}
		return value;
	}

	/** 转为int并返回 */
	public int getInt(String key) {
		String value = getString(key);
		if(value == null || value.equals("")) {
			return 0;
		}
		return Integer.valueOf(value);
	}
	/** 转为int并返回，同时指定默认值 */
	public int getInt(String key, int defaultValue) {
		String value = getString(key);
		if(value == null || value.equals("")) {
			return defaultValue;
		}
		return Integer.valueOf(value);
	}

	/** 转为long并返回 */
	public long getLong(String key) {
		String value = getString(key);
		if(value == null || value.equals("")) {
			return 0;
		}
		return Long.valueOf(value);
	}

	/** 转为double并返回 */
	public double getDouble(String key) {
		String value = getString(key);
		if(value == null || value.equals("")) {
			return 0.0;
		}
		return Double.valueOf(value);
	}

	/** 转为boolean并返回 */
	public boolean getBoolean(String key) {
		String value = getString(key);
		if(value == null || value.equals("")) {
			return false;
		}
		return Boolean.valueOf(value);
	}

	/** 转为Date并返回，根据自定义格式 */
	public Date getDateByFormat(String key, String format) {
		try {
			return new SimpleDateFormat(format).parse(getString(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** 转为Date并返回，根据格式： yyyy-MM-dd */
	public Date getDate(String key) {
		return getDateByFormat(key, "yyyy-MM-dd");
	}

	/** 转为Date并返回，根据格式： yyyy-MM-dd HH:mm:ss */
	public Date getDateTime(String key) {
		return getDateByFormat(key, "yyyy-MM-dd HH:mm:ss");
	}

	/** 获取集合 */
	@SuppressWarnings("unchecked")
	public List<Object> getList(String key) {
		Object value = get(key);
		List<Object> list = null;
		if(value == null || value.equals("")) {
			list = new ArrayList<Object>();
		}
		else if(value instanceof List) {
			list = (List<Object>)value;
		} else {
			list = new ArrayList<Object>();
			list.add(value);
		}
		return list;
	}

	/** 获取集合(指定类型) */
	public <T> List<T> getList(String key, Class<T> cs) {
		List<Object> list = getList(key);
		List<T> list2 = new ArrayList<T>();
		for (Object obj : list) {
			T objC = getValueByClass(obj, cs);
			list2.add(objC);
		}
		return list2;
	}

	/** 获取集合(逗号分隔式)，(指定类型) */
	public <T> List<T> getListByComma(String key, Class<T> cs) {
		String listStr = getString(key);
		if(listStr == null || listStr.equals("")) {
			return new ArrayList<>();
		}
		// 开始转化
		String [] arr = listStr.split(",");
		List<T> list = new ArrayList<T>();
		for (String str : arr) {
			if(cs == int.class || cs == Integer.class || cs == long.class || cs == Long.class) {
				str = str.trim();
			}
			T objC = getValueByClass(str, cs);
			list.add(objC);
		}
		return list;
	}


	/** 根据指定类型从map中取值，返回实体对象 */
	public <T> T getModel(Class<T> cs) {
		try {
			return getModelByObject(cs.newInstance());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 从map中取值，塞到一个对象中 */
	public <T> T getModelByObject(T obj) {
		// 获取类型 
		Class<?> cs = obj.getClass();
		// 循环复制  
		for (Field field : cs.getDeclaredFields()) {
			try {
				Object value = this.get(field.getName());	// 获取对象 
				if(value == null) {
					continue;
				}
				field.setAccessible(true);	// 打开私有访问
				Object valueConvert = getValueByClass(value, field.getType());
				field.set(obj, valueConvert);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("属性取值出错：" + field.getName(), e);
			}
		}
		return obj;
	}

	

	/**
	 * 将指定值转化为指定类型并返回
	 * @param obj
	 * @param cs
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueByClass(Object obj, Class<T> cs) {
		String obj2 = String.valueOf(obj);
		Object obj3 = null;
		if (cs.equals(String.class)) {
			obj3 = obj2;
		} else if (cs.equals(int.class) || cs.equals(Integer.class)) {
			obj3 = Integer.valueOf(obj2);
		} else if (cs.equals(long.class) || cs.equals(Long.class)) {
			obj3 = Long.valueOf(obj2);
		} else if (cs.equals(short.class) || cs.equals(Short.class)) {
			obj3 = Short.valueOf(obj2);
		} else if (cs.equals(byte.class) || cs.equals(Byte.class)) {
			obj3 = Byte.valueOf(obj2);
		} else if (cs.equals(float.class) || cs.equals(Float.class)) {
			obj3 = Float.valueOf(obj2);
		} else if (cs.equals(double.class) || cs.equals(Double.class)) {
			obj3 = Double.valueOf(obj2);
		} else if (cs.equals(boolean.class) || cs.equals(Boolean.class)) {
			obj3 = Boolean.valueOf(obj2);
		} else {
			obj3 = (T)obj;
		}
		return (T)obj3;
	}

	
	// ============================= 写值 =============================

	/**
	 * 给指定key添加一个默认值（只有在这个key原来无值的情况先才会set进去）
	 */
	public void setDefaultValue(String key, Object defaultValue) {
		if(isNull(key)) {
			set(key, defaultValue);
		}
	}

	/** set一个值，连缀风格 */
	public SoMap set(String key, Object value) {
		if(key.toLowerCase().equals("this")) {		// 防止敏感key 
			return this;
		}
		put(key, value);
		return this;
	}

	/** 将一个Map塞进SoMap */
	public SoMap setMap(Map<String, ?> map) {
		if(map != null) {
			for (String key : map.keySet()) {
				this.set(key, map.get(key));
			}
		}
		return this;
	}

	/** 将一个对象解析塞进SoMap */
	public SoMap setModel(Object model) {
		if(model == null) {
			return this;
		}
		Field[] fields = model.getClass().getDeclaredFields();
	    for (Field field : fields) {
	        try{
	            field.setAccessible(true);
	            this.set(field.getName(), field.get(field));
	        }catch (Exception e){
	        	throw new RuntimeException(e);
	        }
	    }
		return this;
	}

	
	// ============================= 删值 =============================

	/** delete一个值，连缀风格 */
	public SoMap delete(String key) {
		remove(key);
		return this;
	}

	/** 清理所有value为null的字段 */
	public SoMap clearNull() {
		Iterator<String> iterator = this.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			if(this.isNull(key)) {
				iterator.remove();
				this.remove(key);
			}

		}
		return this;
	}
	/** 清理指定key */
	public SoMap clearIn(String ...keys) {
		List<String> keys2 = Arrays.asList(keys);
		Iterator<String> iterator = this.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			if(keys2.contains(key) == true) {
				iterator.remove();
				this.remove(key);
			}
		}
		return this;
	}
	/** 清理掉不在列表中的key */
	public SoMap clearNotIn(String ...keys) {
		List<String> keys2 = Arrays.asList(keys);
		Iterator<String> iterator = this.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			if(keys2.contains(key) == false) {
				iterator.remove();
				this.remove(key);
			}

		}
		return this;
	}
	/** 清理掉所有key */
	public SoMap clearAll() {
		clear();
		return this;
	}
	

	// ============================= 快速构建 ============================= 

	/** 构建一个SoMap并返回 */
	public static SoMap getSoMap() {
		return new SoMap();
	}
	/** 构建一个SoMap并返回 */
	public static SoMap getSoMap(String key, Object value) {
		return new SoMap().set(key, value);
	}
	/** 构建一个SoMap并返回 */
	public static SoMap getSoMap(Map<String, ?> map) {
		return new SoMap().setMap(map);
	}

	/** 克隆指定key，返回一个新的SoMap */
	public SoMap cloneKeys(String... keys) {
		SoMap so = new SoMap();
		for (String key : keys) {
			so.set(key, this.get(key));
		}
		return so;
	}
	/** 克隆所有key，返回一个新的SoMap */
	public SoMap cloneSoMap() {
		SoMap so = new SoMap();
		for (String key : this.keySet()) {
			so.set(key, this.get(key));
		}
		return so;
	}

	/** 将所有key转为大写 */
	public SoMap toUpperCase() {
		SoMap so = new SoMap();
		for (String key : this.keySet()) {
			so.set(key.toUpperCase(), this.get(key));
		}
		this.clearAll().setMap(so);
		return this;
	}
	/** 将所有key转为小写 */
	public SoMap toLowerCase() {
		SoMap so = new SoMap();
		for (String key : this.keySet()) {
			so.set(key.toLowerCase(), this.get(key));
		}
		this.clearAll().setMap(so);
		return this;
	}
	/** 将所有key中下划线转为中划线模式 (kebab-case风格) */
	public SoMap toKebabCase() {
		SoMap so = new SoMap();
		for (String key : this.keySet()) {
			so.set(wordEachKebabCase(key), this.get(key));
		}
		this.clearAll().setMap(so);
		return this;
	}
	/** 将所有key中下划线转为小驼峰模式 */
	public SoMap toHumpCase() {
		SoMap so = new SoMap();
		for (String key : this.keySet()) {
			so.set(wordEachBigFs(key), this.get(key));
		}
		this.clearAll().setMap(so);
		return this;
	}
	/** 将所有key中小驼峰转为下划线模式 */
	public SoMap humpToLineCase() {
		SoMap so = new SoMap();
		for (String key : this.keySet()) {
			so.set(wordHumpToLine(key), this.get(key));
		}
		this.clearAll().setMap(so);
		return this;
	}
	
	
	
	
	// ============================= 辅助方法 =============================


	/** 指定key是否为null，判定标准为 NULL_ELEMENT_ARRAY 中的元素  */
	public boolean isNull(String key) {
		return NULL_ELEMENT_LIST.contains(getString(key));
	}

	/** 指定key列表中是否包含value为null的元素，只要有一个为null，就会返回true */
	public boolean isContainNull(String ...keys) {
		for (String key : keys) {
			if(this.isNull(key)) {
				return true;
			}
		}
		return false;
	}
	
	/** 与isNull()相反 */
	public boolean isNotNull(String key) {
		return !isNull(key);
	}

	/** 验证指定key不为空，为空则抛出异常 */
	public SoMap checkNull(String ...keys) {
		for (String key : keys) {
			if(this.isNull(key)) {
				throw new RuntimeException("参数" + key + "不能为空");
			}
		}
		return this;
	}

	static Pattern patternNumber = Pattern.compile("[0-9]*");
	/** 指定key是否为数字 */
	public boolean isNumber(String key) {
		String value = getString(key);
		if(value == null) {
			return false;
		}
	    return patternNumber.matcher(value).matches();   
	}

	
	
	

	// ============================= 工具方法 =============================
	
	

	// 指定字符串的字符串下划线转大写模式
	private static String wordEachBig(String str){
		String newStr = "";
		for (String s : str.split("_")) {
			newStr += wordFirstBig(s);
		}
		return newStr;
	}
	// 返回下划线转小驼峰形式
	private static String wordEachBigFs(String str){
		return wordFirstSmall(wordEachBig(str));
	}

	// 将指定单词首字母大写;
	private static String wordFirstBig(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	// 将指定单词首字母小写;
	private static String wordFirstSmall(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
	}

	// 下划线转中划线
	private static String wordEachKebabCase(String str) {
		return str.replaceAll("_", "-");
	}

	// 驼峰转下划线 
	private static String wordHumpToLine(String str) {
		return str.replaceAll("[A-Z]", "_$0").toLowerCase();
	}


	// ============================= 常见key （以下key经常用，所以封装以下，方便写代码） =============================


	// key: 表示字段的主类型 
	public static final String key_fo_type = "fo_type";		
	public String getFoType() {
		return getString(key_fo_type, "text");
	}
	public SoMap setFoType(String value) {
		set(key_fo_type, value);
		return this;
	}

	// key: 表示字段的注释 (解析后的)
	public static final String key_comment = "comment";		
	public String getComment() {
		return getString(key_comment);
	}
	public SoMap setComment(String value) {
		set(key_comment, value);
		return this;
	}

	// key: 表示字段的注释 (解析前的)
	public static final String key_intro = "intro";		
	public String getIntro() {
		return getString(key_intro);
	}
	public SoMap setIntro(String value) {
		set(key_intro, value);
		return this;
	}

	
	
	
	
	
	
	
	
	

}
