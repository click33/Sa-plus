package com.pj.gen.cfg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fly.jdbc.SqlFly;
import com.fly.jdbc.SqlFlyFactory;
import com.pj.gen.model.DbTable;
import com.pj.gen.read.ReadUtil;

/**
 * 总配置Model，配置sa-plus的整体行为 
 * 
 * @author kong 一路向下
 */
public class GenCfg {

	public SqlFly sqlFly = SqlFlyFactory.getSqlFly(); // 默认SqlFly对象

	public String projectPath = ""; // 总项目路径 (生成代码的路径) 
	
	// 服务端相关 
	public String serverProjectName = "sp-server";	 	// 服务端项目名称  
	public String codePath = "src/main/java/"; // 代码路径
	public String packagePath = "com.pj.project"; 		// 总包名 
	public String package_utils = ""; // util包导包地址 
	public String author = ""; 				// 生成的代码作者名字 

	// 后台相关 
	public String adminProjectName = "sp-admin";		// 后台管理项目名字 
	public String adminCodePath = "";	// 后台管理代码存放目录 

	// 接口文档相关
	public String apidocProjectName = "sp-apidoc";		// 接口文档项目名字 
	public String apidocCodePath = "";	// 接口文档存放目录 
	
	// 通用配置 
	public int fieldType = 1; 			// 对数据库表字段的处理方式（1=转小写，2=转大写，0=不变）
	public Boolean is_lomock = false; 	// 是否使用 lomock 【无用配置】
	public int fileUploadWay = 1; 	// 文件上传方式 (1=普通文件上传, 2=阿里云oss文件服务器)
	public int modelVisitWay = 1; 	// 实体类的访问权限修饰符 (1=private, 2=public) 
	public int modelDocWay = 1; 	// 实体类的注释形式 (1=行尾注释, 2=单行双星文档注释, 3=标准双星文档注释)  
	public int modelAddLine = 1; 	// 实体类的每个字段之间是否隔一个空行(1=是, 2=否)
	public int utilDocWay = 1; 	// util类的注释风格 (1=行尾位注释, 2=行上注释, 3=无注释)
	public int modelStyle = 1;		// 实体类字段风格 (1=保留下划线, 2=下划线转驼峰)  （如果打开下划线转驼峰，需采用resultMap手动映射模式[推荐]，或打开yml配置文件的 map-underscore-to-camel-case=true 选项 ）
	public int resultMapWay = 1; 	// resultMap映射模式  (1=自动模式, 2=手动模式)
	public int apiMappingWay = 1; 	// apiMapping模式  (1=@RequsetMapping, 2=@GetMapping, 3=@PostMapping)
	public int sqlSelectColumnWay = 1; 	// mapper.xml中的通用查询，是select * 还是所有列 (1=select *, 2=select所有列)
	public int sqlEnclose = 2;			// 生成的sql语句中，是否将字段用`包裹起来(1=是,2=否)
	public int saTokenAuthWay = 1;			// 鉴权代码的方式 (1=代码鉴权, 2=注解式鉴权)
	public boolean mybatisPlus = false; // 是否使用mybatisPlus
	public boolean isOutFC = true; // 是否输出FC.java工厂类 
	public int webLibImportWay = 1; // 前端js库导入方式(1=cdn导入, 2=本地导入[需将sa-admin附带js包复制到kj文件夹])
	
	
	public String packageUnderlineTo = "_"; 	// 将包名中的下划线转换成指定内容，比如：$、2、4 或者空字符串"" 
	public String defaultMeunIcon = "el-icon-folder-opened"; 	// 生成后台管理页面时，默认的菜单图标

	public List<String> tableNameList = new ArrayList<>(); 		// 要检索的表名字集合 
	public List<DbTable> tableList = new ArrayList<>(); 		// 检索出的表集合 

	
	// ---------------- 一些工具方法
	// 获取换行 
	public String getLine() {
		return "\r\n";
	}
	// 获取双星文档注释 
	public String getStarDoc(String str) {
		if(modelDocWay == 1) {
			return "\t// " + str + " ";
		}
		if(modelDocWay == 2) {
			return "\t/** " + str + " */";
		}
		if(modelDocWay == 3) {
			return "\t/**\r\n\t * " + str + " \r\n\t */";
		}
		return "";
	}
		

	
	// ---------------- 项目根路径 
	// 服务端代码根路径 
	public String getServerProjectPath() {
		String path = projectPath + "\\" + serverProjectName + "\\";
		return new File(path).getAbsolutePath() + "\\";
	}
	// 后天管理端代码根路径 
	public String getAdminProjectPath() {
		String path = projectPath + "\\" + adminProjectName + "\\";
		return new File(path).getAbsolutePath() + "\\";
	}
	// 接口文档端代码根路径 
	public String getApidocProjectPath() {
		String path = projectPath + "\\" + apidocProjectName + "\\";
		return new File(path).getAbsolutePath() + "\\";
	}
	



	// ---------------- IO 主目录 
	
	// 返会服务端IO的主目录
	public String getServerIoPath() {
		String path = getServerProjectPath() + codePath + "\\" + packagePath.replace(".", "\\");
		return new File(path).getAbsolutePath() + "\\";
	}
	// 返会admin后台管理的IO的主目录
	public String getAdminIoPath() {
		String path = getAdminProjectPath() + adminCodePath + "\\";
		return new File(path).getAbsolutePath() + "\\";
	}
	// 返会apidoc后台管理的IO的主目录
	public String getApidocIoPath() {
		String path = getApidocProjectPath() + apidocCodePath + "\\";
		return new File(path).getAbsolutePath() + "\\";
	}
		

	// 追加所有表名字 
	public GenCfg addTableAll() {
		for (String tName : ReadUtil.getTableList(GenCfgManager.cfg.sqlFly.getConnection())) {
			GenCfgManager.cfg.tableNameList.add(tName);
		}
		System.out.println("----------追加所有表：" + GenCfgManager.cfg.tableNameList);
		return this;
	}
	
	// 追加一个表名字
	public GenCfg addTableName(String... tableNames) {
		for (String tableName : tableNames) {
			if (!tableNameList.contains(tableName))
				tableNameList.add(tableName);
		}
		return this;
	}

	// 移除一个表名字
	public GenCfg removeTableName(String... tableNames) {
		for (String tableName : tableNames) {
			tableNameList.remove(tableName);
		}
		return this;
	}
	
	
	
	// 一坨坨 get set
	public String getProjectPath() {
		return projectPath;
	}

	public GenCfg setProjectPath(String projectPath) {
		this.projectPath = projectPath;
		return this;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public GenCfg setPackagePath(String packagePath) {
		this.packagePath = packagePath;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public GenCfg setAuthor(String author) {
		this.author = author;
		return this;
	}

	public int getFieldType() {
		return fieldType;
	}

	public GenCfg setFieldType(int fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	public Boolean getIs_lomock() {
		return is_lomock;
	}

	public GenCfg setIs_lomock(Boolean is_lomock) {
		this.is_lomock = is_lomock;
		return this;
	}

	public String getCodePath() {
		return codePath;
	}

	public GenCfg setCodePath(String codePath) {
		this.codePath = codePath;
		return this;
	}

	
	public List<DbTable> getTableList() {
		return tableList;
	}

	public void setTableList(List<DbTable> tableList) {
		this.tableList = tableList;
	}


	public String getAdminCodePath() {
		return adminCodePath;
	}

	public GenCfg setAdminCodePath(String adminCodePath) {
		this.adminCodePath = adminCodePath;
		return this;
	}
	public String getPackage_utils() {
		return package_utils;
	}
	public GenCfg setPackage_utils(String package_utils) {
		this.package_utils = package_utils;
		return this;
	}

	
	/**
	 * @return apidocCodePath
	 */
	public String getApidocCodePath() {
		return apidocCodePath;
	}
	/**
	 * @param apidocCodePath 要设置的 apidocCodePath
	 */
	public GenCfg setApidocCodePath(String apidocCodePath) {
		this.apidocCodePath = apidocCodePath;
		return this;
	}
	/**
	 * @return fileUploadWay
	 */
	public int getFileUploadWay() {
		return fileUploadWay;
	}
	/**
	 * @param fileUploadWay 要设置的 fileUploadWay
	 */
	public GenCfg setFileUploadWay(int fileUploadWay) {
		this.fileUploadWay = fileUploadWay;
		return this;
	}
	/**
	 * @return modelVisitWay
	 */
	public int getModelVisitWay() {
		return modelVisitWay;
	}
	/**
	 * @param modelVisitWay 要设置的 modelVisitWay
	 */
	public GenCfg setModelVisitWay(int modelVisitWay) {
		this.modelVisitWay = modelVisitWay;
		return this;
	}
	// 返回实体类的访问权限修饰符 (String形式)
	public String getModelVisitWayString() {
		if(modelVisitWay == 1) {
			return "private";
		}
		if(modelVisitWay == 2) {
			return "public";
		}
		return "";
	}
	/**
	 * @return modelDocWay
	 */
	public int getModelDocWay() {
		return modelDocWay;
	}
	/**
	 * @param modelDocWay 要设置的 modelDocWay
	 */
	public GenCfg setModelDocWay(int modelDocWay) {
		this.modelDocWay = modelDocWay;
		return this;
	}
	/**
	 * @return modelStyle
	 */
	public int getModelStyle() {
		return modelStyle;
	}
	/**
	 * @param modelStyle 要设置的 modelStyle
	 */
	public GenCfg setModelStyle(int modelStyle) {
		this.modelStyle = modelStyle;
		return this;
	}
	/**
	 * @return resultMapWay
	 */
	public int getResultMapWay() {
		return resultMapWay;
	}
	/**
	 * @param resultMapWay 要设置的 resultMapWay
	 */
	public GenCfg setResultMapWay(int resultMapWay) {
		this.resultMapWay = resultMapWay;
		return this;
	}
	/**
	 * @return packageUnderlineTo
	 */
	public String getPackageUnderlineTo() {
		return packageUnderlineTo;
	}
	/**
	 * @param packageUnderlineTo 要设置的 packageUnderlineTo
	 */
	public GenCfg setPackageUnderlineTo(String packageUnderlineTo) {
		this.packageUnderlineTo = packageUnderlineTo;
		return this;
	}/**
	 * @return utilDocWay
	 */
	public int getUtilDocWay() {
		return utilDocWay;
	}
	/**
	 * @param utilDocWay 要设置的 utilDocWay
	 */
	public GenCfg setUtilDocWay(int utilDocWay) {
		this.utilDocWay = utilDocWay;
		return this;
	}
	
	/**
	 * @return serverProjectName
	 */
	public String getServerProjectName() {
		return serverProjectName;
	}
	/**
	 * @param serverProjectName 要设置的 serverProjectName
	 */
	public GenCfg setServerProjectName(String serverProjectName) {
		this.serverProjectName = serverProjectName;
		return this;
	}
	/**
	 * @return adminProjectName
	 */
	public String getAdminProjectName() {
		return adminProjectName;
	}
	/**
	 * @param adminProjectName 要设置的 adminProjectName
	 */
	public GenCfg setAdminProjectName(String adminProjectName) {
		this.adminProjectName = adminProjectName;
		return this;
	}
	/**
	 * @return apidocProjectName
	 */
	public String getApidocProjectName() {
		return apidocProjectName;
	}
	/**
	 * @param apidocProjectName 要设置的 apidocProjectName
	 */
	public GenCfg setApidocProjectName(String apidocProjectName) {
		this.apidocProjectName = apidocProjectName;
		return this;
	}
	/**
	 * @return apiMappingWay
	 */
	public int getApiMappingWay() {
		return apiMappingWay;
	}
	/**
	 * @param apiMappingWay 要设置的 apiMappingWay
	 */
	public GenCfg setApiMappingWay(int apiMappingWay) {
		this.apiMappingWay = apiMappingWay;
		return this;
	}
	/**
	 * 获取apiMapping方式，string形势
	 */
	public String getApiMappingWayString() {
		if(this.apiMappingWay == 1) {
			return "RequestMapping";
		}
		if(this.apiMappingWay == 2) {
			return "GetMapping";
		}
		if(this.apiMappingWay == 3) {
			return "PostMapping";
		}
		return "";
	}
	/**
	 * @return sqlSelectColumnWay
	 */
	public int getSqlSelectColumnWay() {
		return sqlSelectColumnWay;
	}
	/**
	 * @param sqlSelectColumnWay 要设置的 sqlSelectColumnWay
	 */
	public GenCfg setSqlSelectColumnWay(int sqlSelectColumnWay) {
		this.sqlSelectColumnWay = sqlSelectColumnWay;
		return this;
	}
	/**
	 * @return sqlEnclose
	 */
	public int getSqlEnclose() {
		return sqlEnclose;
	}
	/**
	 * @param sqlEnclose 要设置的 sqlEnclose
	 */
	public GenCfg setSqlEnclose(int sqlEnclose) {
		this.sqlEnclose = sqlEnclose;
		return this;
	}
	// 根据配置决定是否应该将一段sql的`去掉
	public String getSqlEncloseRefreshStr(String str) {
		if(sqlEnclose == 2) {
			str = str.replaceAll("`", "");
		}
		return str;
	}
	/**
	 * @return saTokenAuthWay
	 */
	public int getSaTokenAuthWay() {
		return saTokenAuthWay;
	}
	/**
	 * @param saTokenAuthWay 要设置的 saTokenAuthWay
	 */
	public GenCfg setSaTokenAuthWay(int saTokenAuthWay) {
		this.saTokenAuthWay = saTokenAuthWay;
		return this;
	}

	public GenCfg setMybatisPlus(boolean mybatisPlus){
		this.mybatisPlus = mybatisPlus;
		return this;
	}
	public boolean getMybatisPlus(){
		return mybatisPlus;
	}
	
	
	/**
	 * @return isOutFC
	 */
	public boolean isOutFC() {
		return isOutFC;
	}
	/**
	 * @param isOutFC 要设置的 isOutFC
	 */
	public GenCfg setOutFC(boolean isOutFC) {
		this.isOutFC = isOutFC;
		return this;
	}
	/**
	 * @return defaultMeunIcon
	 */
	public String getDefaultMeunIcon() {
		return defaultMeunIcon;
	}
	/**
	 * @param defaultMeunIcon 要设置的 defaultMeunIcon
	 */
	public GenCfg setDefaultMeunIcon(String defaultMeunIcon) {
		this.defaultMeunIcon = defaultMeunIcon;
		return this;
	}
	/**
	 * @return webLibImportWay
	 */
	public int getWebLibImportWay() {
		return webLibImportWay;
	}
	/**
	 * @param webLibImportWay 要设置的 webLibImportWay
	 */
	public GenCfg setWebLibImportWay(int webLibImportWay) {
		this.webLibImportWay = webLibImportWay;
		return this;
	}
	/**
	 * @return modelAddLine
	 */
	public int getModelAddLine() {
		return modelAddLine;
	}
	/**
	 * @param modelAddLine 要设置的 modelAddLine
	 */
	public GenCfg setModelAddLine(int modelAddLine) {
		this.modelAddLine = modelAddLine;
		return this;
	}
	

}
