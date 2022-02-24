package com.pj.project4sp.role4permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.project4sp.role.SpRoleUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.annotation.SaCheckPermission;

/**
 * Controller: 角色与权限的中间表 
 * @author kong
 *
 */
@RestController
@RequestMapping("/SpRolePermission/")
public class SpRolePermissionController {

	/** 底层Service */
	@Autowired
	SpRolePermissionService spRolePermissionService;
	
	/**
	 * 拉取权限id列表  根据指定roleId 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("getPcodeByRid")
	@SaCheckPermission({AuthConst.ROLE_LIST, AuthConst.DEV})
    public AjaxJson getPcodeByRid(@RequestParam(defaultValue="0") long roleId){
		// 防止拉出全部 	
		if(roleId == 0){
			return AjaxJson.getError("roleId不能为null或0");		
		}
		return AjaxJson.getSuccessData(spRolePermissionService.getPcodeByRid(roleId));
	}
	
	/** 拉取菜单id列表  根据当前用户roleId  */
	@RequestMapping("getPcodeByCurrRid")
	public AjaxJson getPcodeByCurrRid(){
		long roleId = SpRoleUtil.getCurrRoleId();
		List<String> list = spRolePermissionService.getPcodeByRid(roleId);
		return AjaxJson.getSuccessData(list);
	}
	
	/**
	 * 修改指定角色的拥有的权限 
	 * @param roleId 角色id
	 * @param code 拥有的权限码集合 
	 * @return
	 */
	@RequestMapping("updatePcodeByRid")
	@SaCheckPermission({AuthConst.ROLE_LIST, AuthConst.DEV})
	public AjaxJson updatePcodeByRid(long roleId){

		// 获取前端传过来的权限码集合 
    	List<String> list = SoMap.getRequestSoMap().getListByComma("codes", String.class); 
    	
		int line = spRolePermissionService.updateRoleMenu(roleId, list);
		return AjaxJson.getSuccessData(line);
	}
	
}
