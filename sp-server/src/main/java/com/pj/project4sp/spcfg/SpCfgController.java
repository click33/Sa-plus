package com.pj.project4sp.spcfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.current.satoken.AuthConst;
import com.pj.utils.sg.AjaxJson;

import cn.dev33.satoken.annotation.SaCheckPermission;

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
	@SaCheckPermission(AuthConst.SP_CFG)
	public AjaxJson getCfg(String cfgName){
		return AjaxJson.getSuccessData(sysCfgService.getCfgValue(cfgName));
	}
	
	/** 修改指定【cfgName】配置信息  */
	@RequestMapping("updateCfg")
	@SaCheckPermission(AuthConst.SP_CFG)
	public AjaxJson updateCfg(String cfgName, String cfgValue){
		int a=sysCfgService.updateCfgValue(cfgName, cfgValue);
		return AjaxJson.getByLine(a);
	}

	/** 返回应用配置信息 （对公开放的） */
	@RequestMapping("appCfg")
	public AjaxJson appCfg(){
		return AjaxJson.getSuccessData(sysCfgService.getCfgValue("app_cfg"));
	}
	
}
