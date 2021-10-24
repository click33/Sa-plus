package com.pj.current.mybatis;

import org.apache.ibatis.logging.Log;

import com.pj.current.config.SystemObject;

/**
 * 自定义mybatis日志层实现，优化mybatis日志输出，主要优化以下部分：
 * <p> 1、删除无用日志信息
 * <p> 2、SQL高亮显示
 * @author kong
 *
 */
public class MybatisStdOutImpl implements Log {
	
	public MybatisStdOutImpl(String clazz) {
		// Do Nothing
	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	@Override
	public void error(String s, Throwable e) {
		System.err.println(s);
		e.printStackTrace(System.err);
	}

	@Override
	public void error(String s) {
		System.err.println(s);
	}

	/** 
	 * MyBatis动作 打印 
	 * 执行Sql与参数 打印
	 */
	@Override
	public void debug(String s) {
		// 以下日志，不再打印 
		if(s.startsWith("Creating") || s.startsWith("SqlSession") || s.startsWith("Cache") || s.startsWith("JDBC") || s.startsWith("Closing")) {
			return;
		}
		// 如果是sql语句，则: 蓝色、加粗、下划线 
		// 参考：https://blog.csdn.net/soinice/article/details/97052030
		if(SystemObject.config != null && SystemObject.config.getColorSql() && s.startsWith("==>  Preparing")) {
			s = "\033[34;1;4m" + s + "\033[0m";
//			s = s.replaceAll("==>  Preparing: ", "");
//			s = "==>  Preparing: " + s;
		}
		System.out.println(s);
	}

	/** 
	 * Sql执行结果，打印 
	 */
	@Override
	public void trace(String s) {
		System.out.println(s);
	}

	@Override
	public void warn(String s) {
		System.out.println(s);
	}


}
