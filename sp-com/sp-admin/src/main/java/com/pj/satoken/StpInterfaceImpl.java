package com.pj.satoken;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.project4sp.admin.SpAdminMapper;
import com.pj.project4sp.role4permission.SpRolePermissionService;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 自定义Sa-Token权限认证接口扩展 
 * 
 * @author kong
 *
 */
@Component	
public class StpInterfaceImpl implements StpInterface {

	@Autowired
	SpAdminMapper spAdminMapper;
	
	@Autowired
	SpRolePermissionService spRolePermissionService;
	
	/** 返回一个账号所拥有的权限码集合  */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		if(loginType.equals(StpUtil.TYPE)) {
			long roleId = spAdminMapper.getById(Long.valueOf(loginId.toString())).getRoleId();
			return spRolePermissionService.getPcodeByRid(roleId);								
		}
		return null;
	}
	
	/** 返回一个账号所拥有的角色标识集合  */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		return null;
	}

}
