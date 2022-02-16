package com.pj.current.global;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.pj.project4sp.apilog.SpApilogUtil;
import com.pj.utils.sg.AjaxJson;

/**
 * API全局日志, Controller 层切面 
 *
 * @author kong
 */
@Aspect
@Component
public class ApilogAspect {
    
	/**
	 * 定义AOP签名 --> 项目代码(所有class名成带有Controller字符的)
	 */
	@Pointcut("execution(* com.pj..*Controller*.*(..))")
    public void webLogProject(){}

    /**
     * 环绕日志 
     * @param pjp
     * @return 
     * @throws Throwable
     */
    @Around("webLogProject()")
    public Object surround(ProceedingJoinPoint pjp) throws Throwable {
        try {
        	// 1、执行 
            Object obj =  pjp.proceed();
            
            // 2、解析返回结果 
            // 如果是 AjaxJson 
            if(obj instanceof AjaxJson){	
            	SpApilogUtil.endRequest((AjaxJson)obj);
            } 
            // 如果是 String  
            else if (obj instanceof String) {	
            	SpApilogUtil.endRequest(AjaxJson.get(901, String.valueOf(obj)));
            } 
            // 如果都不是 
            else {	 
            	SpApilogUtil.endRequest(AjaxJson.get(902, String.valueOf(obj)));
            }
            return obj;
        } catch (Throwable e) {
        	throw e;
        }
    }
    
}