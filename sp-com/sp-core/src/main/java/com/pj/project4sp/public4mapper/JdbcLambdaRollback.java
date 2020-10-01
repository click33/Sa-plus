package com.pj.project4sp.public4mapper;

/**
 * 以lambda表达式回滚事务的辅助类
 * @author kong
 *
 */
public interface JdbcLambdaRollback {
	
	/**
	 * 事务发生异常的方法 
	 * @param e
	 */
	public void run(Exception e);
	
}
