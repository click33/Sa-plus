package com.pj.project4sp.global;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.pj.project4sp.apilog.SpApilogUtil;
import com.pj.utils.sg.AjaxJson;

/**
 *  全局日志切面, 拦截所有controller请求，写入日志 
 * @author kong
 *
 */
@Aspect
@Component
public class GlobalAspect {
    
	/**
	 * 定义AOP签名 --> 项目代码(所有class名成带有Controller字符的)
	 */
	@Pointcut("execution(* com.pj..*Controller*.*(..))")
    public void webLogProject(){}

	
    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("webLogProject()")
    public Object surround(ProceedingJoinPoint pjp) throws Throwable {
    	// 1、开始时  移入 
    	SpApilogUtil.startRequest();
        try {
        	// 2、执行时 
            Object obj =  pjp.proceed();
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