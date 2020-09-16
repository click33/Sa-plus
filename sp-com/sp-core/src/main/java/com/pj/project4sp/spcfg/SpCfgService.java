package com.pj.project4sp.spcfg;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pj.project4sp.SP;

@Service
public class SpCfgService {

	
	// 获得cfg_value 根据cfg_name
	@Cacheable(value="sp_cfg_", key="#cfg_name")	// 加入缓存，以提高性能 
	public String getCfgValue(String cfg_name){
		return SP.publicMapper.getColumnByWhere("sp_cfg", "cfg_value", "cfg_name", cfg_name);
	}
	
	
	// 修改cfg_value 根据cfg_name
	@CacheEvict(value="sp_cfg_", key="#cfg_name")	// 清除缓存 
	public int updateCfgValue(String cfg_name, String cfg_value){
		return SP.publicMapper.updateColumnBy("sp_cfg", "cfg_value", cfg_value, "cfg_name", cfg_name);
	}
	
	
	
		
	
	
	
	
}
