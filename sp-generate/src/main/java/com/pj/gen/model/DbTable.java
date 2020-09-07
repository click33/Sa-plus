package com.pj.gen.model;

import java.util.ArrayList;
import java.util.List;

import com.pj.gen.SUtil;
import com.pj.gen.cfg.GenCfgManager;

/**
 * 一个表
 * @author kongyongshun
 *
 */
public class DbTable {

	private String tableName;					// 表名字  
	private String tableComment;				// 表注释  
	// private String mkName;					// 模块名
	// private String modelName;				// 实体类名  -- 【只读字段】
	// private String varName;					// 变量名，返回此表起变量时的命名 -- 【只读字段】
	// private String packageModules;			// 模块报名 -- 【只读字段】
	private DbColumn primaryKey;				// 主键列  
	// private String packagePath;				// 包名 格式预览：package ${packagePath};   -- 【只读字段】
	// private boolean is_import_util;					// 判断是否需要导入 util包 （根据所有字段里是否有Date数据类型）;   -- 【只读字段】
	// private String kebabName;					// 转换成kebab-case形式     -- 【只读字段】
	
	// private boolean has_date;					// 此表内是否包含date类型字段【只读字段】
	// private boolean has_img;					// 此表内是否包含img类型字段【只读字段】
	// private boolean has_richtext;					// 此表内是否包含richtext类型字段【只读字段】
	
	
	private List<DbColumn> columnList;			// 列集合 	

	
	/**
	 * 范回列的String形式
	 * @return
	 */
	public List<String> getColumnListString() {
		List<String> sList = new ArrayList<>();
		for (DbColumn column : columnList) {
			sList.add(column.getColumnName());
		}
		return sList;
	}
	
	

	// 表名字 
	public void setTableName(String name) {
		this.tableName = name;
	}
	// 返回表名 
	public String getTableName() {
		return tableName;
	}
	// 返回表名，小写形式 
	public String getTableNameSmall() {
		return tableName.toLowerCase();
	}
	
	// 表注释 
	public String getTableComment() {
		if(this.tableComment == null) {
			return "";
		}
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	// 表主键 
	public DbColumn getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(DbColumn primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
	// 返回模块名 (对表名二次处理一下)
	public String getMkName(){
		return this.getTableName();
	}
	// 返回模块名 驼峰大写形式 
	public String getMkNameBig(){
		return SUtil.wordEachBig(getMkName());
	}
	
	// 返回实体类名(模块名下划线转大驼峰)
	public String getModelName(){
//		return getMkNameBig() + "Model";
		return getMkNameBig();
	}
	
	// 返回模块变量名  (实体类名首字母小写)
	public String getVarName() {
		// return getClassName().substring(0, 1).toLowerCase();
		return SUtil.wordFirstSmall(getMkNameBig());
	}
	// 返回变量名 - 简写模式  (只要实体类名首字母)
	public String getVarNameSimple() {
		return getModelName().substring(0, 1).toLowerCase();
	}
	// 返回模块实体类变量名
	public String getModelVarName() {
		return SUtil.wordFirstSmall(getModelName());
	}
	
	
	// 专属包名(模块名所有字母小写)
	public String getPackageModules() {
		// return getTableNameSmall();
		return getMkName().toLowerCase().replaceAll("_", GenCfgManager.cfg.packageUnderlineTo);
	}
	
	//  完全限定名包名 
	public String getPackagePath() {
		return GenCfgManager.cfg.packagePath + "." + getPackageModules();
	}

	// 返回表名转 kebab-case形式 
	public String getKebabName() {
		// return SUtil.xia_2_zhong(getTableName());
//		return SUtil.xia_2_zhong(getPackageModules());
		return SUtil.xia_2_zhong(getMkName());
	}
	
	
	
	
	// 是否需要导入Date包 
	public boolean getIs_import_util() {
		boolean is_flag = false;
		for (DbColumn dbColumn : getColumnList()) {
			if(dbColumn.getFieldType().equals("Date")) {
				is_flag = true;
			}
		}
		return is_flag;
	}
	
	// 是否包含指定表单  类型 
	public boolean hasFo(String ... foType) {
		boolean is_flag = false;
		for (String ft : foType) {
			for (DbColumn dbColumn : getColumnList()) {
				if(dbColumn.getFoType().equals(ft)) {
					is_flag = true;
				}
			}
		}
		return is_flag;
	}

	// private boolean has_date;					// 此表内是否包含date类型字段【只读字段】
	// private boolean has_img;					// 此表内是否包含img类型字段【只读字段】
	// private boolean has_richtext;					// 此表内是否包含richtext类型字段【只读字段】
	
	
	// 列集合 
	public List<DbColumn> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<DbColumn> columnList) {
		this.columnList = columnList;
	}
	
	// 返回列集合 - 所有不需要add/update的列
	public List<DbColumn> getColumnListByNotAdd() {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : this.columnList) {
			if(c.getFoType().equals("date-create") || c.getFoType().equals("date-update")) {
				list.add(c);
			}
		}
		return list;
	}
	
	// 返回列集合 - 所有外键列 
	public List<DbColumn> getColumnListByFk() {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : this.columnList) {
			if(c.getFoType().equals("fk-1") || c.getFoType().equals("fk-2")) {
				list.add(c);
			}
		}
		return list;
	}

	// 返回列集合 - 指定foType的 
	public List<DbColumn> getColumnListBy(String ...foType) {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : this.columnList) {
			for (String f : foType) {
				if(c.getFoType().equals(f)) {
					list.add(c);
				}
			}
		}
		return list;
	}
	
	// 返回这个表的所有外键字段：fk1、fk-2 
	public List<DbFk> getAllDbFk() {
		List<DbFk> list = new ArrayList<DbFk>();
		for (DbColumn c : this.columnList) {
			if(c.isFoType("fk-1", "fk-2")) {	// 如果是fk-1 或者 fk-2
				for (DbFk dbFk : c.getFkPkConcatList()) {
					list.add(dbFk);
				}
			}
		}
		return list;
	}
	
	
	
	// 返回Dao名
	public String getDaoName() {
		return getModelName() + "Dao"; 
	}
	// 返回Service名
	public String getServiceName() {
		return getModelName() + "Service"; 
	}

	
	// 返回服务端应该写入哪个文件夹
	public String getServerIoPath() {
		return GenCfgManager.cfg.getServerIoPath() + this.getPackageModules() + "\\";
	}
	// 返回后台管理应该写入哪个文件夹
	public String getAdminIoPath() {
		return GenCfgManager.cfg.getAdminIoPath() + this.getKebabName() + "\\";
	}
	// 返回接口文档应该写入哪个文件夹
	public String getApidocIoPath() {
		return GenCfgManager.cfg.getApidocIoPath();// + "\\";
	}
	
	
	//	// 返回Dao名 变量形式 
//	public String getDaoName() {
//		return getClassName() + "Dao"; 
//	}
//	// 返回Service名 变量形式 
//	public String getServiceName() {
//		return getClassName() + "Service"; 
//	}
//	

	// 返回这个表的所有列 
	public String getAllColumnString() {
		String str = "";
		for (int i = 0; i < this.columnList.size(); i++) {
			String columnName = this.columnList.get(i).getColumnName();
			if(GenCfgManager.cfg.sqlEnclose == 1) {
				columnName = "`" + columnName + "`";
			}
			str += columnName;	
			if(i != this.columnList.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}

	// 返回这个表的所有列2 (带引号的)
	public String getAllColumnString2() {
		String str = "";
		for (int i = 0; i < this.columnList.size(); i++) {
			str += "\"" + this.columnList.get(i).getColumnName() + "\"";	
			if(i != this.columnList.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}

	// 返回这个表的所有列3 (根据配置,下划线转驼峰,返回带引号的所有字段名)
	public String getAllColumnString3() {
		String str = "";
		for (int i = 0; i < this.columnList.size(); i++) {
			String column = this.columnList.get(i).getColumnName();
			if(GenCfgManager.cfg.getModelStyle() == 2){
				column = SUtil.wordEachBig_fs(column);
			}
			str += "\"" + column + "\"";
			if(i != this.columnList.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}
	
	// 根据配置返回*或者所有列
	public String getAllColumnStringOrStar() {
		if(GenCfgManager.cfg.sqlSelectColumnWay == 1) {
			return "*";
		} 
		if(GenCfgManager.cfg.sqlSelectColumnWay == 2) {
			return getAllColumnString();
		} 
		return "";
	}
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Table [name=" + tableName + ", comment=" + tableComment + ", columnList=" + columnList + "]";
	}



	


	



	



	



	

	
	
}
