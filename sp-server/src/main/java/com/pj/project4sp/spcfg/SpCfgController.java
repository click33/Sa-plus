package com.pj.project4sp.spcfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.utils.sg.AjaxJson;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 系统配置相关 
 */
@RestController
@RequestMapping("/SpCfg/")
public class SpCfgController {

	
	// 配置信息
	@Autowired
	SpCfgService sysCfgService;
		
	
	// 返回指定【cfg_name】配置信息 
	@RequestMapping("getCfg")
	public AjaxJson getCfg(String cfg_name){
		StpUtil.checkPermission(AuthConst.p_sp_cfg);	// 鉴权 
		return AjaxJson.getSuccessData(sysCfgService.getCfgValue(cfg_name));
	}
	
	// 修改指定【cfg_name】配置信息 
	@RequestMapping("updateCfg")
	public AjaxJson updateCfg(String cfg_name, String cfg_value){
		StpUtil.checkPermission(AuthConst.p_sp_cfg);	// 鉴权 
		int a=sysCfgService.updateCfgValue(cfg_name, cfg_value);
		return AjaxJson.getByLine(a);
	}


	// 返回应用配置信息 （对公开放的）
	@RequestMapping("app_cfg")
	public AjaxJson app_cfg(){
		return AjaxJson.getSuccessData(sysCfgService.getCfgValue("app_cfg"));
	}
	
	
	
	
	
	
}
