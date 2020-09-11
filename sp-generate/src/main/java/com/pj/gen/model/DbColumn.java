package com.pj.gen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pj.gen.SUtil;
import com.pj.gen.cfg.GenCfgManager;
import com.pj.gen.read.ReadUtil;
import com.pj.gen.utils.SoMap;

/**
 * 一个列
 */
public class DbColumn {
	
	public DbTable dt;		// 所属的数据库表 	（循环引用） 



	private String columnName;			// 列名字
	private String columnComment;		// 字段注释
	private String columnType;			// 数据库类型 
	// private String fieldName;			// 对应的java名字   【只读字段】
	// private String getset;			// 对应的getset形式   【只读字段】
	private String fieldType;			// 对应的java类型

//	private String defaultValue;			// 数据类型对应的java默认值 
	
	
	// 枚举相关 
	private String foType = "text";		// 表单类型 
	private Map<String, String> jvList = new HashMap<String, String>();	// 如果是枚举类型，则代表所有枚举类型 
	
	// 外键相关 (在DbFk中也有存储此数据，但是这并不冗余，因为)
	public String fkPkTableName;	// 如果是个外键(fk-1或fk-2)，对应的主键表名  
	public String fkPkColumnName;	// 如果是个外键(fk-1或fk-2)，对应的主键字段名字  
	
//	private String fkPkConcatName;	// 如果是个外键，对应的连表查需要展示的字段名   （只记录首个，在fk-1用到）
//	private String fkPkConcatComment;	// 如果是个外键，对应的连表查需要展示的字段名 的字段注释 （只记录首个，在fk-1用到）
	
	private List<DbFk> fkPkConcatList;	// 连表字段集合 
	

//	private Map<String, String> txMap = new HashMap<String, String>();	// 特性Map 
	private SoMap txMap = new SoMap();	// 特性Map 
	
	
	public DbColumn() {
	}
	public DbColumn(String columnName, String columnComment, String columnType, String fieldType) {
		super();
		this.columnName = columnName;
		this.columnComment = columnComment;
		this.columnType = columnType;
		this.fieldType = fieldType;
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
	public DbColumn setDt(DbTable dt) {
		this.dt = dt;
		return this;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnComment() {	// 正常版
		return columnComment;
	}
	public String getColumnComment2() {	// 去空格版 
		if(columnComment == null) {
			return "";
		}
		return columnComment.replaceAll(" ", "");
	}
	public String getColumnComment3() {	// 去空格 和 去括号 
		String columnComment = this.columnComment;
		// 去空格
		if(columnComment == null) {
			return "";
		}
		columnComment = columnComment.replaceAll(" ", "");
		// 去括号
		// 获取枚举信息
		int start_index = columnComment.lastIndexOf("(");
		int end_index = columnComment.lastIndexOf(")");
		if(start_index > -1 && end_index > -1) {
			String k_str = columnComment.substring(start_index, end_index + 1);
			columnComment = columnComment.replace(k_str, "");
		}
		return columnComment;
	}
	
	// 写入字段注释 ========================
	public void setColumnComment(String columnComment) {
		try {
			// 获取解析的ft
			Ft ft = ReadUtil.getFt(columnComment);
			String foType = ft.type;
			this.columnComment = ft.comment;
			this.txMap = ft.txMap;
			
			// 开始判断, 不同类型不同展现 、
			if(foType.equals("text") || foType.equals("t") || foType.equals("input")) {	// 普通input 
				this.foType = "text";
			}
			else if(foType.equals("num")) {	// 数字input 
				this.foType = "num";
			}
			else if(foType.equals("textarea") || foType.equals("d")) {	// 多行文本域 
				this.foType = "textarea";
			}
			else if(foType.equals("img")) {	// 单图片
				this.foType = "img";
			}
			else if(foType.equals("audio")) {	// 单音频
				this.foType = "audio";
			}
			else if(foType.equals("video")) {	// 单视频
				this.foType = "video";
			}
			else if(foType.equals("file")) {	// 单文件(任意类型)
				this.foType = "file";
			}
			else if(foType.equals("img-list") || foType.equals("imgList") || foType.equals("img_list")) {	// 多图片 
				this.foType = "img-list";
			}
			else if(foType.equals("audio-list") || foType.equals("audioList") || foType.equals("audio_list")) {	// 多音频 
				this.foType = "audio-list";
			}
			else if(foType.equals("video-list") || foType.equals("videoList") || foType.equals("video_list")) {	// 多视频
				this.foType = "video-list";
			}
			else if(foType.equals("file-list") || foType.equals("fileList") || foType.equals("file_list")) {	// 多文件(任意类型)
				this.foType = "file-list";
			}
			else if(foType.equals("img-video-list") || foType.equals("imgVideoList") || foType.equals("img_video_list")) {	// 图片与视频混合_list
				this.foType = "img-video-list";
			}
			else if(foType.equals("date") || foType.equals("datetime")) {	// 日期 
				this.foType = "date";
				this.fieldType = "Date";
			}
			else if(foType.equals("date-create")) {	// 日期 - create 创建日期
				this.foType = "date-create";
				this.fieldType = "Date";
			}
			else if(foType.equals("date-update")) {	// 日期 - update 更新日期 
				this.foType = "date-update";
				this.fieldType = "Date";
			}
			else if(foType.equals("richtext") || foType.equals("f")) {	// 富文本
				this.foType = "richtext";
			}
			else if(foType.equals("enum") || foType.equals("j")) {	// 枚举 
				this.foType = "enum";
				this.txMap.setDefaultValue("s-type", "2");	// list页，默认单选文字
				this.txMap.setDefaultValue("a-type", "3");	// add页，默认单选按钮
				// 获取枚举信息
				int start_index = this.columnComment.lastIndexOf("(");
				int end_index = this.columnComment.lastIndexOf(")");
				if(start_index == -1 || end_index == -1) {
					return;
				}
				// 切割字符串 , 来获取 
				this.jvList = new LinkedHashMap<String, String>();
				String eStr = this.columnComment.substring(start_index + 1, end_index);
				String[] eArr = eStr.split(",");
				if(eArr != null && eArr.length != 0) {
					for (String e : eArr) {
						try {
							String key = e.split("=")[0].trim();
							String value = e.split("=")[1].trim();
							this.jvList.put(key, value);
						} catch (Exception e2) {
							System.err.println("枚举字段(" + this.dt.getTableName() + "." + this.columnName + ")"+")解析可能出错：" + e2.getMessage());
						}
					}
				}
//				System.out.println(e_str);
			}
			else if(foType.equals("fk-1")) {	// fk-1  外键，下拉列表框 
				this.foType = "fk-1";
				String pkInfo = this.txMap.getString("js", this.txMap.getString("pk"));
				try {
					String[] pkArr = pkInfo.split("\\.");
					this.fkPkTableName = pkArr[0];
					this.fkPkColumnName = pkArr[1];
					this.fkPkConcatList = new ArrayList<DbFk>();
					fkPkConcatList.add(new DbFk("fk-1", this, pkArr[2], pkArr[3]).setColumnName(this.txMap.getString("fname", this.txMap.getString("as"))));		// 连表字段、连表字段注释
					// 肮脏的实现
//					this.fkPkConcatName = pkArr[2];
//					this.fkPkConcatComment = pkArr[3];
				} catch (Exception e) {
					System.err.println("外键字段(" + this.dt.getTableName() + "." + this.columnName + ")"+"解析出错：" + pkInfo+ "," + e.getMessage());
				}
				if(this.fkPkConcatList.size() == 0) {
//					System.err.println("外键解析出错：" + pkInfo+ "," + e.getMessage());
					throw new Exception("fk-1模式必须指定一个外键连表字段：" + pkInfo);	
				}
			}
			else if(foType.equals("fk-2")) {	// fk-2  外键，弹出选择页面 
				this.foType = "fk-2";
				this.txMap.setDefaultValue("showfk", true);	// 默认：显示本表外键  
				this.txMap.setDefaultValue("link", true);	// 默认：带点击链接 
				String pkInfo = this.txMap.getString("js", this.txMap.getString("pk"));
				try {
					String[] pkArr = pkInfo.split("\\.");
					this.fkPkTableName = pkArr[0];
					this.fkPkColumnName = pkArr[1];
					this.fkPkConcatList = new ArrayList<DbFk>();
					for (int i = 2; i < pkArr.length; i+=2) {
						fkPkConcatList.add(new DbFk("fk-2", this, pkArr[i], pkArr[i+1])
								.setColumnName(this.txMap.getString("fname", this.txMap.getString("as"))));		// 连表字段、连表字段注释		
					}
//					this.fkPkConcatName = pkArr[2];
//					this.fkPkConcatComment = pkArr[3];
				} catch (Exception e) {
					System.err.println("外键解析出错：" + pkInfo+ "," + e.getMessage());
				}
			}
			else if(foType.equals("no")) {	// no 添加修改时 不展示 
				this.foType = "no";
			}
			else {	// 什么都不是，还是默认吧 
				this.foType = "text";
			}
			// 后置工作
		} catch (Exception e) {
			System.err.println("字段(" + this.dt.getTableName() + "." + this.columnName + ")" + " 注释解析异常：" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
//	public static void main(String[] args) {
//		DbColumn c = new DbColumn();
//		c.setColumnComment("所属分类 [fk-1 pk=sys_type.id.name.所属分类]");
//		System.out.println(c);
//	}
//	
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getFieldName() {
		if(GenCfgManager.cfg.getModelStyle() == 2) {
			String columnName = this.getColumnName();
			return SUtil.wordEachBig_fs(columnName);// 下划线转小驼峰 
		}
		return columnName;
	}
//	public void setFieldName(String fieldName) {
//		this.fieldName = fieldName;
//	}
	public String getGetset() {
		return SUtil.getGetSet(getFieldName()) ;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	// 返回次类型的默认值 
	public String getDefaultValue() {
		String str = "\"\"";
		if("long".equals(fieldType) || "int".equals(fieldType) ) {
			str = "0";
		}
		return str;
	}

	
	// 判断其 foType 是否属于其中一种 
	public boolean isFoType(String ... foType) {
		boolean is_flag = false;
		for (String ft : foType) {
			if(this.getFoType().equals(ft)) {
				is_flag = true;
			}
		}
		return is_flag;
	}

	/**
	 * @return foType
	 */
	public String getFoType() {
		return foType;
	}
	/**
	 * @param foType 要设置的 foType
	 */
	public void setFoType(String foType) {
		this.foType = foType;
	}
	/**
	 * @return jvList
	 */
	public Map<String, String> getJvList() {
		return jvList;
	}
	/**
	 * @param jvList 要设置的 jvList
	 */
	public void setJvList(Map<String, String> jvList) {
		this.jvList = jvList;
	}

	
	
	/**
	 * @return txMap
	 */
	public SoMap getTxMap() {
		return txMap;
	}
	/**
	 * @param txMap 要设置的 txMap
	 */
	public void setTxMap(SoMap txMap) {
		this.txMap = txMap;
	}
	
	// 获取一个特性
	public String getTx(String tx_key) {
		String tv = this.txMap.getString(tx_key);
		if(tv == null) {
			tv = "";
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
	
	

	// 外键对应主键的表名，的模块，的接口名 
	public String getFkPkTableMkName() {
		return SUtil.wordEachBig(fkPkTableName);
	}

	// 外键对应主键的表名，的模块，的kebab-case形式 
	public String getFkPkTableKebabName() {
		String mkName = fkPkTableName;
		String packageModules = mkName.toLowerCase();
		return SUtil.xia_2_zhong(packageModules);
//		return SUtil.wordEachBig(fkPkTableName);
	}
	
	


	/**
	 * @return fkPkTableName
	 */
	public String getFkPkTableName() {
		return fkPkTableName;
	}
	public String getFkPkTableName2() {		// 表名下划线转小驼峰 
		return SUtil.wordEachBig_fs(fkPkTableName);
	}
	/**
	 * @param fkPkTableName 要设置的 fkPkTableName
	 */
	public void setFkPkTableName(String fkPkTableName) {
		this.fkPkTableName = fkPkTableName;
	}
	/**
	 * @return fkPkColumnName
	 */
	public String getFkPkColumnName() {
		return fkPkColumnName;
	}
	public String getFkPkFieldName() {	// 如果配置下划线转驼峰了
		if(GenCfgManager.cfg.getModelStyle() == 2) {
			String columnName = this.fkPkColumnName;
			return SUtil.wordEachBig_fs(columnName);// 下划线转小驼峰 
		}
		return fkPkColumnName;
	}
	/**
	 * @param fkPkColumnName 要设置的 fkPkColumnName
	 */
	public void setFkPkColumnName(String fkPkColumnName) {
		this.fkPkColumnName = fkPkColumnName;
	}
	
	
	
	/**
	 * @return fkPkConcatList
	 */
	public List<DbFk> getFkPkConcatList() {
		return fkPkConcatList;
	}
	/**
	 * @param fkPkConcatList 要设置的 fkPkConcatList
	 */
	public void setFkPkConcatList(List<DbFk> fkPkConcatList) {
		this.fkPkConcatList = fkPkConcatList;
	}
	
	
	
	/**
	 * 【只读字段】第一个外键的连表字段名
	 * @return fkPkConcatName
	 */
	public String getFkPkConcatName() {
		if(fkPkConcatList == null || fkPkConcatList.size() == 0) {
			return "";
		}
		String cName = fkPkConcatList.get(0).getFkPkConcatName();
		if(GenCfgManager.cfg.getModelStyle() == 2) {
			cName = SUtil.wordEachBig_fs(cName);	// 下划线转小驼峰 
		}
		return cName;
	}
//	/**
//	 * @param fkPkConcatName 要设置的 fkPkConcatName
//	 */
//	public void setFkPkConcatName(String fkPkConcatName) {
//		this.fkPkConcatName = fkPkConcatName;
//	}
	/**
	 * 【只读字段】第一个外键的连表字段注释 
	 * @return fkPkConcatComment
	 */
	public String getFkPkConcatComment() {
		if(fkPkConcatList == null || fkPkConcatList.size() == 0) {
			return "";
		}
		return fkPkConcatList.get(0).getFkPkConcatComment();
	}
//	/**
//	 * @param fkPkConcatComment 要设置的 fkPkConcatComment
//	 */
//	public void setFkPkConcatComment(String fkPkConcatComment) {
//		this.fkPkConcatComment = fkPkConcatComment;
//	}
//	
	
	
	
	
	
	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DbColumn [columnName=" + columnName + ", columnComment=" + columnComment + ", columnType=" + columnType
				+ ", fieldType=" + fieldType + ", foType=" + foType + ", jvList=" + jvList + ", fkPkTableName="
				+ fkPkTableName + ", fkPkColumnName=" + fkPkColumnName + ", fkPkConcatList=" + fkPkConcatList + ", txMap=" + txMap
				+ "]";
	}


	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
