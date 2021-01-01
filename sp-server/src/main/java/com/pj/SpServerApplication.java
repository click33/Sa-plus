package com.pj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动 
 * @author kong 
 */
@EnableCaching // 启用缓存
@EnableScheduling // 启动定时任务
@SpringBootApplication // springboot本尊
@EnableTransactionManagement // 启动注解事务管理
public class SpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpServerApplication.class, args);
	}
	
}
