package com.pj.current.global;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pj.project4sp.apilog.SpApilogUtil;
import com.pj.project4sp.spcfg.SpCfgUtil;
import com.pj.utils.sg.AjaxError;
import com.pj.utils.sg.AjaxJson;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;

/**
 * 全局异常拦截
 */
@ControllerAdvice // 可指定包前缀，例如：(basePackages = "com.pj.controller.admin")
public class GlobalException {

	
	// 全局异常拦截
	@ResponseBody
	@ExceptionHandler
	public AjaxJson handlerException(Exception e) {

//    	System.out.println("全局异常");
    	
		// 打印堆栈，以供调试
		e.printStackTrace(); 

    	// 记录日志信息
    	AjaxJson aj = null;
    	// 如果是未登录异常 
		if(e instanceof NotLoginException){	
			aj = AjaxJson.getNotLogin();
		} 
		// 如果是权限异常
		else if(e instanceof NotPermissionException) {	
			NotPermissionException ee = (NotPermissionException) e;
			aj = AjaxJson.getNotJur("无此权限：" + ee.getCode());
		} 
		// 如果是AjaxError，则获取其具体code码 
		else if(e instanceof AjaxError) {		
			AjaxError ee = (AjaxError) e;
			aj = AjaxJson.get(ee.getCode(), ee.getMessage());
		}  
		// 普通异常输出：500 + 异常信息 
		else {	
			// 如果是SQLException，并且指定了hideSql，则只返回sql error 
			Throwable e2 = e.getCause();
			if((e instanceof SQLException || (e2 != null && e2 instanceof SQLException)) && SpCfgUtil.get_throw_out_sql() == false) {
				aj = AjaxJson.getError(e2.getMessage());
				SpApilogUtil.endRequest(aj);	// 无论是否打开隐藏sql，日志表记录的都是真实异常信息 
				return AjaxJson.getError("sql error").set("req_id", SpApilogUtil.getCurrReqId());
			} 
			aj = AjaxJson.getError(e.getMessage());
		}
		// 插入到日志表 
		SpApilogUtil.endRequest(aj);
		
		// 返回到前台 
		aj.set("req_id", SpApilogUtil.getCurrReqId());
		return aj;
	}
	


}
