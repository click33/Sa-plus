package com.pj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.dev33.satoken.spring.SaTokenSetup;

/**
 * 启动 
 * @author kong
 */
@SaTokenSetup	// sa-token权限验证 
@EnableCaching // 启用缓存
@EnableScheduling // 启动定时任务
@SpringBootApplication // springboot本尊
@EnableTransactionManagement // 启动注解事务管理
public class SpStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpStartApplication.class, args);
	}
	
}
