package com.pj.gen;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.pj.gen.cfg.GenCfgManager;

import cn.hutool.core.io.file.FileReader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * freemarker操作 的工具类
 * @author kong
 *
 */
public class FreeMarkerUtil {

	
	static String prefix = "freemarker/";		// 路径前缀 
	static String suffix = "";				// 路径后缀 
	
	/**
	 * 读取并返回 
	 * @param flt_url flt的路径 
	 * @param parameMap 参数集合 
	 * @return
	 */
	// public static String getResult(String flt_url, Map<String, Object> parameMap) {
	public static String getResult(String flt_url, Object dataModel) {
		
		// 1、从文件中读取字符串
		FileReader fileReader = new FileReader(prefix + flt_url + suffix);
		String str = fileReader.readString();
		
		// 2、让 freemarker解析遍历 
		StringWriter result = new StringWriter();
		try {
			Template t = new Template("template", new StringReader(str), new Configuration(Configuration.VERSION_2_3_23));
			t.process(dataModel, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// 3、返回结果 
		return result.toString();
	}
	
	// 重载一下 
	// 因为cfg对象几乎太常用了，几乎每个模板都必须用它，所以封装起来 
	public static String getResult(String flt_url, String appendKey, Object appendModel ) {
		Map<String, Object> parameMap = new HashMap<String, Object>();
		parameMap.put("cfg", GenCfgManager.cfg);
		parameMap.put(appendKey, appendModel);
		return getResult(flt_url, parameMap);
	}
	
	
	
}
