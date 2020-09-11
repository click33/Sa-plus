package com.pj.gen.model;

import com.pj.gen.SUtil;
import com.pj.gen.cfg.GenCfgManager;
import com.pj.gen.utils.SoMap;

/**
 * 处理外键 
 * @author kong
 *
 */
public class DbFk {
	
	
	private DbColumn dc;		// 对应的数据库列 	（循环引用） 
	public DbTable dt;			// 对应的数据库表 	（循环引用） 
	public String type;			// 此外键的类型 

//	public String fkPkTableName;	// 外键要链接的表名 (fk-聚合 的时候才会存储)
//	public String fkPkColumnName;	// 外键要链接的列名 (fk-聚合 的时候才会存储)
	
	private String fkPkConcatName;	// 对应的连表查需要展示的字段名   
	private String fkPkConcatComment;	// 对应的连表查需要展示的字段名 的字段注释 

	private String  columnName;		// 对应的表列名  	
//	private String  fieldName;		// 实体类中字段名 	
	
	/*
	 * 在fk-*外键时: 
	 * jt = 连接表名、
	 * jc = 连接字段名、
	 * comment = 字段注释、
	 * js = 以上三者的缩写、
	 * and = 追加条件 (可省略)、
	 * where = 完全自定义条件 (可省略)、
	 * sql = 完全自定义sql (可省略) 、 
	 * ac = 设定聚合列, 默认: * (可省略) 、 
	 * java-type = 设定java中类型，默认值 long(可省略) 、
	 * as = 给列起个别名(可省略) 
	 * ttc = 指定连接本表的哪个字段, 默认连主键 (一般不用指定) 
	 */
	public SoMap tx = SoMap.getSoMap().deleteThis();

	
	public DbFk() {} 
	public DbFk(String type, DbColumn dc, String fkPkConcatName, String fkPkConcatComment) {
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
		// 如果是空，就不写入了 
		if(SUtil.isNull(columnName)) {
			return this;
		}
		// 赋值并返回 
		this.columnName = columnName;
		return this;
	}
	
	

	/**
	 * @return dt
	 */
	public DbTable getDt() {
		return dt;
	}
	/**
	 * @param dt 要设置的 dt
	 */
	public DbFk setDt(DbTable dt) {
		this.dt = dt;
		return this;
	}
	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type 要设置的 type
	 */
	public DbFk setType(String type) {
		this.type = type;
		return this;
	}
	/**
	 * @return tx
	 */
	public SoMap getTx() {
		return tx;
	}
	/**
	 * @param tx 要设置的 tx
	 */
	public DbFk setTx(SoMap tx) {
		this.tx = tx;
		return this;
	}
	

	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DbFk [fkPkConcatName=" + fkPkConcatName + ", fkPkConcatComment=" + fkPkConcatComment
				+ ", columnName=" + columnName + "]";
	}


	
	// 返回，剔除fk-前缀的类型
	public String getType2() {
		String type2 = this.type.replace("fk-", "");
		if(type2.equals("?")) {
			type2 = "custom";
		}
		return type2;
	}
	// 返回as列名
	public String getAsColumnName() {
		// 如果已经被整理过，则直接返回 
		if(this.columnName != null) {
			return this.columnName;
		}
		// 如果声明了fname 或 as，则直接返回 
		String asColumnName = tx.getString("fname", tx.getString("as"));
		if(asColumnName != null) {	
			return asColumnName;
		}
		// 如果没有，则返回标明+类型 
		asColumnName = tx.getString("jt") + "_" + getType2();
		return asColumnName;
	}
	// 返回as列名, 经过驼峰转化的 
	public String getAsColumnName_fs() {
		String asColumnName = getAsColumnName();
		if(GenCfgManager.cfg.modelStyle == 2) {
			asColumnName = SUtil.wordEachBig_fs(asColumnName);
		}
		return asColumnName;
	}
	// 默认的java-数据类型 
	public String getJavaType() {
		return tx.getString("java-type", "long");
	}
	
	/**
      * 返回该聚合查询的sql (完全java代码构建)
      */
	public String getJhSql() {
		String sql = "(";		// 样本: (select count(*) from comment where sid = user.id and type = 1) as 评论数量 
		// 如果声明了 sql (完全自定义sql) 
		if(tx.isNotNull("sql")) {
			sql += tx.getString("sql");
		} else {
			sql += "select " + getType2() + "(" + tx.getString("ac", "*") + ") from " + tx.getString("jt") + " where ";
			// 如果声明了where 
			if(tx.isNotNull("where")) {
				sql += tx.getString("where");
			} else {	// 否则来拼接 
				sql += tx.getString("jc") + " = " + dt.getTableName() + "." + tx.getString("ttc", dt.getPrimaryKey().getColumnName());
				// 如果声明了追加and
				if(tx.isNotNull("and")) {
					sql += " and " + tx.getString("and") + "";
				}
			}
		}
		// 拼接末尾，返回 
		sql += ") as " + getAsColumnName();
		return sql;
	}
	

	
	
	
	
	
	

}
