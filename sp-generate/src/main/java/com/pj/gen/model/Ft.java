package com.pj.gen.model;

import com.pj.gen.utils.SoMap;

/**
 * 字段类型对象 
 * @author kong
 *
 */
public class Ft {

	
	public String type = "";		// 字段类型 
	
	public String comment = "";	// 去除掉类型声明后，应该保留的注释 
	
	public SoMap txMap = new SoMap().deleteThis();		//  	// 特性Map 


	// 获取一个特性
	public String getTx(String tx_key) {
		String tv = this.txMap.getString(tx_key);
		if(tv == null) {
			tv = "";
		}
		return tv;
	}
	// 获取一个特性, 并写上默认值 
	public String getTx(String tx_key, String defaultValue) {
		String tv = this.txMap.getString(tx_key);
		if(tv == null || tv.equals("")) {
			return defaultValue;
		}
		return tv;
	}

	// 是否包含一个特性
	public boolean isTx(String tx_key) {
		Object tv = this.txMap.get(tx_key);
		if(tv == null || tv.equals("false")) {
			return false;
		}
		return true;
	}
	
	

	// 如果是一个聚合外键，获取它去掉(fk-)的形式
	public String getJhType() {
		return type.replaceFirst("fk-", ""); 
	}
	

	
	@Override
	public String toString() {
		return "Ft [type=" + type + ", comment=" + comment + ", txMap=" + txMap + "]";
	}

	

	
}
