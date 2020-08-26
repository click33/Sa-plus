package com.pj.utils.sg;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



/**
 *  web工具类
 * @author kong
 *
 */
public class WebNbUtil {

	// 工具方法

	/**
	 * 取出一个值，我保证不乱码,tomcat8及以上版本此方法废掉
	 */
	public static String getParam(HttpServletRequest request, String key) {
		try {
			request.setCharacterEncoding("UTF-8");
			String value = request.getParameter(key); // 获得v
			if (value != null && request.getMethod().equals("GET")) {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}
			return value;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	/**
	 * 取出一个值，我保证不乱码,tomcat8及以上版本专用
	 */
	public static String getParam8(HttpServletRequest request, String key) {
		try {
			if(request.getCharacterEncoding()==null) {
				request.setCharacterEncoding("UTF-8");
			}
			return request.getParameter(key);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	/**
	 * 将一个值，强制转码ISO_8859_1-->utf-8
	 */
	public static String getISO_8859_1(String str) {
		try {
			if(str==null) {
				return str;
			}
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将一个request请求所携带的参数封装成map返回
	 * <br/>对于数组型参数，此方法不能正确获得值
	 * @param request
	 * @return
	 */
	public static Map<String,String>getParamsMap(HttpServletRequest request){
		Map<String,String>map=new HashMap<String, String>();
		try {
			Enumeration<String> paramNames = request.getParameterNames();//获得K列表
			request.setCharacterEncoding("UTF-8");
			while (paramNames.hasMoreElements()) {  
				try {
					String key =paramNames.nextElement();	//获得k
					String value=request.getParameter(key);	//获得v
					if(request.getMethod().equals("GET")){
						//value=new String(value.getBytes("ISO-8859-1"),"UTF-8");
					}
					map.put(key,value);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return map;
	}
	
	/**  将一个request请求所携带的参数封装成map返回 ，带集合的 */
	public static Map<String, Object>getParamsMap2(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String[]> parameterMap = request.getParameterMap();	// 获取所有参数 
		for (String key : parameterMap.keySet()) {
			try {
				String[] values = parameterMap.get(key); // 获得values 
				if(values.length == 1) {
					map.put(key, values[0]);
				} else {
					List<String> list = new ArrayList<String>();
					for (String v : values) {
						list.add(v);
					}
					map.put(key, list);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}
	

	/**
	 * 将一个request请求Header所携带的参数封装成map返回
	 */
	public static Map<String,String>getHeaderMap(HttpServletRequest request){
		Map<String,String>map=new HashMap<String, String>();
		try {
			Enumeration<String> paramNames = request.getHeaderNames();//获得K列表
			request.setCharacterEncoding("UTF-8");
			while (paramNames.hasMoreElements()) {  
				try {
					String key =paramNames.nextElement();	//获得k
					String value=request.getHeader(key);	//获得v
					if(request.getMethod().equals("GET")){
						new String(value.getBytes("ISO-8859-1"),"UTF-8");
					}
					map.put(key,value);	
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 返回请求端的IP地址
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		ip = checkIp(ip) ? ip : (
                checkIp(ip = request.getHeader("Proxy-Client-IP")) ? ip : (
                        checkIp(ip = request.getHeader("WL-Proxy-Client-IP")) ? ip :
                                request.getRemoteAddr()));
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
	
	private static boolean checkIp(String ip) {
        return !NbUtil.isNull(ip) && !"unknown".equalsIgnoreCase(ip);
    }

	/**
	 * 返回指定地址在服务器上的绝对路径
	 */
	public static String getRealPath(HttpSession session, String path) {
		return session.getServletContext().getRealPath(path);// 路径
	}

	/**
	 * 返回项目的http地址
	 */
	public static String getHttpUrl(HttpServletRequest request, String port) {

		String path = request.getServletContext().getContextPath();
		StringBuffer url = request.getRequestURL();
		String http = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();	
		
		if(port != null && !port.equals("") && !port.equals("80") && !port.equals("443")){
			if(http.endsWith(":" + port) == false){
				http += ":" + port;
			}
		}
		http += path;
		if(http.endsWith("/") == false){
			http += "/";
		}
		
		return http;
	}
	
	
	/**
	 * 检测request请求是否为ajax
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return !(request.getHeader("x-requested-with") == null);
	}

	/**
	 * 将指定key的参数转化为int类型，转化不了的给默认值
	 */
	public static int getInt(HttpServletRequest request, String key, int default_value) {
		try {
			String arg_str = request.getParameter(key);
			return Integer.valueOf(arg_str);
		} catch (Exception e) {
			return default_value;
		}
	}

	
	
}
