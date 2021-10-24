package com.pj.project4sp.apilog;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.current.satoken.StpUserUtil;
import com.pj.utils.LogUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.WebNbUtil;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;

/**
 * 工具类：api请求记录表
 * @author kong 
 *
 */
@Component
public class SpApilogUtil {

	/** 底层 Mapper 对象 */
	static SpApilogMapper spApilogMapper;
	@Autowired
	public void setSpApilogMapper(SpApilogMapper spApilogMapper) {
		SpApilogUtil.spApilogMapper = spApilogMapper;
	}
	
	static final String APILOG_OBJ_SAVE_KEY = "APILOG_OBJ_SAVE_KEY";
	static final String APILOG_OBJ_SAVE_ID_KEY = "APILOG_OBJ_SAVE_ID_KEY";
	
	/**
	 * 请求开始时调用，开始计时 
	 */
	public static void startRequest() {
		if(isWeb() == false)  {
			return;
		}
		
		// 1、开始时 
    	HttpServletRequest request = SpringMVCUtil.getRequest();
    	SpApilog a = new SpApilog();
    	a.setId(getSnowflakeId());		
    	a.setReqIp(WebNbUtil.getIP(request));	
    	a.setReqApi(request.getRequestURI());;		
    	a.setReqParame(JSON.toJSONString(WebNbUtil.getParamsMap2(request)));	
    	a.setReqToken(StpUtil.getTokenValue());			
    	a.setReqHeader(JSON.toJSONString(WebNbUtil.getHeaderMap(request)));		
    	a.setReqType(request.getMethod());		
    	a.setAdminId(StpUtil.getLoginId(0L));	
    	a.setUserId(StpUserUtil.getLoginId(0L));		
    	a.setStartTime(new Date());			
    	request.setAttribute(APILOG_OBJ_SAVE_KEY, a);
    	
    	// 控制台日志 
    	LogUtil.info("----------------------------------------------------------------");
		LogUtil.info("IP: " + a.getReqIp() + "\tr-> " + a.getReqApi()+ "\tp-> " + a.getReqParame());
	}
	

	/**
	 * 请求结束时调用，结束计时 
	 * @param aj
	 */
	public static void endRequest(AjaxJson aj) {
		if(isWeb() == false)  {
			return;
		}
		
		// 读取本次请求的 ApiLog 对象 
		HttpServletRequest request = SpringMVCUtil.getRequest();
		SpApilog a = (SpApilog)request.getAttribute(APILOG_OBJ_SAVE_KEY);
		if(a == null) {
//	    	LogUtil.info("未找到相应ApiLog对象（可能原因：全局异常），aj=" + aj);
	    	SpApilogUtil.startRequest();	
	    	a = (SpApilog)request.getAttribute(APILOG_OBJ_SAVE_KEY);
		}

		// 保存数据库
		try {
			// 开始结束计时 
			a.setResCode(aj.getCode()); 	
			a.setResMsg(aj.getMsg());	
			a.setResString(new ObjectMapper().writeValueAsString(aj));		
			a.setEndTime(new Date());		
			a.setCostTime((int)(a.getEndTime().getTime() - a.getStartTime().getTime()));
			
			// res 字符串过长时禁止写入  
			if(a.getResString().length() > 50000) {
				a.setResString("{\"msg\": \"数据过长，无法写入 (length=" + a.getResString().length() + ")\"}");		
			}
		
        	LogUtil.info("本次请求耗时：" + ((a.getCostTime() + 0.0) / 1000) + "s, 返回：" + a.getResString());
        	spApilogMapper.saveObj(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	/**
	 * 当前是否为web环境 
	 */
	public static boolean isWeb() {
		// 大善人SpringMVC提供的封装 
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(servletRequestAttributes != null) {
			return true;
		}
		return false;
	}


	/** 获取当前请求的id */
	public static String getCurrReqId() {
		HttpServletRequest request = SpringMVCUtil.getRequest();
		String id = (String)request.getAttribute(APILOG_OBJ_SAVE_ID_KEY);
		if(id == null) {
			id = IdUtil.simpleUUID();
			request.setAttribute(APILOG_OBJ_SAVE_ID_KEY, id);
		}
		return id;
	}


	/**
	 * 根据雪花算法，返回唯一id 
	 * (此地方将workerId写死为1，如果你在分布式场景中应用此方法，你需要对workerId生成策略进行改造)
	 * @return
	 */
	public static String getSnowflakeId() {
		return IdUtil.getSnowflake(1, 1).nextIdStr();
	}
	
}
