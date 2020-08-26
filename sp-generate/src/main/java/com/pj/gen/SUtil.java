package com.pj.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.pj.gen.utils.SoMap;

/**
 * String处理工具类
 * @author kongyongshun
 *
 */
public class SUtil {


	// 防止 一次有变，到处乱改
	//static String path_fly_jdbc = "com.fly.jdbc";

//	static String fly = "Fly";
//	static String fly_run = "FlyRun";
//	static String fly_code = "FlyCode";
//	static String sql_fly = "SqlFly";
//	static String fly_factory = "FlyFactory";
//	
//	static String path_fly_util = "com.fly.util";
	

	static String package_fly = "com.fly.jdbc";				// 框架 总包地址
	static String package_Page = package_fly + ".pageing";	// Page 包地址   
	static String package_AjaxJson = "com.pj.utils";		// AjaxJson 包地址   
	
	static String class_SqlFly = "SqlFly";			// SqlFly 类名 
	static String class_SqlFlyFactory = "SqlFlyFactory";		// SqlFlyFactory 类名 
	static String class_FlyUtil = "FlyUtil";		//FlyUtil 类名
	static String class_Page = "Page";				//Page 类名   
	static String class_SAP = "SAP";				//SAP 类名   
	static String class_AjaxJson = "AjaxJson";		// AjaxJson 类名   

	static String import_sqlfly = "import " + package_fly + ".*;";	// sqlfly 本尊导入  
	static String import_Page = "import " + package_Page + "." + class_Page + ";";	// Page 导入  
	
	
	static String method_add = "add";		// 函数名 增
	static String method_delete = "delete";		// 函数名 删
	static String method_update = "update";		// 函数名 改
	static String method_getById = "getById";		// 函数名 查 
	static String method_getList = "getList";		// 函数名 查 - 集合 
	
	
	
	
	// =====================  工具型方法  =========================   
	
	// 将指定单词首字母大写;
	static String wordFirstBig(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	// 将指定单词首字母小写;
	public static String wordFirstSmall(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
	}
	
	// 去掉字符串最后一个字符
	static String strLastDrop(String str) {
		try {
			return str.substring(0, str.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			return str;
		}
	}
	
	// 去掉字符串最后x个字符
	static String strLastDrop(String str, int x) {
		try {
			return str.substring(0, str.length() - x);
		} catch (StringIndexOutOfBoundsException e) {
			return str;
		}
	}
	
	// 单词大小写转换
	// way=方式(1转小写 2转大写，其它不变)
	static String wordChangeBigSmall(String str, int way) {
		if (way == 1) {
			str = str.toLowerCase();
		} else if (way == 2) {
			str = str.toUpperCase();
		}
		return str;
	}
	
	// 快速组织普通方法注释
	static String getNotes(String str) {
		return "\t// " + str + " \r\n";
	}
	
	// 快速组织文档注释,三行,一缩进
	static String getDoc(String str) {
		return "\t/**\r\n\t * " + str + " \r\n\t */\r\n";
	}
	
	// 指定字符串的getter形式
	public static String getGetSet(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		if (str.length() == 1 || str.charAt(1) == '_' || str.charAt(1) == '$') {
			return wordFirstBig(str);
		}
		if (Character.isLowerCase(str.charAt(0)) && Character.isLowerCase(str.charAt(1))) {
			return wordFirstBig(str);
		} else {
			return str;
		}
	}
	
	// 指定字符串的字符串下划线转大写模式
	public static String wordEachBig(String str){
		String newStr = "";
		for (String s : str.split("_")) {
			newStr += wordFirstBig(s);
		}
		return newStr;
	}
	// 返回下划线转小驼峰形式
	public static String wordEachBig_fs(String str){
		return wordFirstSmall(wordEachBig(str));
	}
	
//	public static void main(String[] args) {
//		System.out.println();
//	}
	
	
	// 将类似 name=张三, age=18 样式的字符串, 转换为map
	public static SoMap txStringToMap(String tx_str) {
		SoMap map = new SoMap();
		if(tx_str == null) {
			return map;
		}
		String[] arr = tx_str.split(",");
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
						map.put(darr[0], darr[1]);
					}
				}
			} catch (Exception e) {
				System.err.println("特性：" + str + "解析出错：" + e.getMessage());
			}
		}
		return map;
	}
	
//	public static void main(String[] args) {
////		System.out.println(txStringToMap("name=1, bas= 21, kjsa, ss=,"));
//		System.out.println(txStringToMap(null));
//	}
	
	// 下划线转中划线
	public static String xia_2_zhong(String str) {
		return str.replaceAll("_", "-");
	}
	
	// 获取字符串，true返回第一个，false返回第2个  
	public static String getString(boolean bo, String s1, String s2) {
		return bo ? s1 : s2;
	}
	
	// 获取toString的doc
	public static String get_doc_toString() {
		return "\r\n\t/* (non-Javadoc)\r\n\t * @see java.lang.Object#toString()\r\n\t */\r\n\t@Override\r\n";
	}
	
	// 获取指定字段的 gettet 方法 
	// 字段名、注释 
//	public static String get_getMethod(DbColumn column) {
//		String getMethod = SUtil.getDoc("@return " + column.comment);
//		getMethod += "\tpublic " + column.javaType + " get" + SUtil.getSetGet(column.name) + 
//				"(){\r\n\t\treturn " + column.name + ";\r\n\t}";
//		return getMethod;
//	}
	
	// 获取指定字段的 settet 方法 
	// 字段名、注释 
//	public static String get_setMethod(DbColumn column, String class_name) {
//		String setMethod = SUtil.getDoc("@param " + column.name + " " + column.comment);
//		setMethod += "\tpublic " + class_name + " set" + SUtil.getSetGet(column.name) + 
//				"(" + column.javaType + " " + column.name + ") {\r\n\t\tthis." + column.name + 
//				" = " + column.name + ";\r\n\t\treturn this;\r\n\t}";
//		return setMethod;
//	}
	
	// 获取SO的getPage方法代码
	public static String get_getPage() {
		String str = 
				"\tpublic Page getPage() {\r\n" + 
				"\t\tif(this.page == null){\r\n" + 
				"\t\t\tthis.page = Page.getPage(this.pageNo, this.pageSize);\r\n" + 
				"\t\t}\r\n" + 
				"\t\treturn this.page;\r\n" + 
				"\t}";
		return str;
	}

	// 获取SO的getSortString方法代码
	public static String get_getSortString() {
		String str = "\tpublic String getSortString(){\r\n" + 
				"\t\treturn \" order by \" + arr[this.sort_type];\r\n" + 
				"\t}\r\n";
		return str;
	}
	
	// 获取getSqlFly()的代码
	public static String get_getSqlFly() {
		String getfly = "\t// 底层SqlFly对象\r\n\tprivate " + SUtil.class_SqlFly 
				+ " getSqlFly() {\r\n\t\treturn " + SUtil.class_SqlFlyFactory 
				+ ".getSqlFly();\r\n\t}\r\n\r\n";
		return getfly;
	}
	
	
	// =====================  代码doc相关 markdown  =========================   
	public static String fzDoc(String title, String api, String args_str, String return_str) {
		String str = "--- \r\n";
		str += "### " + title + "\r\n";
		str += "- 接口 \r\n```\r\n\t" + api + "\r\n```\r\n";
		str += "- 参数\r\n```\r\n" + args_str + "```\r\n";
		str += "- 返回\r\n```\r\n" + return_str + "```\r\n";
		str += "\r\n\r\n";
		return str;
	}
	
	
	
	
	
	
	// =====================  业务方法  =========================   

	// 输出指定字符串
	static void print(String str){
		System.out.print(str);
	}
	
	// 指定地址，写入指定内容
	static void outFile(String filePath, String txt){
		File file = new File(filePath);
		File fileDir = new File(file.getParent());
		if(fileDir.exists() == false){
			new File(file.getParent()).mkdirs();
		}
		try {
			file.createNewFile();
			Writer fw = new FileWriter(file.getAbsolutePath());
			fw.write(txt);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 指定包的Spring工厂类
	public static String SpringBeanFC(String projectPath, String packagePath, String fcName){
		File wjj = new File(projectPath, packagePath.replace(".", "\\")); // 创建路径
		String[] classNameArray = wjj.list();
		
		String _package = "package " + packagePath + ";\r\n\r\n";
		String _import = "\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\n";
		_import += "import org.springframework.stereotype.Component;\r\n\r\n";
		String fc = _package + _import + "/**\r\n* 工厂类\r\n*/\r\n@Component\r\n" + "public class " + fcName + "{\r\n\r\n\r\n"; // 工厂类
		
		for (String className : classNameArray) {
			try{
				if(className.indexOf(".java")==-1){
					continue;
				}
				className = className.replace(".java","");
				String Xxx = wordFirstBig(className);	//大写形式
				String xXX = wordFirstSmall(className);	//小写形式
				fc += "\t/**  */\r\n";
				fc += "\tpublic static "+className+" "+xXX+";\r\n";
				fc += "\t@Autowired\r\n";
				fc += "\tpublic void set"+Xxx+"("+Xxx+" "+xXX+") {\r\n";
				fc += "\t\t" + fcName + "."+xXX+" = "+xXX+";\r\n";
				fc += "\t}\r\n\r\n\r\n";
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		fc += "}";
		
		return fc;
	}
	
	// 生成 FC指定一个类型的代码注入体 
	public static String getFCone(String className, String comment) {
		String varName = wordFirstSmall(className);
		String str = 
				"\t/** " + comment + " */\r\n" + 
				"\tpublic static " + className + " " + varName + ";\r\n" + 
				"\t@Autowired\r\n" + 
				"\tpublic void set" + className + "(" + className + " " + varName + ") {\r\n" + 
				"\t\tFC." + varName + " = " + varName + ";\r\n" + 
				"\t}\r\n";
		return str;
	}
	
	
	
	
	
	
	
	
	
	
}
