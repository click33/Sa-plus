package com.pj.gen.model;

import com.pj.gen.SUtil;
import com.pj.gen.cfg.GenCfgManager;

/**
 * 处理外键 
 * @author kong
 *
 */
public class DbFk {
	
	
	private DbColumn dc;		// 对应的数据库列 	（循环引用） 

	
	private String fkPkConcatName;	// 对应的连表查需要展示的字段名   
	private String fkPkConcatComment;	// 对应的连表查需要展示的字段名 的字段注释 

	private String  columnName;		// 对应的表列名  	
//	private String  fieldName;		// 实体类中字段名 	

	
	public DbFk(DbColumn dc, String fkPkConcatName, String fkPkConcatComment) {
		super();	
		this.dc = dc;	
		this.fkPkConcatName = fkPkConcatName;		
		this.fkPkConcatComment = fkPkConcatComment;		
		this.columnName = dc.fkPkTableName + "_" + fkPkConcatName; 	
	}
	
	
	/**
	 * @return fkPkConcatName
	 */
	public String getFkPkConcatName() {
		return fkPkConcatName;
	}

	/**
	 * @param fkPkConcatName 要设置的 fkPkConcatName
	 */
	public void setFkPkConcatName(String fkPkConcatName) {
		this.fkPkConcatName = fkPkConcatName;
	}

	/**
	 * @return fkPkConcatComment
	 */
	public String getFkPkConcatComment() {
		return fkPkConcatComment;
	}

	/**
	 * @param fkPkConcatComment 要设置的 fkPkConcatComment
	 */
	public void setFkPkConcatComment(String fkPkConcatComment) {
		this.fkPkConcatComment = fkPkConcatComment;
	}

	/**
	 * @return fieldName
	 */
	public String getFieldName() {
		if(GenCfgManager.cfg.getModelStyle() == 2) {
			String columnName = this.columnName;
			return SUtil.wordEachBig_fs(columnName);// 下划线转小驼峰 
		}
		return this.columnName;
//		// 如果无值，走默认配置
//		if(this.fieldName == null) {
//			if(GenCfgManager.cfg.getModelStyle() == 2) {
//				String columnName = this.columnName;
//				return SUtil.wordEachBig_fs(columnName);// 下划线转小驼峰 
//			}
//			return this.columnName;
//		} else {
//			if(GenCfgManager.cfg.getModelStyle() == 2) {
//				String fieldName = this.fieldName;
//				return SUtil.wordEachBig_fs(fieldName);// 下划线转小驼峰 
//			}
//			return this.fieldName;
//		}
	}
//
//	/**
//	 * @param fieldName 要设置的 fieldName
//	 */
//	public DbFk setFieldName(String fieldName) {
//		this.fieldName = fieldName;
//		return this;
//	}

	
	/**
	 * @return dc
	 */
	public DbColumn getDc() {
		return dc;
	}

	/**
	 * @param dc 要设置的 dc
	 */
	public void setDc(DbColumn dc) {
		this.dc = dc;
	}

	/**
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}


	/**
	 * @param columnName 要设置的 columnName
	 */
	public DbFk setColumnName(String columnName) {
		if(columnName != null && !columnName.equals("")) {
			this.columnName = columnName;
		}
		return this;
	}
	

	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DbFk [dc=" + dc + ", fkPkConcatName=" + fkPkConcatName + ", fkPkConcatComment=" + fkPkConcatComment
				+ ", columnName=" + columnName + "]";
	}


	
     	
	

	
	
	
	
	
	

}
