package com.pj.project4sp.spcfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.utils.sg.AjaxJson;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 系统配置相关 
 * @author kong
 *
 */
@RestController
@RequestMapping("/SpCfg/")
public class SpCfgController {

	@Autowired
	SpCfgService sysCfgService;
		
	/** 返回指定【cfgName】配置信息 */
	@RequestMapping("getCfg")
	public AjaxJson getCfg(String cfgName){
		StpUtil.checkPermission(AuthConst.SP_CFG);
		return AjaxJson.getSuccessData(sysCfgService.getCfgValue(cfgName));
	}
	
	/** 修改指定【cfgName】配置信息  */
	@RequestMapping("updateCfg")
	public AjaxJson updateCfg(String cfgName, String cfgValue){
		StpUtil.checkPermission(AuthConst.SP_CFG);
		int a=sysCfgService.updateCfgValue(cfgName, cfgValue);
		return AjaxJson.getByLine(a);
	}


	/** 返回应用配置信息 （对公开放的） */
	@RequestMapping("appCfg")
	public AjaxJson appCfg(){
		return AjaxJson.getSuccessData(sysCfgService.getCfgValue("app_cfg"));
	}
	
	
	
	
	
	
}
