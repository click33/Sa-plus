package com.pj.project4sp.public4mapper;

/**
 * 以lambda表达式开启事务的辅助类
 * @author kong
 *
 */
public interface JdbcLambdaBeginRet {
	

	/**
	 * 执行事务的方法 
	 * @return
	 */
	public Object run();
	
	
}
