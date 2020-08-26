package com.pj.project4sp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pj.project4sp.SP;
import com.pj.project4sp.admin4password.SpAdminPasswordService;

import cn.dev33.satoken.stp.StpUtil;

/**
 * Service: admin管理员
 * @author kong
 *
 */
@Service
public class SpAdminService {

	
	@Autowired
	SpAdminMapper spAdminMapper;
	
	@Autowired
	SpAdminPasswordService spAdminPasswordService;
	
	
	// 管理员添加一个管理员
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)	// REQUIRED=如果调用方有事务  就继续使用调用方的事务 
	public long add(SpAdmin admin) {
		// 检查姓名是否合法
		SpAdminUtil.checkAdmin(admin);
		
		// 开始添加
		admin.setCreate_by_aid(StpUtil.getLoginId_asLong());	// 创建人，为当前账号  
		spAdminMapper.add(admin);	// 添加
		long id = SP.publicMapper.getPrimarykey();	// 获取主键
		spAdminPasswordService.updatePassword(id, admin.getPassword2());	// 更改密码（md5与明文）
		
		// 返回主键 
		return id;
		// return AjaxJson.getSuccessData(id);
	}
	
	
	
	
}
