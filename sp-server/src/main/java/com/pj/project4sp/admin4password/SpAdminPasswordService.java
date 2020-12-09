package com.pj.project4sp.admin4password;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pj.current.config.SystemObject;
import com.pj.project4sp.SP;

/**
 * 用户表 密码相关 
 * @author kong
 *
 */
@Service
public class SpAdminPasswordService {

	
	// REQUIRED=如果调用方有事务  就继续使用调用方的事务 
	/** 修改一个admin的密码为  */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)	
	public int updatePassword(long adminId, String password) {
		// 更改密码 
		SP.publicMapper.updateColumnById("sp_admin", "password", SystemObject.getPasswordMd5(adminId, password), adminId);
		if(SystemObject.config.getIsPw()) {
			// 明文密码 
			SP.publicMapper.updateColumnById("sp_admin", "pw", password, adminId);		
			return 2;
		}
		return 1;
	}
	
	
}
