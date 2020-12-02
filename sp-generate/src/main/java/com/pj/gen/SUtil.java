package com.pj.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.pj.gen.cfg.GenCfgManager;
import com.pj.gen.model.TheString;
import com.pj.gen.utils.SoMap;

/**
 * String处理工具类
 * @author kongyongshun
 *
 */
public class SUtil {


	// ------ 新
	public static final String str_notp = "--notp";		// 一个字段注释，包含了这个字符串，才不会被解析 
	
	
	
	// =====================  工具型方法  =========================   
	
	/**
	 * 该字符串是否为null或者空串
	 */
	public static boolean isNull(String str) {
		return (str == null || str.equals(""));
	}
	
	
	// 将指定单词首字母大写;
	public static String wordFirstBig(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	// 将指定单词首字母小写;
	public static String wordFirstSmall(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
	}
	
	// 去掉字符串第一个字符
	public static String strFirstDrop(String str) {
		try {
			return str.substring(1, str.length());
		} catch (StringIndexOutOfBoundsException e) {
			return str;
		}
	}
	
	
	// 去掉字符串最后一个字符
	public static String strLastDrop(String str) {
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
	
	

	
	// ---------------------------- 注释解析相关 ----------------------------

	// 将类似 "name=张三, age=18" 样式的字符串, 转换为SoMap(特性)
	public static SoMap parseStringToTxMap(String txStr) {
		SoMap soMap = new SoMap();
		if(txStr == null) {
			return soMap;
		}
		String[] arr = txStr.split(",");
		for (String str : arr) {
			try {
				str = str.trim();
				if(str.equals("")) {	// 如果是空
					
				}
				else if(str.indexOf("=") == -1) {	// 如果没有=
					soMap.put(str, "true");
				}
				else {	
					String[] darr = str.split("=");
					if(darr.length == 1) {
						soMap.put(darr[0], "true");
					} else {
						String value = darr[1];
						if(darr.length > 2) {
							for (int i = 2; i < darr.length; i++) {
								value += "=" + darr[i];
							}
						}
						// 去掉空格和左右括号 
						value = value.trim();
						if(value.startsWith("(")) {
							value = SUtil.strFirstDrop(value);
						}
						if(value.endsWith(")")) {
							value = SUtil.strLastDrop(value);
						}
						if(value.equals("")) {
							value = "true";
						}
						// 添加进map 
						soMap.set(darr[0], value);
					}
				}
			} catch (Exception e) {
				System.err.println("特性：" + str + "解析出错：" + e.getMessage());
			}
		}
		return soMap;
	}

	// 将类似 "[str x=xx]" 样式的字符串, 转换为SoMap(特性+fo_type)
	private static SoMap parseStringToFtMap(String ftStr) {
		try {
			// 解析特性str 
			SoMap so = new SoMap();
			String ftStr2 = ftStr.substring(1, ftStr.length()-1).trim();	// 去掉第一个和最后一个字符 
			// 如果包含空格
			if(ftStr2.indexOf(" ") > -1) {
				// 特性 
				int tx_index = ftStr2.indexOf(" ");
				String txStr = ftStr2.substring(tx_index + 1, ftStr2.length());	// 特性列表字符串 
				so.setMap(SUtil.parseStringToTxMap(txStr));
				// fo_type 
				so.setFoType(ftStr2.substring(0, tx_index));	
			} else {
				// 没有特性，直接写入
				so.setFoType(ftStr2);		// fo 类型 
			}

			// 返回 
			return so;
		} catch (Exception e) {
			System.err.println("字符串(" + ftStr + ")解析异常：" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	// 从一个字符串里"bala [str x=xx]"，获取一个SoMap (特性、fo_type、comment)
	public static SoMap getFt(String str) { 
		// 过滤掉换行符 
		str = str.replaceAll("\r","").replaceAll("\n","");	
		
		SoMap so = new SoMap();

		// 如果包含 非解析字符， 代表不要解析
		if(str.indexOf(SUtil.str_notp) > -1) {
			so.setComment(str.replace(SUtil.str_notp, ""));
			return so;
		}
		
		// 判断是否包含[] , 如果没有直接返回 
		if(str.indexOf("[") > -1 && str.indexOf("]") > -1) {
		} else {
			so.setComment(str);
			return so;
		}
		
		// 开始解析 
		int index_l = str.indexOf("[");
		int index_r = str.indexOf("]");
		String ftStr = str.substring(index_l, index_r + 1);
		// 去除表单声明信息 
		so = parseStringToFtMap(ftStr);
		so.setComment(str.replace(ftStr, "").trim());	// 去掉特殊声明后的注释 
		
		return so;
	}

	// 从一个字符串里"bala [str x=xx][str2 x=xx]"，获取多个SoMap 
	public static List<SoMap> getFtList(String str, TheString ts) {
			
			List<SoMap> ftList = new ArrayList<SoMap>();
			str = str.replaceAll("\r","").replaceAll("\n","");	// 过滤掉换行符 

			// 如果包含 非解析字符， 代表不要解析
			if(str.indexOf(SUtil.str_notp) > -1) {
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
					SoMap so = parseStringToFtMap(ftStr);
//					str = str.replace(ftStr, "").trim();	// 去除掉这个 
					str = str.substring(0, index_l) + str.substring(index_r + 1);			// 去除掉这个 
					ftList.add(so);
				} else {
					if(ts != null) {
						ts.str = str;
					}
					return ftList;
				}
			}
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

	// 指定字符串，根据驼峰是否下划线转驼峰 
	public static String getHumpByCfg(String str) {
		if(GenCfgManager.cfg.modelStyle == 2) {
			return SUtil.wordEachBig_fs(str);
		}
		return str;
	}
	
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
