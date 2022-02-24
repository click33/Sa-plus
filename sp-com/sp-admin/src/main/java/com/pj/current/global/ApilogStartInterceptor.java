package com.pj.current.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pj.project4sp.apilog.SpApilogUtil;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;

/**
 * API全局日志 初始化拦截器 - 注册 
 * 
 * @author kong
 */
@Configuration
public class ApilogStartInterceptor implements WebMvcConfigurer {

	/**
	 * 注册API日志拦截器  
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
			/*
			 * 这里是请求的入口 （除过滤器外代码最先开始执行的地方）：
			 * 		用来初始化本次请求的日志记录 
			 * 请求出口有三个地方：
			 * 		情况1：请求在 Controller 正常执行，会在 GlobalAspect 全局日志处走出 
			 * 		情况2：请求在 Controller 发生异常，会在 GlobalException 全局异常处走出 
			 * 		情况3：请求的 /path 404 了，会在 NotFoundHandle 处走出 
			 */
			SpApilogUtil.startRequest();
		}))
		
		 // 排除掉 /SgApilog/** 相关接口，因为访问 [全局日志相关API] 本身时不应该产生API日志 
		.addPathPatterns("/**").excludePathPatterns("/SgApilog/**", "/error")
		 
		// -10000 保证此拦截器总是最先执行，因为靠后了容易失掉记录日志的机会 
		.order(-10000);
	}

}
