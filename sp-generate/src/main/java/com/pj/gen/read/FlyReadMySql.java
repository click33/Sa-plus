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
			table.setTableComment(tcMap.get(tableName));	// 表注释
			getColumnList(table);	// 获取字段信息
			codeCfg.tableList.add(table);	// 添加进集合 
		}
	}
	
	
	
	// 获取指定表的所有列信息 
	public void getColumnList(DbTable table){
		List<DbColumn> columns = new ArrayList<>();
		try {
			Map<String, String> jtMap = ReadUtil.getJtMap(codeCfg.sqlFly, table.getTableName());
			ResultSet rs = codeCfg.sqlFly.getResultSet("show full columns from " + table.getTableName());
			while (rs.next()) {
				DbColumn column = DbModelManager.manager.getDbColumn();	 //new DbColumn();
				column.setColumnName(rs.getString("Field")); 				// 字段名 
				column.setColumnType(rs.getString("Type"));					// DB类型 
				column.setFieldType(jtMap.get(column.getColumnName()));		// java类型  
				column.setColumnComment(rs.getString("Comment"));		 	// 注释 
				columns.add(column);
			}
			table.setColumnList(columns);
			table.setPrimaryKey(columns.get(0));	// 主键列 TODO 先写死，就是第一个 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	

	
	
}
