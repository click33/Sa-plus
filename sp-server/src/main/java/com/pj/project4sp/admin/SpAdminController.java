package com.pj.project4sp.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.project4sp.SP;
import com.pj.project4sp.admin4password.SpAdminPasswordService;
import com.pj.project4sp.role.SpRoleUtil;
import com.pj.utils.sg.AjaxError;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;

/**
 * Controller -- 系统管理员表
 * @author kong
 */
@RestController
@RequestMapping("/admin/")
public class SpAdminController {

	@Autowired
	SpAdminMapper spAdminMapper;
	
	@Autowired
	SpAdminService spAdminService;
	
	@Autowired
	SpAdminPasswordService spAdminPasswordService;

	/** 增  */
	@RequestMapping("add")
	@SaCheckPermission(AuthConst.ADMIN_ADD)
	AjaxJson add(SpAdmin admin){
		long id = spAdminService.add(admin);
		return AjaxJson.getSuccessData(id);
	}

	/** 删 */
	@RequestMapping("delete")
	@SaCheckPermission(AuthConst.ADMIN_LIST)
	AjaxJson delete(long id){
		// 不能自己删除自己
		if(StpUtil.getLoginIdAsLong() == id) {
			return AjaxJson.getError("不能自己删除自己");
		}
		int line = spAdminMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 删 - 根据id列表 */
	@RequestMapping("deleteByIds")
	@SaCheckPermission(AuthConst.ADMIN_LIST)
	AjaxJson deleteByIds(){
		// 不能自己删除自己
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class); 
		if(ids.contains(StpUtil.getLoginIdAsLong())) {
			return AjaxJson.getError("不能自己删除自己");
		}
		// 开始删除 
		int line = SP.publicMapper.deleteByIds("sp_admin", ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改  -  name */
	@RequestMapping("update")
	@SaCheckPermission(AuthConst.ADMIN_LIST)
	AjaxJson update(SpAdmin obj){
		SpAdminUtil.checkName(obj.getId(), obj.getName());
		int line = spAdminMapper.update(obj);
		return AjaxJson.getByLine(line);
	}

	/** 改密码 */
	@RequestMapping("updatePassword")
	@SaCheckPermission({AuthConst.ADMIN_LIST, AuthConst.DEV})
	AjaxJson updatePassword(long id, String password){
		int line = spAdminPasswordService.updatePassword(id, password);
		return AjaxJson.getByLine(line);
	}
	
	/** 改头像  */
	@RequestMapping("updateAvatar")
	@SaCheckPermission(AuthConst.ADMIN_LIST)
	AjaxJson updateAvatar(long id, String avatar){
		int line = SP.publicMapper.updateColumnById("sp_admin", "avatar", avatar, id);
		return AjaxJson.getByLine(line);
	}
	
	/** 改状态  */
	@RequestMapping("updateStatus")
	@SaCheckPermission({AuthConst.ADMIN_LIST, AuthConst.DEV})
	public AjaxJson updateStatus(long id, int status) {

		// 验证对方是否为超管 
		if(StpUtil.hasPermission(id, AuthConst.DEV)){
			return AjaxJson.getError("抱歉，对方角色为最高权限，您暂时无法完成此操作");
		}
		
		// 修改状态 
		SP.publicMapper.updateColumnById("sp_admin", "status", status, id);
		// 如果是禁用，就将其强制注销 
		if(status == 2) {
			StpUtil.logout(id);
		}
		return AjaxJson.getSuccess();
	}
	
	/** 改角色  */
	@RequestMapping("updateRole")
	@SaCheckPermission({AuthConst.ADMIN_LIST, AuthConst.DEV})
	AjaxJson updateRole(long id, String roleId){
		// 改角色 
		int line = SP.publicMapper.updateColumnById("sp_admin", "role_id", roleId, id);
		AjaxError.throwByLine(line);
		
		// 清缓存 
		SpRoleUtil.clearRoleIdCache(id);
		
		// 返回 
		return AjaxJson.getSuccess();
	}
	
	/** 查  */
	@RequestMapping("getById")
	@SaCheckPermission(AuthConst.ADMIN_LIST)
	AjaxJson getById(long id){
		Object data = spAdminMapper.getById(id);
		return AjaxJson.getSuccessData(data);
	}

	/** 返回当前 Admin 信息  */
	@RequestMapping("getByCurr")
	AjaxJson getByCurr() {
		SpAdmin admin = SpAdminUtil.getCurrAdmin();
		return AjaxJson.getSuccessData(admin);
	}
	
	/** 查 - 集合 */
	@RequestMapping("getList")
	@SaCheckPermission(AuthConst.ADMIN_LIST)
	AjaxJson getList(){
		SoMap so = SoMap.getRequestSoMap();
		List<SpAdmin> list = spAdminMapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}

	/** 当前 Admin 修改自己信息 */
	@RequestMapping("updateInfo")
	AjaxJson updateInfo(SpAdmin obj){
		obj.setId(StpUtil.getLoginIdAsLong());
		SpAdminUtil.checkName(obj.getId(), obj.getName());
		int line = spAdminMapper.update(obj);
		return AjaxJson.getByLine(line);
	}

	/** 模拟指定账号登录 */
	@RequestMapping("runAs")
	@SaCheckPermission({AuthConst.ADMIN_LIST, AuthConst.DEV})
	AjaxJson runAs(long adminId) {
		// 如果不存在这个账号 
		if(SpAdminUtil.spAdminMapper.getById(adminId) == null) {
			return AjaxJson.getError("未找到账号：" + adminId);
		}
		// 如果要模拟的账号就是当前账号 
		if(StpUtil.getLoginIdAsLong() == adminId) {
			return AjaxJson.getError("不能自己模拟自己");
		}
		
		// 获取这个人的token 
		String token = StpUtil.createLoginSession(adminId);
		return AjaxJson.getSuccessData(token);
	}
	
}
