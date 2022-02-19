package com.pj.project4sp.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.current.satoken.AuthConst;
import com.pj.project4sp.admin.SpAdminUtil;
import com.pj.utils.sg.AjaxError;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.NbUtil;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 工具类：SysRole模块 
 * @author kong
 *
 */
@Component
public class SpRoleUtil {
	
	/** 底层Mapper依赖 */
	static SpRoleMapper spRoleMapper;
	@Autowired
	public void setspRoleMapper(SpRoleMapper spRoleMapper) {
		SpRoleUtil.spRoleMapper = spRoleMapper;
	}
	
	/**
	 * 验证一个SysRole对象 是否符合标准 
	 * @param s
	 * @return
	 */
	static AjaxJson checkRole(SpRole s) {
		
		// 1、名称相关 
		if(NbUtil.isNull(s.getName())) {
			return AjaxJson.getError("昵称不能为空");
		}
		// 2、如果该名称已存在，并且不是当前角色 
		SpRole s2 = spRoleMapper.getByRoleName(s.getName());
		if(s2 != null && s2.getId() != s.getId()) {
			return AjaxJson.getError("昵称与已有角色重复，请更换");
		}
		
		// 重重检验，最终合格
		return AjaxJson.getSuccess();
	}
	
	/**
	 * 验证一个Role是否符合标准, 不符合则抛出异常 
	 * @param s
	 */
	static void checkRoleThrow(SpRole s) {
		AjaxJson aj = checkRole(s);
		if(aj.getCode() != AjaxJson.CODE_SUCCESS){
			throw AjaxError.get(aj.getMsg());
		}
	}


	/**
	 * 获取当前会话的roleId 
	 */
	public static long getCurrRoleId() {
		return getRoleIdByAdminId(StpUtil.getLoginIdAsLong());
	}

	/**
	 * 获取指定 Admin 账号的 roleId 
	 */
	public static long getRoleIdByAdminId(long adminId) {
		// 先获取其 User-Session 
		SaSession session = StpUtil.getSessionByLoginId(adminId, false);
		if(session == null) {
			// 如果此账号的 User-Session 尚未创建，则直接查库，避免创建 Session 缓存 
			return SpAdminUtil.spAdminMapper.getById(adminId).getRoleId();
		}
		
		// 从 Session 中获取 
		long roleId = session.get(AuthConst.ROLE_ID_KEY, () -> {
			return SpAdminUtil.spAdminMapper.getById(adminId).getRoleId();
		});
		
		return roleId;
	}
	
	/**
	 * 清空指定 Admin 账号的 roleId 值缓存 
	 */
	public static void clearRoleIdCache(long adminId) {
		// 先获取其 User-Session 
		SaSession session = StpUtil.getSessionByLoginId(adminId, false);
		if(session == null) {
			// 如果此账号的 User-Session 尚未创建，则不执行任何动作 
			return;
		}
		// 清空值 
		session.delete(AuthConst.ROLE_ID_KEY);
	}
	
	
}
