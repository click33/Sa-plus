package com.pj.gen.read;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pj.gen.cfg.GenCfg;
import com.pj.gen.model.DbColumn;
import com.pj.gen.model.DbTable;
import com.pj.gen.model.DbModelManager;

public class FlyReadMySql implements FlyRead{

	// 配置信息
	GenCfg codeCfg;
	public FlyRead setCodeCfg(GenCfg codeCfg){
		this.codeCfg = codeCfg;
		return this;
	}
		

	// 开始读取 
	@Override
	public void readInfo() {
		Map<String, String> tcMap = ReadUtil.getTcMap(codeCfg.sqlFly);
		for (String tableName : codeCfg.tableNameList) {
			DbTable table = DbModelManager.manager.getDbTable();		// new DbTable();
			table.setTableName(tableName);	// 表名字 
			getColumnList(table);	// 获取字段信息
			table.setTableComment(tcMap.get(tableName));	// 表注释
			ReadUtil.f5TableFkName(table);  // 刷新重复的外键名称 
			codeCfg.tableList.add(table);	// 添加进集合 
		}
	}
	
	
	
	// 获取指定表的所有列信息 
	public void getColumnList(DbTable table){
		List<DbColumn> columns = new ArrayList<>();
		table.setColumnList(columns);	// 写入列集合 
		try {
			Map<String, String> jtMap = ReadUtil.getJtMap(codeCfg.sqlFly, table.getTableName());
			ResultSet rs = codeCfg.sqlFly.getResultSet("show full columns from " + table.getTableName());
			while (rs.next()) {
				DbColumn column = DbModelManager.manager.getDbColumn();	 // 获取一个new列类型 
				columns.add(column);
				column.setDt(table);
				column.setType(1); 											// 此列的类型 
				column.setColumnName(rs.getString("Field")); 				// 列名 
				column.setColumnType(rs.getString("Type"));					// 此列在数据库中的类型 
				column.setFieldType(jtMap.get(column.getColumnName()));		// 此列在java类中的类型  
				column.setColumnComment(rs.getString("Comment"));		 	// 此列的注释 
			}
			table.setPrimaryKey(columns.get(0));	// 主键列 TODO 先写死，就是第一个 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	

	
	
}
