package com.pj.project4sp.public4mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 公共service，一大堆常见方法 
 * @author kong
 *
 */
@Service
public class PublicService {

	
	
	/**
	 * 以lambda方式开始事务
	 * @param code args
	 */
	@Transactional(rollbackFor = Exception.class)
	public void begin(JdbcLambdaBegin code) {
		code.run();
	}
	
	/**
	 * 以lambda方式开始事务
	 * @param begin begin
	 * @param rollback rollback
	 */
	@Transactional(rollbackFor = Exception.class)
	public void begin(JdbcLambdaBegin begin, JdbcLambdaRollback rollback) {
		try {
			begin.run();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if(rollback == null) {
				throw e;
			}
			rollback.run(e);
		}
	}
	
	
	/**
	 * 已lambda方式开启事务，并返回一个值 
	 * @param begin begin
	 * @return v
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	public <T> T beginRT(JdbcLambdaBeginRT begin) {
		Object return_obj =  begin.run();
		return (T)return_obj;
	}

	/**
	 *  已lambda方式开启事务，并返回一个值 
	 * @param begin begin
	 * @param rollback rollback
	 * @return v
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	public <T> T beginRT(JdbcLambdaBeginRT begin, JdbcLambdaRollbackRT rollback) {
		Object return_obj = null;
		try {
			return_obj = begin.run();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if(rollback == null) {
				throw e;
			}
			return_obj = rollback.run(e);
		}
		return (T)return_obj;
	}
	
	
	
	
}
