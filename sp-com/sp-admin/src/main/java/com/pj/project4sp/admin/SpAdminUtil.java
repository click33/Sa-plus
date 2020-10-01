package com.pj.project4sp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.utils.sg.AjaxError;
import com.pj.utils.sg.NbUtil;

import cn.dev33.satoken.SaTokenManager;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;

/**
 * admin工具类 
 * @author kong
 *
 */
@Component
public class SpAdminUtil {

	
	static SpAdminMapper spAdminMapper;
	@Autowired
	public void setSpAdminMapper(SpAdminMapper spAdminMapper) {
		SpAdminUtil.spAdminMapper = spAdminMapper;
	}
	
	
	// 当前admin
	public static SpAdmin getCurrAdmin() {
		long admin_id = StpUtil.getLoginIdAsLong();
		return spAdminMapper.getById(admin_id);
	}
	
	
	// 检查指定姓名是否合法 ,如果不合法，则抛出异常 
	public static boolean checkName(long admin_id, String name) {
		if(NbUtil.isNull(name)) {
			throw AjaxError.get("账号名称不能为空");
		}
		if(NbUtil.isNumber(name)) {
			throw AjaxError.get("账号名称不能为纯数字");
		}
//		if(name.startsWith("a")) {
//			throw AjaxException.get("账号名称不能以字母a开头");
//		}
		SpAdmin a2 = spAdminMapper.getByName(name);
		if(a2 != null && a2.getId() != admin_id) {	// 能查出来数据，而且不是本人，则代表与已有数据重复
			throw AjaxError.get("账号名称已有账号使用，请更换");
		} 
		return true;
	}
	
	// 检查整个admin是否合格 
	public static boolean checkAdmin(SpAdmin a) {
		// 检查姓名 
		checkName(a.getId(), a.getName());
		// 检查密码 
		if(a.getPassword2().length() < 4) {
			throw new AjaxError("密码不得低于4位");
		}
		return true;
	}
	
	
	
	// 指定的name是否可用 
	public static boolean nameIsOk(String name) {
		SpAdmin a2 = spAdminMapper.getByName(name);
		if(a2 == null) {
			return true;
		}
		return false;
	}
	
	
	// 获取指定token对应的admin_id 
	public static long getAdminIdByToken(String token) {
		Object login_id = SaTokenManager.getDao().getValue(StpUtil.stpLogic.getKeyTokenValue(token));
		if(login_id == null) {
			throw new NotLoginException();
		}
		return Long.parseLong(login_id.toString());
	}






	
	
}
