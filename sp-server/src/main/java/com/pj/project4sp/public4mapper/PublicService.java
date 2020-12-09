package com.pj.project4sp.public4mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	public void begin(JdbcLambdaBegin code) {
		code.run();
	}
	
	/**
	 * 以lambda方式开始事务
	 * @param begin begin
	 * @param rollback rollback
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
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
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	public <T> T beginRet(JdbcLambdaBeginRet begin) {
		Object returnObj =  begin.run();
		return (T)returnObj;
	}

	/**
	 *  已lambda方式开启事务，并返回一个值 
	 * @param begin begin
	 * @param rollback rollback
	 * @return v
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	public <T> T beginRet(JdbcLambdaBeginRet begin, JdbcLambdaRollbackRet rollback) {
		Object returnObj = null;
		try {
			returnObj = begin.run();
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			if(rollback == null) {
				throw e;
			}
			returnObj = rollback.run(e);
		}
		return (T)returnObj;
	}
	
	
	
	
}
