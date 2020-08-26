package com.pj.current.mybatis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper.xml热刷新
 */
@Configuration
public class RefConfig {
	
	@Value("${autof5:true}")
	boolean autof5;
	
	@Bean(name="MybatisMapperDynamicLoader")
	public MybatisMapperDynamicLoader get() {
		// System.out.println("是啥===：" + autof5);
		return new MybatisMapperDynamicLoader(autof5);
	}
	
	
}
