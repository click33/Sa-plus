package com.pj.utils.sg;

import javax.servlet.http.HttpServletRequest;

import com.ejlchina.okhttps.OkHttps;
import com.pj.utils.so.SoMap;

/**
 * Ip相关操作 
 * @author kong
 *
 */
public class IpUtil {

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
	 * 根据ip获取地址 
	 * @param ip /
	 * @return /
	 */
	public static String getAddres(String ip) {
		try {
			// 如果是127.0.0.1
			if(ip.equals("127.0.0.1")) {
				return "unknown";
			}
			
			// 请求 
			String url = "http://whois.pconline.com.cn/ipJson.jsp"
					+ "?json=true"
					+ "&ip=" + ip;
			String body = OkHttps.sync(url).get().getBody().toString();
			SoMap so = SoMap.getSoMap().setJsonString(body);
			System.out.println(so);
			String addr = so.getString("addr");
			
			// 如果是局域网，则返回 unknown 
			if(addr == null || addr.contains("本机地址") || addr.contains("局域网")) {
				return "unknown";
			}
			
			// 正常返回 
			return addr;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknown";
		}
	}
	
}
