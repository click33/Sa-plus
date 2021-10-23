package com.pj.gen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pj.gen.SUtil;
import com.pj.gen.cfg.GenCfgManager;
import com.pj.gen.utils.AjaxError;
import com.pj.gen.utils.SoMap;

/**
 * 一个列
 * @author kong
 *
 */
public class DbColumn {

	
	// ---------- 列类型 (1=普通字段, 2=连接外键, 3=聚合外键)
	public int type = 1;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	// ---------- 所属的数据库表 	（循环引用） 
	public DbTable dt;		
	public DbTable getDt() {
		return dt;
	}
	public DbColumn setDt(DbTable dt) {
		this.dt = dt;
		return this;
	}

	// ---------- 列名字
	private String columnName;		
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getFieldName() {	// 在实体类中的字段名 [fieldName] 
		if(GenCfgManager.cfg.getModelStyle() == 2) {
			String columnName = this.getColumnName();
			return SUtil.wordEachBig_fs(columnName);// 下划线转小驼峰 
		}
		return columnName;
	}
	public String getGetset() { // 在get和set时应该是什么样子 [getset] 
		return SUtil.getGetSet(getFieldName()) ;
	}
	public String getFieldNameFnCat() {	// 在方法拼接时的字段名(首字母大写或者拼接下划线) [fieldNameFnCat] 
		if(GenCfgManager.cfg.getModelStyle() == 2) {
			return SUtil.wordFirstBig(this.getFieldName());	// 首字母大写
		}
		return "_" + this.getFieldName();		// 拼接下划线 
	}

		
	
	
	// ---------- 字段注释
	private String columnComment;	
	public void setColumnComment(String columnComment) {
		try {
			// 如果type != 1, 立即停止解析
			if(type != 1) {
				this.columnComment = columnComment;
				return;
			}
			// 获取解析的ft
			SoMap ft = SUtil.getFt(columnComment);
			String foType = ft.getFoType();
			this.columnComment = ft.getComment();
			this.tx = ft;
			
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
			else if(foType.equals("richtext") || foType.equals("f")) {	// 富文本 
				this.foType = "richtext";
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
			else if(foType.equals("link")) {	// 连接类型 
				this.foType = "link";
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
			else if(foType.equals("file-list") || foType.equals("fileList") || foType.equals("file_list")) {	// 多文件(任意类型)
				this.foType = "file-list";
			}
			else if(foType.equals("time")) {	// 时间 - [时:分:秒]
				this.foType = "time";
			}
			else if(foType.equals("enum") || foType.equals("j")) {	// 枚举 
				this.foType = "enum";
				this.tx.setDefaultValue("s-type", "2");	// list页，默认单选文字
				this.tx.setDefaultValue("a-type", "3");	// add页，默认单选按钮
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
							// 判断是否为String类型, 如果是，则添加上单引号 
							if(tx.getString("dt", "").toLowerCase().equals("string")) {
								key = "'" + key + "'";
							}
							this.jvList.put(key, value);
							this.jvKeyList.add(key);
						} catch (Exception e2) {
							System.err.println("枚举字段(" + this.dt.getTableName() + "." + this.columnName + ")"+")解析可能出错：" + e2.getMessage());
						}
					}
				}
//					System.out.println(e_str);
			}
			else if(foType.equals("logic-delete") || foType.equals("lc-del")) {	// 逻辑删除标识 
				this.foType = "logic-delete";
				this.tx.setDefaultValue("yes", "1");
				this.tx.setDefaultValue("no", "0");
			}
			else if(foType.equals("no")) {	// no 添加修改时 不展示 [此特性已经遗弃，不建议使用]
				this.foType = "no";
			}
			else {	// 什么都不是，还是默认吧 
				this.foType = "text";
			}
			
			// 后置工作
			// 如果有click 
			if(this.istx("click")) {
				setClickInfo(tx.getString("click"));
//				if(tx.getString("click").indexOf(".") != -1) {
//					
////					this.foType = "click";
//				}
			}
			
			
		} catch (Exception e) {
			System.err.println("字段(" + this.dt.getTableName() + "." + this.columnName + ")" + " 注释解析异常：" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	public String getColumnComment() {	// 正常版 
		return columnComment;
	}
	public String getColumnComment2() {	// 去空格版 [columnComment2] 
		if(columnComment == null) {
			return "";
		}
		return columnComment.replaceAll(" ", "");
	}
	public String getColumnComment3() {	// 去空格 和 去括号 [columnComment3] 
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
	

	// 数据库类型 
	private String columnType;			
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	
	// 对应的java类型
	private String fieldType;			
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldType() {
		return fieldType;
	}
	public String getDefaultValue() {	// 返回此字段类型的变量默认值  [defaultValue] 
		String str = "\"\"";
		if("Integer".equals(fieldType)) {
			str = "0";
		}
		if("Long".equals(fieldType)) {
			str = "0L";
		}
		if("Double".equals(fieldType)) {
			str = "0.0";
		}
		if("Date".equals(fieldType)) {
			str = "new Date()";
		}
		return str;
	}

	// 枚举额外数据
	// 枚举所有取值 
	private Map<String, String> jvList = new HashMap<String, String>();	
	public Map<String, String> getJvList() {
		return jvList;
	}
	public void setJvList(Map<String, String> jvList) {
		this.jvList = jvList;
	}
	// 返回json形式的枚举值，形如：{1: '正常', 2: '禁用'}
	public String getJvJson() {
		String str = "";
		int i = 0;
		for (String key : jvList.keySet()) {
			// 判断key是否追加引号 
			String key2 = key;
			if(tx.getString("dt", "").toLowerCase().equals("string")) {
				key2 = "'" + key2 + "'";
			}
			// 拼接参数 
			str += key2 + ": " + "'" + jvList.get(key) + "'";
			if(i != jvList.size() - 1) {
				str += ", ";
			}
			i++;
		}
		str = "{" + str + "}";
		return str;
	}

	// 枚举所有取值的key 
	private List<String> jvKeyList = new ArrayList<String>();	
	public List<String> getJvKeyList() {
		return jvKeyList;
	}
	public void setJvKeyList(List<String> jvKeyList) {
		this.jvKeyList = jvKeyList;
	}


	// ---------- 当此列是一个fk-s-t时，此字段代表其对应外键的数量，
	public int showCount = 0;
	public int getShowCount() {
		return showCount;
	}
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	
	
	
	// ---------------------------------------- 方法 ---------------------------------------- 

	// fo类型 
	/*
	 * 类型：
	 * 		s
	 * 		fk-s	连接外键
	 * 		fk-p	聚合外键
	 * 
	 * 属性：
	 * 		no-s		是否在检索条件处不显示
	 * 		no-show		是否在查询列表表格里不显示
	 */
	private String foType = "text";		
	public String getFoType() {
		return foType;
	}
	public void setFoType(String foType) {
		this.foType = foType;
	}
	// fn: 判断其 foType 是否属于其中一种 
	public boolean isFoType(String ... foType) {
		boolean is_flag = false;
		for (String ft : foType) {
			if(this.getFoType().equals(ft)) {
				is_flag = true;
			}
		}
		return is_flag;
	}
	

	// 特性Map 
	/**
	 * flag 	标记 
	 * 在fk-s 连接外键时：
	 * 		js=配置连接信息，或拆分为：
	 * 			curr=这边的字段
	 * 			jt=要连接的表名
	 * 			jc=要连接的字段
	 * 		show=配置展示信息，或拆分为：
	 * 			catc=展示列
	 * 			comment=展示列的注释
	 * 		drop=true 是否展示下拉列表框
	 * 		java-type = 设定java中类型，默认值 String(可省略) 、
	 * 		as = 给列起个别名(可省略) 
	 * 
	 * 
	 * 在fk-* 聚合外键时：
	 * 		外键时: 
	 * 		jt = 连接表名、
	 * 		jc = 连接字段名、
	 * 		comment = 字段注释、
	 * 		js = 以上三者的缩写、
	 * 		and = 追加条件 (可省略)、
	 * 		where = 完全自定义条件 (可省略)、
	 * 		sql = 完全自定义sql (可省略) 、 
	 * 		ac = 设定聚合列, 默认: * (可省略) 、 
	 * 		java-type = 设定java中类型，默认值 long(可省略) 、
	 * 		as = 给列起个别名(可省略) 
	 * 		curr = 指定连接本表的哪个字段, 默认连主键 (一般不用指定) 
	 * 
	 */
	public SoMap tx = new SoMap();	
	public SoMap getTx() {
		return tx;
	}
	public void setTx(SoMap tx) {
		this.tx = tx;
	}
	
	// 获取一个特性
	public String gtx(String txKey) {
		String tv = this.tx.getString(txKey);
		if(tv == null) {
			tv = "";
		}
		return tv;
	}
	// 是否包含一个特性
	public Boolean istx(String txKey) {
		Object tv = this.tx.get(txKey);
		if(tv == null || tv.equals("false")) {
			return false;
		}
		return true;
	}

	// key: 表示字段的标记 
	public static final String key_flag = "fo_type";		
	public String getFlag() {
		return gtx(key_flag);
	}
	public void setFlag(String value) {
		tx.set(key_flag, value);
	}

	
	
	// 写入link信息 
	public void setClickInfo(String str) {
		// 如果是空 
		if(str == null || str.equals("") || str.equals("false")) {
			return;
		}
		if(str.indexOf(".") == -1) {
			AjaxError.getAndThrow("请确保表[" + dt.getTableName() + "]字段[" + getColumnName() + "]"
					+ "正确声明了click信息(正确格式形如:click=sys_user.id)");
			return;
		}
		// 开始解析 
		String[] arr = str.split("\\.");
		this.tx.set("clickCatTable", arr[0]);
		this.tx.set("clickCatColumn", arr[1]);
		this.tx.set("click", str);
//		this.foType = "click";	// 改为link型
	}
	// 外键对应主键的表名，的模块，的接口名 
	public String getClickCatTableMkName() {
		return SUtil.wordEachBig(tx.getString("clickCatTable"));
	}
	// 外键对应主键的表名，的模块，的kebab-case形式 
	public String getClickCatTableKebabName() {
		String mkName = tx.getString("clickCatTable");
		String packageModules = mkName.toLowerCase();
		return SUtil.xia_2_zhong(packageModules);
//		return SUtil.wordEachBig(fkPkTableName);
	}
	// 外键对应主键的表名，的模块，的外键查询列 
	public String getClickCatKeyColumn() {
		if(type == 1) {
			return getFieldName();
		}
		if(type == 2) {
			return getFkSCurrDc().getFieldName();
		}
		return getFkSCurrDc().getFieldName();
	}

	
	

	// ---------------------------------------- 外键方面 ---------------------------------------- 


	// ---------------------- 连接外键相关
	
	// ---------- 当此列是一个fk-s时，对应的本表外键列 
	public DbColumn fkSCurrDc;
	public DbColumn getFkSCurrDc() {
		return fkSCurrDc;
	}
	public void setFkSCurrDc(DbColumn dc) {
		this.fkSCurrDc = dc;
	}
	// 连接外键相关
	// 返回该连接查询的sql (完全java代码构建)
	public String getT2Sql() {
//		System.out.println("----------------------------------------"+dt);
		String sql = "(";		// 样本: (select name from sys_type where id = ser_article.type_id) as sys_type_name, 
		sql += "select " + tx.get("catc") + " from " + tx.get("jt");
		sql += " where " + tx.get("jc") + " = " + dt.getTableName() + "." + tx.get("curr") + ")";
		sql += " as " + getColumnName();

		return sql;
	}
	

	// 返回此外键的jt的模块名称
	public String getJtMkName() {
		return SUtil.wordEachBig(tx.getString("jt"));
	}


	// ---------------------- 聚合外键相关
	
	// ---------- 构造sql的方法 
	// 剔除fk-前缀的类型
	public String foType2;
	public void setFoType2(String foType2) {
//		if(foType2.equals("?")) {
//			foType2 = "custom";
//		}
		this.foType2 = foType2;
	}
	public String getFoType2() {
		return foType2;
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
		asColumnName = tx.getString("jt") + "_" + getFoType2();
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
		return tx.getString("java-type");
	}
	
	/**
      * 返回该聚合查询的sql (完全java代码构建)
      */
	public String getT3Sql() {
		String sql = "(";		// 样本: (select count(*) from comment where sid = user.id and type = 1) as 评论数量 
		// 如果声明了 sql (完全自定义sql) 
		if(tx.isNotNull("sql")) {
			sql += tx.getString("sql");
		} else {
			sql += "select " + getFoType2() + "(" + tx.getString("ac", "*") + ") from " + tx.getString("jt");
			if(tx.getString("jt").equals(dt.getTableName())) {
				sql += " temp_t";	// 如果里外表为同一张表，则追加别名 
			}
			sql += " where ";
			// 如果声明了where 
			if(tx.isNotNull("where")) {
				sql += tx.getString("where");
			} else {	// 否则来拼接 
				sql += tx.getString("jc") + " = " + dt.getTableName() + "." + tx.getString("curr", dt.getPrimaryKey().getColumnName());
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
	

	
	
	@Override
	public String toString() {
		return "DbColumn [type=" + type + ", columnName=" + columnName + ", columnComment=" + columnComment
				+ ", columnType=" + columnType + ", fieldType=" + fieldType + ", jvList=" + jvList + ", showCount="
				+ showCount + ", foType=" + foType + ", tx=" + tx + "]";
	}
	
	
	
	

	
	


	
	
	
	
	
	
	
	
	
	
	
	
}
