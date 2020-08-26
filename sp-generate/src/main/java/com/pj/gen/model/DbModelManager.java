package com.pj.gen.model;

/**
 * 管理 DbModel 的创建 ，方便你的重写 
 * @author kong
 *
 */
public class DbModelManager {

	// 持有静态自己的引用  
	public static DbModelManager manager = new DbModelManager();
	
	// 创建一个 DbTable 
	public DbTable getDbTable() {
		return new DbTable();
	}
	
	// 创建一个 DbColumn 
	public DbColumn getDbColumn() {
		return new DbColumn();
	}
		
	
	
	
	
	
}
