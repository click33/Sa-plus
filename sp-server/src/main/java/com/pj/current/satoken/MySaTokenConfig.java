package com.pj.current.satoken;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.dev33.satoken.interceptor.SaCheckInterceptor;

/**
 * sa-token代码方式进行配置 
 * @author kong 
 */
@Configuration
public class MySaTokenConfig implements WebMvcConfigurer {

	/**
	 * 注册sa-token的拦截器，打开注解式鉴权功能 (如果您不需要此功能，可以删除此类) 
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaCheckInterceptor()).addPathPatterns("/**"); 
    }
	
	
}
