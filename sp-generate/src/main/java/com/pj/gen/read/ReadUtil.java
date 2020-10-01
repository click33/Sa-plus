package com.pj.gen.read;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fly.jdbc.SqlFly;
import com.pj.gen.SUtil;
import com.pj.gen.model.DbFk;
import com.pj.gen.model.DbTable;
import com.pj.gen.model.Ft;
import com.pj.gen.model.TheString;
import com.pj.gen.utils.SoMap;

/**
 * 从数据库里拿指定信息
 * @author kongyongshun
 *
 */
public class ReadUtil {

	
	

	// 获取Connection中所有表的名字
	public static List<String> getTableList(Connection connection) {
		List<String> tables = new ArrayList<String>();
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}
	
	
	// 返回所有表的表注释
	public static Map<String, String> getTcMap(SqlFly sqlFly) {
		Map<String, String> map = new HashMap<>();
		try {
			String sql = "show table status";
			ResultSet rs = sqlFly.getResultSet(sql);
			while(rs.next()){
				map.put(rs.getString("Name"), rs.getString("Comment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	// 返回Connection中指定表中的主键列,无主键的返回"id"
	public static String getPkColumn(Connection connection, String tableName) {
		String pk = "id";	// 默认就叫id吧
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			ResultSet rs = dbmd.getPrimaryKeys(null, null, tableName);
			if (rs.next()) {
				pk = rs.getString("COLUMN_NAME");
			}
			return pk;
		} catch (Exception e) {
			System.out.println("\t表" + tableName + "读取主键失败");
		}
		return pk;
	}
	
	
	// 返回指定表的全部字段JDBC建议类型 
	public static Map<String, String> getJtMap(SqlFly sqlFly, String tableName) {
		Map<String, String> map = new HashMap<>();
		try {
			String sql = "select * from " + tableName + " where 1=0";
			ResultSetMetaData rsmd = sqlFly.getResultSet(sql).getMetaData();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String propName = rsmd.getColumnName(i + 1); // 列名
				String javaType = JDBC2JT(rsmd.getColumnClassName(i + 1)); // 类型
				map.put(propName, javaType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	// 返回JDBC建议类型转换后的类型
	public static String JDBC2JT(String cType) {
		if (cType.equals("java.lang.Integer")) {
			return "int";
		} else if (cType.equals("java.sql.Timestamp") || cType.equals("java.sql.Date")) {
//			return "Date";
			return "String";
		} else if (cType.equals("java.lang.Double")) {
			return "double";
		} else if (cType.equals("java.lang.Long")) {
			return "long";
		} else {
			return "String";
		}
	}
	
	

	// 将一个表中，所有重复的外键名重命名一下，按照数字依次递增 
	public static void f5TableFkName(DbTable table) {
		List<String> strList = new ArrayList<String>();
		// 整理fk-12外键
		for (DbFk fk : table.getAllDbFk_12()) {
			// 如果含有 
			String columnName = fk.getColumnName();
			if(strList.contains(columnName)) {
				for (int i = 2; i < 1000; i++) {	// 1000应该足够了 
					String str = fk.getColumnName() + i;
					if(strList.contains(str) == false) {
						columnName = str;
						break;
					}
				}
			}
			strList.add(columnName);
			fk.setColumnName(columnName);
		}
		// 整理聚合外键
		for (DbFk fk : table.getAllDbFk_jh()) {
			// 如果含有 
			String columnName = fk.getAsColumnName();
			if(strList.contains(columnName)) {
				for (int i = 2; i < 1000; i++) {	// 1000应该足够了 
					String str = fk.getAsColumnName() + i;
					if(strList.contains(str) == false) {
						columnName = str;
						break;
					}
				}
			}
			strList.add(columnName);
			fk.setColumnName(columnName);
		}
	}

	
	// ---------------------------- 注释解析相关 ----------------------------

	// 将类似 name=张三, age=18 样式的字符串, 转换为map
	public static SoMap parseStringToTxMap(String txStr) {
		SoMap map = new SoMap();
		if(txStr == null) {
			return map;
		}
		String[] arr = txStr.split(",");
		for (String str : arr) {
			try {
				str = str.trim();
				if(str.equals("")) {	// 如果是空
					
				}
				else if(str.indexOf("=") == -1) {	// 如果没有=
					map.put(str, "");;
				}
				else {	
					String[] darr = str.split("=");
					if(darr.length == 1) {
						map.put(darr[0], "");
					} else {
						String value = darr[1];
						if(darr.length > 2) {
							for (int i = 2; i < darr.length; i++) {
								value += "=" + darr[i];
							}
						}
						value = value.trim();
						if(value.startsWith("(")) {
							value = SUtil.strFirstDrop(value);
						}
						if(value.endsWith(")")) {
							value = SUtil.strLastDrop(value);
						}
						map.put(darr[0], value);
					}
				}
			} catch (Exception e) {
				System.err.println("特性：" + str + "解析出错：" + e.getMessage());
			}
		}
		return map.deleteThis();
	}
	
	// 将一个字符串(例: [str])，解析成一个 Ft对象 
	private static Ft parseStringToFt(String ftStr) {
		try {
			// 过滤掉换行符 
			ftStr = ftStr.replaceAll("\r","").replaceAll("\n","");

			// 声明对象 
			Ft ft = new Ft();
			String type = "";		// 字段类型 
			String txStr = "";		// 特性Map
			
			// 解析特性str   (如果包含空格)
			String comment2 = ftStr.substring(1, ftStr.length()-1).trim();	// 去掉第一个和最后一个字符 
			if(comment2.indexOf(" ") > -1) {
				int tx_index = comment2.indexOf(" ");
				txStr = comment2.substring(tx_index + 1, comment2.length());	// 特性列表字符串 
				type = comment2.substring(0, tx_index);
			} else {
				type = comment2;
			}

			// 赋值 
			ft.type = type;
			ft.txMap = ReadUtil.parseStringToTxMap(txStr);
			return ft;
		} catch (Exception e) {
			System.err.println("字符串(" + ftStr + ")解析异常：" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	// 从一个字符串里，获取一个Ft
	public static Ft getFt(String str) { 
		
		Ft ft = new Ft();
		str = str.replaceAll("\r","").replaceAll("\n","");	// 过滤掉换行符 

		// 如果包含--notp， 代表不要解析
		if(str.indexOf("--notp") > -1) {
			ft.comment = str.replace("--notp", "");
			return ft;
		}
		
		// 判断是否包含[] , 如果没有直接返回 
		if(str.indexOf("[") > -1 && str.indexOf("]") > -1) {
		} else {
			ft.comment = str;
			return ft;
		}
		
		// 开始解析 
		int index_l = str.indexOf("[");
		int index_r = str.indexOf("]");
		String ftStr = str.substring(index_l, index_r + 1);
		// 去除表单声明信息 
		ft = parseStringToFt(ftStr);
		ft.comment = str.replace(ftStr, "").trim();	// 去掉特殊声明后的注释 
		
		return ft;
	}
	
	// 从一个字符串里，获取多个Ft 
	public static List<Ft> getFtList(String str, TheString ts) {
		
		List<Ft> ftList = new ArrayList<Ft>();
		str = str.replaceAll("\r","").replaceAll("\n","");	// 过滤掉换行符 

		// 如果包含--notp， 代表不要解析
		if(str.indexOf("--notp") > -1) {
			return ftList;
		}
		
		// 一直解析 
		for (;;) {
			// 判断是否包含[] , 如果没有直接返回 
			if(str.indexOf("[") > -1 && str.indexOf("]") > -1) {
				// 开始解析 
				int index_l = str.indexOf("[");
				int index_r = str.indexOf("]");
				String ftStr = str.substring(index_l, index_r + 1);
				Ft ft = parseStringToFt(ftStr);
//				str = str.replace(ftStr, "").trim();	// 去除掉这个 
				str = str.substring(0, index_l) + str.substring(index_r + 1);			// 去除掉这个 
				ftList.add(ft);
			} else {
				if(ts != null) {
					ts.str = str;
				}
				return ftList;
			}
		}
	}
	
//	public static void main(String[] args) {
//		String str = "ab[aa][aa]cdefg";
//		int index_l = str.indexOf("[");
//		int index_r = str.indexOf("]");
//		String ftStr = str.substring(index_l, index_r + 1);
//		Ft ft = parseStringToFt(ftStr);
////		str = str.replace(ftStr, "").trim();	// 去除掉这个 
//		str = str.substring(0, index_l) + str.substring(index_r + 1);			// 去除掉这个 
//		System.out.println(str);
//	}
	
	
//	public static void main(String[] args) {
//		System.out.println("文章详情 [f t=radio] []  ");		// 
//		for (Ft ft : getFtList("文章详情 [tree] [fk-count t=radio] []  ")) {
//			System.out.println(ft);
//		}
//	}
	
	
	
	
	
	
	
	
	
	
}
