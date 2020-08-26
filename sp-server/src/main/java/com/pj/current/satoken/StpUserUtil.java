package com.pj.current.satoken;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.dev33.satoken.SaTokenManager;
import cn.dev33.satoken.SaTokenUtil;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.util.SaCookieUtil;
import cn.dev33.satoken.util.SpringMVCUtil;

/**
 * user表认证实现 
 * @author kong
 *
 */
@Service
public class StpUserUtil {

	/**
	 * 底层的 StpLogic 对象  
	 */
	public static StpLogic stpLogic = new StpLogic("user") {
		// 重写获取token的方法
		@Override
		public String getTokenValue() {
			// 0、获取相应对象 
			HttpServletRequest request = SpringMVCUtil.getRequest();
			SaTokenConfig config = SaTokenManager.getConfig();
			String key_tokenName = getKey_tokenName();
			
			// 1、尝试从request里读取 
			if(request.getAttribute(SaTokenUtil.JUST_CREATED_SAVE_KEY) != null) {
				return String.valueOf(request.getAttribute(SaTokenUtil.JUST_CREATED_SAVE_KEY));
			}

			// 4、尝试从请求体里面读取 
			if(config.getIsReadBody() == true){
				String tokenValue = request.getParameter(key_tokenName);
				if(tokenValue != null) {
					return tokenValue;
				}
			}

			// 3、尝试从header力读取 
			if(config.getIsReadHead() == true){
				String tokenValue = request.getHeader(key_tokenName);
				if(tokenValue != null) {
					return tokenValue;
				}
			}
			
			// 2、尝试从cookie里读取 
			Cookie cookie = SaCookieUtil.getCookie(request, key_tokenName);
			if(cookie != null){
				String tokenValue = cookie.getValue();
				if(tokenValue != null) {
					return tokenValue;
				}
			}
			
			// 5、都读取不到，那算了吧还是  
			return null;
		}
	
		// 
//		public String getKey_tokenName() {
//	 		return SaTokenManager.getConfig().getTokenName() + "user";
//	 	}
	}; 
	
	
	// =================== 获取token 相关 ===================


	/**
	 *  获取当前tokenValue
	 * @return 当前tokenValue
	 */
	public static String getTokenValue() {
		return stpLogic.getTokenValue();
	}

	/** 
	 * 获取指定id的tokenValue
	 * @param loginId 
	 * @return
	 */
	public static String getTokenValueByLoginId(Object loginId) {
		return stpLogic.getTokenValueByLoginId(loginId);
	}

	/**
	 * 获取当前会话的token信息：tokenName与tokenValue
	 * @return 一个Map对象 
	 */
	public static Map<String, String> getTokenInfo() {
		return stpLogic.getTokenInfo();
	}

	// =================== 登录相关操作 ===================

	/**
	 * 在当前会话上登录id 
	 * @param loginId 登录id ，建议的类型：（long | int | String）
	 */
	public static void setLoginId(Object loginId) {
		stpLogic.setLoginId(loginId);
	}

	/** 
	 * 当前会话注销登录
	 */
	public static void logout() {
		stpLogic.logout();
	}

	/**
	 * 指定login_id的会话注销登录（踢人下线）
	 * @param loginId 账号id 
	 */
	public static void logoutByLoginId(Object loginId) {
		stpLogic.logoutByLoginId(loginId);
	}

	// 查询相关

	/** 
 	 * 获取当前会话是否已经登录
 	 * @return 是否已登录 
 	 */
	public static boolean isLogin() {
		return stpLogic.isLogin();
	}

	/**
	 * 检验当前会话是否已经登录，如未登录，则抛出异常
 	 */
 	public static void checkLogin() {
 		getLoginId();
 	}
	
	/** 
 	 * 获取当前会话登录id, 如果未登录，则抛出异常
 	 * @return 
 	 */
	public static Object getLoginId() {
		return stpLogic.getLoginId();
	}

	/** 
	 * 获取当前会话登录id, 如果未登录，则返回默认值
	 * @param default_value
	 * @return
	 */
	public static <T> T getLoginId(T default_value) {
		return stpLogic.getLoginId(default_value);
	}
	
	/** 
	 * 获取当前会话登录id, 如果未登录，则返回null
	 * @return
	 */
	public static Object getLoginId_defaultNull() {
		return stpLogic.getLoginId_defaultNull();
 	}

	/** 
	 * 获取当前会话登录id, 并转换为String
	 * @return
	 */
	public static String getLoginId_asString() {
		return stpLogic.getLoginId_asString();
	}

	/** 
	 * 获取当前会话登录id, 并转换为int
	 * @return
	 */
	public static int getLoginId_asInt() {
		return stpLogic.getLoginId_asInt();
	}

	/**
	 * 获取当前会话登录id, 并转换为long
	 * @return
	 */
	public static long getLoginId_asLong() {
		return stpLogic.getLoginId_asLong();
	}

	/** 
 	 * 获取指定token对应的登录id，如果未登录，则返回 null 
 	 * @return 
 	 */
 	public static Object getLoginIdByToken(String tokenValue) {
 		return stpLogic.getLoginIdByToken(tokenValue);
 	}
	
	// =================== session相关 ===================

	/** 
	 * 获取指定login_id的session
	 * @param loginId
	 * @return
	 */
	public static SaSession getSessionByLoginId(Object loginId) {
		return stpLogic.getSessionByLoginId(loginId);
	}

	/** 
	 * 获取当前会话的session
	 * @return
	 */
	public static SaSession getSession() {
		return stpLogic.getSession();
	}

	// =================== 权限验证操作 ===================

	/** 
 	 * 指定login_id是否含有指定权限
 	 * @param loginId
 	 * @param pcode
 	 * @return
 	 */
	public static boolean hasPermission(Object loginId, Object pcode) {
		return stpLogic.hasPermission(loginId, pcode);
	}

	/** 
 	 * 当前会话是否含有指定权限
 	 * @param pcode
 	 * @return
 	 */
	public static boolean hasPermission(Object pcode) {
		return stpLogic.hasPermission(pcode);
	}

	/** 
 	 * 当前账号是否含有指定权限 ， 没有就抛出异常
 	 * @param pcode
 	 */
	public static void checkPermission(Object pcode) {
		stpLogic.checkPermission(pcode);
	}

	/** 
 	 * 当前账号是否含有指定权限 ， 【指定多个，必须全都有】
 	 * @param pcodeArray
 	 */
	public static void checkPermissionAnd(Object... pcodeArray) {
		stpLogic.checkPermissionAnd(pcodeArray);
	}

	/** 
 	 * 当前账号是否含有指定权限 ， 【指定多个，有一个就可以了】
 	 * @param pcodeArray
 	 */
	public static void checkPermissionOr(Object... pcodeArray) {
		stpLogic.checkPermissionOr(pcodeArray);
	}


}
