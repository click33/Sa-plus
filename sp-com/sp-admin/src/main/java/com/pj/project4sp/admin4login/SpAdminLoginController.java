package com.pj.project4sp.admin4login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.project4sp.SP;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.annotation.SaCheckPermission;


/**
 * Controller: sp_admin_login -- 管理员登录日志表 
 * 
 * @author shengzhang 
 */
@RestController
@RequestMapping("/SpAdminLogin/")
public class SpAdminLoginController {

	/** 底层 Mapper 对象 */
	@Autowired
	SpAdminLoginMapper sysLoginLogMapper;

	/** 删 */  
	@RequestMapping("delete")
	@SaCheckPermission(SpAdminLogin.PERMISSION_CODE)
	public AjaxJson delete(Long id){
		int line = sysLoginLogMapper.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@RequestMapping("deleteByIds")
	@SaCheckPermission(SpAdminLogin.PERMISSION_CODE)
	public AjaxJson deleteByIds(){
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class); 
		int line = SP.publicMapper.deleteByIds(SpAdminLogin.TABLE_NAME, ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 查 - 根据id */  
	@RequestMapping("getById")
	@SaCheckPermission(SpAdminLogin.PERMISSION_CODE)
	public AjaxJson getById(Long id){
		SpAdminLogin s = sysLoginLogMapper.getById(id);
		return AjaxJson.getSuccessData(s);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	@RequestMapping("getList")
	@SaCheckPermission(SpAdminLogin.PERMISSION_CODE)
	public AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
		List<SpAdminLogin> list = sysLoginLogMapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
}
