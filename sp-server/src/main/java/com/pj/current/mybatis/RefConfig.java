package com.pj.current.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper.xml相关配置注入 
 * @author kong
 *
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
	

	/**
	 * 注入日志组件 （从yml文件中配置的方式，打包后有概率无法启动项目且无法解决，故用此方法注入自定义日志组件）
	 * @param sqlSessionFactory
	 */
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		sqlSessionFactory.getConfiguration().setLogImpl(MybatisStdOutImpl.class);
	}
	
}
