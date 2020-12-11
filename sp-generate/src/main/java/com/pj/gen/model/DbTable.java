package com.pj.gen.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pj.gen.SUtil;
import com.pj.gen.cfg.GenCfgManager;
import com.pj.gen.utils.AjaxError;
import com.pj.gen.utils.SoMap;

/**
 * Model模型：一个表
 * @author kongyongshun
 *
 */
public class DbTable {

	
	// ---------------------------------------- 字段 ---------------------------------------- 
	
	// ---------- 表名字  
	private String tableName;					
	public void setTableName(String name) {
		this.tableName = name;
	}
	public String getTableName() {
		return tableName;
	}
	public String getTableNameSmall() {		// 返回表名，小写形式 
		return tableName.toLowerCase();
	}
	
	// ---------- 表注释  
	private String tableComment;		
	public String getTableComment() {
		if(this.tableComment == null) {
			return "";
		}
		return tableComment.trim();
	}	
	public void setTableComment(String tableComment) {	// 写入表注释 	
		
		
		// 解析所有类型 
		TheString ts = new TheString();
		this.ftList = SUtil.getFtList(tableComment, ts);
		this.tableComment = ts.str;
		
		// 开始整理
		for (SoMap ft : this.ftList) {
			
			// 如果是连接外键 
			if(ft.getFoType().equals("fk-s")) {
				// 如果声明了简写, 则从简写里解析必须的三个值 
				if(ft.containsKey("js")) {
					String js = ft.getString("js");
//					String[] arr = js.split("\\.");
					if(js.indexOf("=") == -1 || js.indexOf(".") == -1 ) {
						System.err.println("无法解析表(" + this.tableName + ")注释, fk-s的js简写模式格式应该是: curr=jt.jc");
					} 
//					ft.setDefaultValue("curr", arr[0]);
//					ft.setDefaultValue("jt", arr[1]);
//					ft.setDefaultValue("jc", arr[2]);
					String[] arr = js.split("=");
					String[] arr2 = arr[1].split("\\.");
					ft.setDefaultValue("curr", arr[0]);
					ft.setDefaultValue("jt", arr2[0]);
					ft.setDefaultValue("jc", arr2[1]);
				}
				// ----- 先验证, 是否包含了必须具有的值 
				if(ft.isContainNull("curr", "jt", "jc")) {
					AjaxError.getAndThrow("表(" + this.tableName + ")注释, fk-s外键必须curr、jt、jc三个值");
				}
				
				// 给本表外键主键，加上连接信息 
				DbColumn dcCurrFk = this.getDbColumnByName(ft.getString("curr"));
				AjaxError.throwByIsNull(dcCurrFk, "未能在本表找到列: " + ft.getString("curr"));
//				dcCurrFk.setIsCatLink(ft.getString("click", "true").equals("true"));
//				dcCurrFk.setFoType("fk-sn"); 	// 强制更改一下类型为fk-st 
//				dcCurrFk.tx.setDefaultValue("click", "true");
//				dcCurrFk.tx.setDefaultValue("drop", ft.get("drop", false)); 
//				dcCurrFk.setTxMap(ft);
				// 如果配置了下拉列表框，则默认以下几项
//				System.err.println("------------------"+ft.get("drop"));
				if(ft.isNotNull("drop")) {
					dcCurrFk.tx.setDefaultValue("no-s", "true");
					dcCurrFk.tx.setDefaultValue("no-show", "true");
					dcCurrFk.tx.setDefaultValue("no-add", "true");
//					dcCurrFk.tx.setDefaultValue("no-update", "true");
				}
				
				// 如果声明了catc和comment，则转移到show上 
				if(ft.containsKey("catc") && ft.containsKey("comment") ) {
					ft.setDefaultValue("show", ft.get("catc") + "." + ft.get("comment"));
				}
				if(ft.isNull("show")) {
					continue;
				}
				// 开始遍历要展示的列 
				String show = ft.getString("show");
				String[] arr = show.split("\\.");
				for (int i = 0; i < arr.length; i+=2) {
					// 越界检查
					if(i + 1 == arr.length) {
						break;
					}
					DbColumn dc = DbModelManager.manager.getDbColumn();
					dc.setDt(this);			// 表
					dc.setType(2);			// 2=连接外键类型 
					SoMap tx = SoMap.getSoMap(ft);
					dc.setTx(tx);		// 特性tx 
					tx.set("catc", arr[i]);	// 展示列
					tx.set("comment", arr[i + 1]);	// 展示列的注释 
					dc.setColumnName(tx.getString("as", tx.get("jt") + "_" + arr[i]));		// 最终的as列名字 
					dc.setColumnComment(tx.getString("comment"));	// 注释
					dc.setFkSCurrDc(dcCurrFk);			// 对应的本表外键列 
					dc.setFoType("fk-s"); 	// fo类型，强制为fk-s 
					// 写入连接信息， 如果是一个true，则代表是fk-s的简写模式 
					if(tx.getString("click", "").equals("true")) {
						dc.setClickInfo(ft.getString("jt") + "." + ft.getString("jc"));	// 连接信息 
					} else {
						dc.setClickInfo(tx.getString("click"));	// 连接信息 
					}
					// 一些默认值 
//					ft.setDefaultValue("click", "false");	// 是否点击连接
					tx.setDefaultValue("java-type", "String"); // 默认的java类型
					// 将这个fk-2列，追加到对应的主键后面 
					int index = this.columnList.indexOf(dcCurrFk) + dcCurrFk.showCount + 1;
					this.columnList.add(index, dc);
					dcCurrFk.showCount++;	// 统计一下 
				}
			}
			// 如果是五大聚合函数 
			else if(Arrays.asList("fk-count", "fk-max", "fk-min", "fk-sum", "fk-avg", "fk-?").contains(ft.getFoType())) {
				// 构建对象 
				DbColumn dc = DbModelManager.manager.getDbColumn();
				dc.setDt(this);	// 表
				dc.setType(3);	// 类型：3 
				dc.setFoType("fk-p"); 	// fo类型 
				dc.setFoType2(ft.getFoType().replace("fk-", "")); 	// 聚合外键类型 
				
				// 解析特性 
//				SoMap tx = ft.txMap;
				// 如果声明了简写 
				if(ft.getString("js") != null) {
					String jsInfo = ft.getString("js"); 
					String[] jsArr = jsInfo.split("\\."); 
					if(jsArr.length < 3) {
						System.err.println("无法解析表(" + this.tableName + ")注释, fk-?简写模式请至少提供三个值: " + jsInfo);
					} else {
						// 尝试写入但不覆盖 
						ft.setDefaultValue("jt", jsArr[0]);
						ft.setDefaultValue("jc", jsArr[1]);
						ft.setDefaultValue("comment", jsArr[2]);
					}
				}
//				System.out.println("--------------------------------df的特性：" + df.tx); 
				// ------------ 检查是否包含必要的条件 
				// 指定sql后，需要继续提供 as 或 jt jc 
				if(ft.isNotNull("sql")) {		
					if(ft.isNull("as") && ft.isContainNull("jt")) {
						AjaxError.getAndThrow("无法解析表(" + this.tableName + ")注释, 指定了sql后，必须提供as或jt");
					}
				}
				// 指定where后，需要继续提供 jt 
				else if(ft.isNotNull("where")) {
					if(ft.isNull("jt")) {
						AjaxError.getAndThrow("无法解析表(" + this.tableName + ")注释, 指定了where后，必须提供jt值 ");
					}
				}
				// 什么都没有指定，则必须有jt和jc 
				else if(ft.isContainNull("jt", "jc")) {
					AjaxError.getAndThrow("无法解析表(" + this.tableName + ")注释, 请至少提供jt、jc两个值 ");
				}
				// 所有情况都必须提供注释 
				if(ft.isNull("comment")) {
					AjaxError.getAndThrow("表(" + this.tableName + ")注释, 聚合外键必须指定comment值，否则无法给字段标注相应的注释");
				}
				// 写入特性 
				dc.tx = ft;
				// 一些默认值 
				dc.setColumnComment(ft.getString("comment"));	// 注释
				ft.setDefaultValue("java-type", "Long"); // 默认的java类型
				// 加入到列列表
				this.columnList.add(dc);
			}
			// tree树表
			else if(ft.getFoType().equals("tree") || ft.getFoType().equals("tree-lazy")) {
				this.tableType = "tree";
				DbColumn parentIdColumn = this.getDbColumnByName(ft.getString("fkey", "parent_id"));
				// 找到fkey列做上标记 
				if(parentIdColumn == null) {
					AjaxError.getAndThrow("表[" + this.tableName + "]的tree请声明，必须指定一个存在的parent_id列");
				}
				parentIdColumn.setFlag("tree-parent-id");
				ft.setDefaultValue("top", "-1");	// 标记：懒加载树形表格的count计数列 
				
				// 如果是懒加载的tree，继续追加逻辑 
				if(ft.getFoType().equals("tree-lazy")) {
					this.tableType = "tree-lazy";
					// 追加一个fk-count列, 用于计算[是否包含子级]列 
					// 构建对象 
					DbColumn dc = DbModelManager.manager.getDbColumn();
					dc.setDt(this);	// 表
					dc.setType(3);	// 类型：3 
					dc.setFoType("fk-p"); 	// fo类型 
					dc.setFoType2("count"); 	// 聚合外键类型 
					
					dc.setColumnComment("子级数量");	// 注释
					dc.tx.setDefaultValue("jt", this.getTableName());
					dc.tx.setDefaultValue("jc", ft.getString("fkey", "parent_id"));
					dc.tx.setDefaultValue("comment", "子级数量");
					dc.tx.setDefaultValue("java-type", "Long"); 	// 默认的java类型
					dc.setFlag("tree-lazy-children-count");	// 标记：懒加载树形表格的count计数列 
					
					// 加入到列列表
					this.columnList.add(dc);
				}
			}
			// 默认, 普通 
			else {
				this.tableType = "table"; 
			}
		}
		
//		this.tableComment = tableComment;
	}

	// ---------- 模块名(对表名二次处理一下)  [mkName] 【只读】
	public String getMkName(){
		return this.getTableName();
	}
	public String getMkNameBig(){	// 返回模块名 驼峰大写形式  [mkNameBig] 
		return SUtil.wordEachBig(getMkName());
	}

	// ---------- 实体类名(模块名下划线转大驼峰) [modelName]【只读】  
	public String getModelName(){
		return getMkNameBig();
	}

	// ---------- 变量名，(实体类名首字母小写)  [varName]【只读】
	public String getVarName() {
		// return getClassName().substring(0, 1).toLowerCase();
		return SUtil.wordFirstSmall(getMkNameBig());
	}
	public String getVarNameSimple() {	// 变量名的简写模式  (只要实体类名首字母)  [varNameSimple] 
		return getModelName().substring(0, 1).toLowerCase();
	}
	public String getModelVarName() {		// 返回模块实体类变量名  [modelVarName] 
		return SUtil.wordFirstSmall(getModelName());
	}
	
	// ---------- 模块包名(模块名所有字母小写) [packageModules] 【只读】
	public String getPackageModules() {
		return getMkName().toLowerCase().replaceAll("_", GenCfgManager.cfg.packageUnderlineTo);
	}
	public String getPackagePath() {		// 完全限定名包名 [packagePath] 
		return GenCfgManager.cfg.packagePath + "." + getPackageModules();
	}

	// ---------- 转换成kebab-case形式 [kebabName] 【只读】
	public String getKebabName() {
		return SUtil.xia_2_zhong(getMkName());
	}
	
	// ---------- 主键列  
	private DbColumn primaryKey;				
	public DbColumn getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(DbColumn primaryKey) {
		this.primaryKey = primaryKey;
	}

	// ---------- 表类型 
	private String tableType;					
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getTableType() {
		return tableType;
	}

	
	

	// ---------------------------------------- 方法 ---------------------------------------- 
	

	// ---------- 列集合 [columnList]
	public List<DbColumn> columnList;	
	public List<DbColumn> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<DbColumn> columnList) {
		this.columnList = columnList;
	}

	// 返回列集合，根据指定type  
	private List<DbColumn> getColumnListByType(int type) {
		List<DbColumn> list = new ArrayList<DbColumn>();	
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.type == type) {
				list.add(dbColumn);
			}
		}
		return list;
	}
	// 返回所有t1列集合  [普通列]
	public List<DbColumn> getT1List() {
		return getColumnListByType(1);
	}
	// 返回所有t2列集合  [连接外键]
	public List<DbColumn> getT2List() {
		return getColumnListByType(2);
	}
	// 返回所有t3列集合 [聚合外键]
	public List<DbColumn> getT3List() {
		return getColumnListByType(3);
	}
	// 返回所有t12列集合 [外键] 
	public List<DbColumn> getT12List() {
		List<DbColumn> list = new ArrayList<DbColumn>();	
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.type == 1 || dbColumn.type == 2) {
				list.add(dbColumn);
			}
		}
		return list;
	}
	// 返回所有t23列集合 [外键] 
	public List<DbColumn> getT23List() {
		List<DbColumn> list = new ArrayList<DbColumn>();	
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.type == 2 || dbColumn.type == 3) {
				list.add(dbColumn);
			}
		}
		return list;
	}
	// 返回所有t2列集合中特性含有drop的
	public List<DbColumn> getT2DropList() {
		List<DbColumn> list = new ArrayList<DbColumn>();	
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.type == 2 && dbColumn.istx("drop")) {
				list.add(dbColumn);
			}
		}
		return list;
	}
	// 返回所有集合
	public List<DbColumn> getTallList() {
		return columnList;
	}
		
	// 返回所有t1列中 - 所有不需要add/update的列
	public List<DbColumn> getT1ListByNotAdd() {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : getT1List()) {
			if(c.getFoType().equals("date-create") || c.getFoType().equals("date-update")) {
				list.add(c);
			}
		}
		return list;
	}
	// 返回所有列中 - 需要加入排序字段的 
	public List<DbColumn> getTallListBySort() {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : getTallList()) {
			if(c.istx("no-sort") || c.isFoType("time", "img", "audio", "video", "file", "img-list", "audio-list", "video-list", "file-list", "img-video-list", 
					"link", "richtext", "textarea", "textarea", "fk-s", "logic-delete")) {
				// 不添加 
			} else {
				list.add(c);
			}
		}
		return list;
	}
		
	// 返回列集合 - 指定foType的 
	public List<DbColumn> getTallListByFoType(String ...foType) {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : getTallList()) {
			for (String f : foType) {
				if(c.getFoType().equals(f)) {
					list.add(c);
				}
			}
		}
		return list;
	}

	// 返回列集合 - 含有指定特性的 
	public List<DbColumn> getTallListByTxKey(String ...txKeys) {
		List<DbColumn> list = new ArrayList<DbColumn>();
		for (DbColumn c : getTallList()) {
			for (String txKey : txKeys) {
				if(c.istx(txKey)) {
					list.add(c);
				}
			}
		}
		return list;
	}
	

	// ---------- 额外方法 
	
	// 返回所有t1列的列名集合 
	public List<String> getT1ListNameList() {	
		List<String> list = new ArrayList<>();
		for (DbColumn column : getT1List()) {
			list.add(column.getColumnName());
		}
		return list;
	}
	// 此表所有字段中是否包含指定表单类型 
	public boolean hasFo(String ... foType) {
		boolean flag = false;
		for (String ft : foType) {
			for (DbColumn dbColumn : getColumnList()) {
				if(dbColumn.getFoType().equals(ft)) {
					flag = true;
				}
			}
		}
		return flag;
	}
	// 以逗号拼接这个表的所有t1列，形如：a, b, c
	public String getT1ListCatString() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			String columnName = t1.get(i).getColumnName();
			str += columnName;	
			if(i != t1.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}
	// 以逗号拼接这个表的所有t1列，(带引号的), 形如："a", "b", "c"
	public String getT1ListCatString2() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			str += "\"" + t1.get(i).getColumnName() + "\"";	
			if(i != t1.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}
	// 以逗号拼接这个表的所有t1列3，(带引号的)(根据判断是否下划线转驼峰)
	public String getT1ListCatString3() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			String column = t1.get(i).getColumnName();
			if(GenCfgManager.cfg.getModelStyle() == 2){
				column = SUtil.wordEachBig_fs(column);
			}
			str += "\"" + column + "\"";
			if(i != t1.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}
	// 以逗号拼接这个表的所有t1列, 或* (由配置决定)
	public String getT1ListCatStringOrStar() {
		if(GenCfgManager.cfg.sqlSelectColumnWay == 1) {
			return "*";
		} 
		if(GenCfgManager.cfg.sqlSelectColumnWay == 2) {
			return getT1ListCatString();
		} 
		return "";
	}
	
	
	// 在mapper.xml的insert时，组织所有列，形如："a", "b", "c"
	public String getT1List_ByMapperInsertColumn() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			String columnName = t1.get(i).getColumnName();
			if(GenCfgManager.cfg.sqlEnclose == 1) {
				columnName = "`" + columnName + "`";
			}
			str += columnName;	
			if(i != t1.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}
	// 在mapper.xml的insert时，组织所有列值，形如：#{a}, #{b}, #{c}
	public String getT1List_ByMapperInsertValues() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			DbColumn dc = t1.get(i);
			String value = "#{" + dc.getFieldName() + "}";
			// 如果是创建时间或者更新时间
			if(t1.get(i).isFoType("date-create", "date-update")) {
				value = "now()";
			}
			// 如果是逻辑删除标识
			if(t1.get(i).isFoType("logic-delete")) {
				value = dc.tx.getString("yes");
			}
			str += value;	
			if(i != t1.size() - 1) {
				str += ", ";
			}
		}
		return str;
	}
	// 在mapper.xml的insert时，组织所有列值，形如：a=#{a}, b=#{b}, c=#{c}
	public String getT1List_ByMapperUpdateSet() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			DbColumn dc = t1.get(i);
			String column = dc.getColumnName();
			String value = "#{" + dc.getFieldName() + "}";
			// 如果配置了`
			if(GenCfgManager.cfg.sqlEnclose == 1) {
				column = "`" + column + "`";
			}
			// 如果是更新时间
			if(dc.isFoType("date-update")) {
				value = "now()";
			}
			String row = "\t\t" + column + " = " + value + "";
			// 如果是创建时间或者更新时间
			if(t1.get(i).isFoType("date-create")) {
				continue;
			}
			str += row;	
			str += ", \r\n";
//			if(i != t1.size() - 1) {
//				str += ", \r\n";
//			}
		}
		// 截取掉最后几个字符 
		if(str.length() > 5) {
			str = str.substring(0, str.length() - 4);
		}
		return str;
	}
	
	// 在mapper.xml的getList时，组织所有查询条件，形如：<if test=' this.isNotNull("id") '> and id = #{id} </if>
	public String getT1List_ByMapperGetListWhere() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			DbColumn c = t1.get(i);
			if(c.isFoType("date", "date-create", "date-update", "time", "img", "img-list", "audio", "audio-list", "video", "video-list", "file", "file-list")) {
				continue;
			}
			// 声明字段
			String columnName = c.getColumnName();	// 列名 
			String fieldName = c.getFieldName();	// 字段名
			String tj = "=";	// 条件 
			String tjV = "#{" + fieldName + "}";
			// 一些特殊情况
			if(c.tx.getString("j", "").equals("like")) {
				tj = "like"; 
				tjV = "concat('%', #{" + c.getFieldName() + "}, '%')"; 
			}
			// 拼接 
			str += "\t\t\t<if test=' this.has(\"" + fieldName + "\") '> and `" + columnName + "` " + tj + " " + tjV + " </if>\r\n"; 
		}
//		str = "\t\t<where>\r\n" + str + "\t\t</where>\r\n";
		return str;
	}
	// 在mapper.xml的getList时，组织所有查询条件，形如：<when test='sortType == 1'> id desc </when> 
	public String getT1List_ByMapperGetListSort() {
		List<DbColumn> list = getTallListBySort();
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			DbColumn c = list.get(i);
			// 拼接 
			str += "\t\t\t<when test='sortType == " + (i + 1) + "'> `" + c.getColumnName() + "` desc </when>\r\n"; 
		}
		// 默认的
		String otherwise = " `" + this.primaryKey.getAsColumnName() + "` desc ";
		if(this.hasFt("tree")) {
			otherwise = " `" + this.primaryKey.getAsColumnName() + "` asc ";
		}
		str = str + "\t\t\t<otherwise>" + otherwise + "</otherwise>\r\n";
		return str;
	}
	
	// 在XxxUtil.java的check方法时，组织所有验证条件，形如：AjaxError.throwByIsNull(s.id, "记录id不能为空");
	public String getT1List_ByUtilCheck() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			DbColumn c = t1.get(i);
			String str2 = "";
			// 声明字段
			String fieldName = "get" + c.getGetset() + "()";	// 字段名
			String columnComment = c.getColumnComment3();	// 字段名
			if(c.isFoType("logic-delete")) {
				continue;
			}
			// 一些特殊情况
			// 如果是public 
			if(GenCfgManager.cfg.modelVisitWay == 2) {
				fieldName = c.getFieldName();	// 字段名
			}
			// 拼接字符串 
			str2 += "\t\tAjaxError.throwByIsNull(" + getVarNameSimple() + "." + fieldName + ", \"[" + columnComment + "] 不能为空\");"; 
			// 拼接注释 
			if(GenCfgManager.cfg.utilDocWay == 1) {
				str2 += "\t\t// 验证: " + c.getColumnComment() + " ";
			}
			if(GenCfgManager.cfg.utilDocWay == 2) {
				str2 = "\t\t// 验证: " + c.getColumnComment() + " \r\n" + str2;
			}
			str += str2 + "\r\n";
		}
		return str;
	}
	// 在XxxUtil.java的check方法时，组织所有验证条件，形如：s.id = 0;		// 记录id 
	public String getT1List_ByUtilGetModel() {
		List<DbColumn> t1 = getT1List();
		String str = "";
		for (int i = 0; i < t1.size(); i++) {
			DbColumn c = t1.get(i);
			String str2 = "";
//			if(Arrays.asList("String", "long", "int").contains(c.getFieldType()) == false) {
//				continue;
//			}
			if(c.isFoType("logic-delete")) {
				continue;
			}
			// 如果是priavate 
			if(GenCfgManager.cfg.modelVisitWay == 1) {
				str2 += "\t\t" + getVarNameSimple() + ".set" + c.getGetset() + "(" + c.getDefaultValue() + ");";
			}
			if(GenCfgManager.cfg.modelVisitWay == 2) {
				str2 += "\t\t" + getVarNameSimple() + "." + c.getFieldName() + " = " + c.getDefaultValue() + ";";
			}
			// 拼接注释 
			if(GenCfgManager.cfg.utilDocWay == 1) {
				str2 += "\t\t// " + c.getColumnComment() + " ";
			}
			if(GenCfgManager.cfg.utilDocWay == 2) {
				str2 = "\t\t// " + c.getColumnComment() + " \r\n" + str2;
			}
			str += str2 + "\r\n";
		}
		return str;
	}
	
	
	

	// ---------- IO输出相关 
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

	
	// ---------- 表自身相关 
	// ft列表 
	private List<SoMap> ftList;		
	// 返回这个表的 默认ft
	public SoMap getDefFt() {
		for (SoMap ft : ftList) {
			if(ft.getFoType().equals("table")) {
				return ft;
			}
		}
		// 如果没有，则返回默认的
		return SoMap.getSoMap().setFoType("table");
	}
	// 返回后台管理的菜单icon
	public String getIcon() {
		return getDefFt().getString("icon", GenCfgManager.cfg.getDefaultMeunIcon());
	}
	
	// 是否含有指定ft
	public boolean hasFt(String... foType) {
		for (SoMap ft : ftList) {
			for (String fType : foType) {
				if(ft.getFoType().equals(fType)) {
					return true;
				}
			}
		}
		return false;
	}
	// 获取指定ft
	public SoMap getFt(String... foType) {
		for (SoMap ft : ftList) {
			for (String fType : foType) {
				if(ft.getFoType().equals(fType)) {
					return ft;
				}
			}
		}
		return null;
	}
	
	// 是个tree时，其idkey 
	public String getTreeIdkey() {
		String value = getFt("tree", "tree-lazy").getString("idkey", this.primaryKey.getFieldName());
		return SUtil.getHumpByCfg(value);
	}
	// 是个tree时，其fkey 
	public String getTreeFkey() {
		String value = getFt("tree", "tree-lazy").getString("fkey", "parent_id");
		return SUtil.getHumpByCfg(value);
	}
	// 是个tree时，其ckey 
	public String getTreeCkey() {
		String value = getFt("tree", "tree-lazy").getString("ckey", "children");
		return SUtil.getHumpByCfg(value);
	}
	

	// 返回指定列，根据列名字
	public DbColumn getDbColumnByName(String columnName) {
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.getColumnName().equals(columnName)) {
				return dbColumn;
			}
		}
		return null;
	}

	// 返回指定列，根据列标记
	public DbColumn getDbColumnByFalg(String falg) {
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.getFlag().equals(falg)) {
				return dbColumn;
			}
		}
		return null;
	}
	// 返回指定列，根据foType的 
	public DbColumn getDbColumnByFoType(String foType) {
		for (DbColumn dbColumn : columnList) {
			if(dbColumn.getFoType().equals(foType)) {
				return dbColumn;
			}
		}
		return null;
	}


	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "DbTable [tableName=" + tableName + ", tableComment=" + tableComment + ", primaryKey=" + primaryKey
				+ ", tableType=" + tableType + ", columnList=" + columnList + ", ftList=" + ftList + "]";
	}
	

	



	



	

	
	
}
