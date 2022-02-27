package com.pj.project4sp.admin4acc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.pj.project4sp.admin.SpAdmin;
import com.pj.project4sp.admin.SpAdminUtil;
import com.pj.project4sp.role4permission.SpRolePermissionService;
import com.pj.project4sp.spcfg.SpCfgUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.NbUtil;
import com.pj.utils.so.SoMap;

import cn.dev33.satoken.stp.StpUtil;

/**
 * Admin账号相关的接口 
 * 
 * @author kong
 *
 */
@RestController
@RequestMapping("/AccAdmin/")
public class SpAccAdminController {

	@Autowired
	SpAccAdminService spAccAdminService;
	
	@Autowired
	SpRolePermissionService spRolePermissionService;
	
	/** 账号、密码登录  */
	@RequestMapping("doLogin")
	@SentinelResource(value = "qps-max-1", blockHandler = "doLoginBlock")
	AjaxJson doLogin(String key, String password) {
		// 1、验证参数 
		if(NbUtil.hasNull(key, password)) {
			return AjaxJson.getError("请提供key与password参数");
		}
		return spAccAdminService.doLogin(key, password);
	}
	// 限流之后触发的函数 
	AjaxJson doLoginBlock(String key, String password, BlockException e) {
		return AjaxJson.getError("访问过于频繁，请稍后再试");
	}
	
	/** 退出登录  */
	@RequestMapping("doExit")
	AjaxJson doExit() {
		StpUtil.logout();
		return AjaxJson.getSuccess();
	}
	
	/** 获取会话信息 */
	@RequestMapping("getLoginInfo")
	AjaxJson getLoginInfo(HttpServletRequest request) {
		// 当前admin
		SpAdmin admin = SpAdminUtil.getCurrAdmin();
		
		// 组织参数 (admin信息，权限信息，配置信息)
		SoMap map = new SoMap();
		map.set("admin", admin);	
		map.set("perList", spRolePermissionService.getPcodeByRid(admin.getRoleId()));				
		map.set("appCfg", SpCfgUtil.getAppCfg());	
		return AjaxJson.getSuccessData(map); 
	}
	
}
