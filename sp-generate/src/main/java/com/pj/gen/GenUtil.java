package com.pj.gen;

import com.pj.gen.cfg.GenCfg;
import com.pj.gen.cfg.GenCfgManager;
import com.pj.gen.model.DbTable;
import com.pj.gen.read.FlyRead;
import com.pj.gen.read.FlyReadMySql;
import com.pj.gen.read.ReadUtil;

/**
 *  操作工具类 
 * @author kong
 *
 */
public class GenUtil {

	
	
	public static FlyRead flyRead = new FlyReadMySql();			// 默认的读取实现类    
	
	
	// 返回读取好的表信息  
	public static GenCfg getCodeCfgByReadOk(){
		initTables();
		flyRead.setCodeCfg(GenCfgManager.cfg).readInfo();
		return GenCfgManager.cfg;
	}
	
	
	// 开始读取 
	public static void doRead() {
		initTables();
		flyRead.setCodeCfg(GenCfgManager.cfg);
		flyRead.readInfo();
	}
	
	// 输出java代码 （mybatis版本） 
	public static void doOutMyBatis() {
		
		// 模块
		for (DbTable t : GenCfgManager.cfg.tableList) {
			
			// model
			String modelPath = t.getServerIoPath() + t.getModelName() + ".java";			// 路径
			String modelContent = FreeMarkerUtil.getResult("mybatis/Model.ftl", "t", t);		// 内容 
			SUtil.outFile(modelPath, modelContent);
			System.out.println(t.getModelName() + " 写入成功：\t\t\t" + modelPath);
			
			// Mapper.java 
			String mapperJavaPath = t.getServerIoPath() + t.getMkNameBig() + "Mapper.java";	// 路径
			String mapperJavaContent = FreeMarkerUtil.getResult("mybatis/MapperJava.ftl", "t", t);	// 内容 
			SUtil.outFile(mapperJavaPath, mapperJavaContent);
			System.out.println(t.getModelName() + "Mapper.java 写入成功：\t\t" + mapperJavaPath);

			// Mapper.xml 
			String mapperXmlPath = t.getServerIoPath() + t.getMkNameBig() + "Mapper.xml";	// 路径
			String mapperXmlContent = FreeMarkerUtil.getResult("mybatis/MapperXml.ftl", "t", t);	// 内容 
			mapperXmlContent = GenCfgManager.cfg.getSqlEncloseRefreshStr(mapperXmlContent);
			SUtil.outFile(mapperXmlPath, mapperXmlContent);
			System.out.println(t.getModelName() + "Mapper.xml 写入成功：\t\t" + mapperXmlPath);

			// Controller
			String controllerPath = t.getServerIoPath() + t.getMkNameBig() + "Controller.java";	// 路径 
			String controllerContent = FreeMarkerUtil.getResult("mybatis/Controller.ftl", "t", t);		// 内容 
			SUtil.outFile(controllerPath, controllerContent);
			System.out.println(t.getModelName() + "Controller 写入成功：\t\t" + controllerPath);

			// Util 
			String utilPath = t.getServerIoPath() + t.getMkNameBig() + "Util.java";	// 路径 
			String utilContent = FreeMarkerUtil.getResult("mybatis/Util.ftl", "t", t);		// 内容 
			SUtil.outFile(utilPath, utilContent);
			System.out.println(t.getModelName() + "Util 写入成功：\t\t" + utilPath);

			// 多打印一行，模块之间有个间隔 
			System.out.println();	
		}
		
		// FC.java 依赖清单 
		if(GenCfgManager.cfg.isOutFC) {
			String FCPath = GenCfgManager.cfg.getServerIoPath() + "FC.java";						// 路径  
			String FContent = FreeMarkerUtil.getResult("mybatis/FC.ftl", "abc", 123);		// 内容 
			SUtil.outFile(FCPath, FContent);
			System.out.println("FC.java 依赖清单写入成功：\t\t" + FCPath);
		}
		System.out.println("\n");
	}
	

	// 输出java代码 （mybatis版本-带service层 ） 
	public static void doOutMyBatisService() {
		
		// 模块
		for (DbTable t : GenCfgManager.cfg.tableList) {

			// Service
			String servicePath = t.getServerIoPath() + t.getMkNameBig() + "Service.java";	// 路径 
			String serviceContent = FreeMarkerUtil.getResult("service/Service.ftl", "t", t);		// 内容 
			SUtil.outFile(servicePath, serviceContent);
			System.out.println(t.getModelName() + "Service 写入成功：\t\t" + servicePath);

			// Controller
			String controllerPath = t.getServerIoPath() + t.getMkNameBig() + "Controller.java";	// 路径 
			String controllerContent = FreeMarkerUtil.getResult("service/Controller.ftl", "t", t);		// 内容 
			SUtil.outFile(controllerPath, controllerContent);
			System.out.println(t.getModelName() + "Controller 写入成功：\t\t" + controllerPath);
			
			// 多打印一行，模块之间有个间隔 
			System.out.println();	
		}
		
		// FC.java 依赖清单 
		if(GenCfgManager.cfg.isOutFC) {
			String FCPath = GenCfgManager.cfg.getServerIoPath() + "FC.java";						// 路径  
			String FContent = FreeMarkerUtil.getResult("service/FC.ftl", "abc", 123);		// 内容 
			SUtil.outFile(FCPath, FContent);
			System.out.println("FC.java 依赖清单写入成功：\t\t" + FCPath);
		}
		System.out.println("\n");
	}
	
	
	// 开始生成admin后台管理
	public static void doOutAdminHtml() {
		// 模块
		for (DbTable t : GenCfgManager.cfg.tableList) {
			// 查 
			String xxxListPath = t.getAdminIoPath() + t.getKebabName() + "-list.html";			// 路径 
			String xxxListContent = FreeMarkerUtil.getResult("admin/xxx-list.ftl", "t", t);			// 内容 
			SUtil.outFile(xxxListPath, xxxListContent);
			System.out.println(t.getKebabName() + "-list.html 写入成功：\t\t\t" + xxxListPath);

			// 增 
			String xxxAddPath = t.getAdminIoPath() + t.getKebabName() + "-add.html";			// 路径 
			String xxxAddContent = FreeMarkerUtil.getResult("admin/xxx-add.ftl", "t", t);			// 内容 
			SUtil.outFile(xxxAddPath, xxxAddContent);
			System.out.println(t.getKebabName() + "-add.html 写入成功：\t\t\t" + xxxAddPath);
			
			// 详情 
			String xxxInfoPath = t.getAdminIoPath() + t.getKebabName() + "-info.html";			// 路径 
			String xxxInfoContent = FreeMarkerUtil.getResult("admin/xxx-info.ftl", "t", t);			// 内容 
			SUtil.outFile(xxxInfoPath, xxxInfoContent);
			System.out.println(t.getKebabName() + "-info.html 写入成功：\t\t\t" + xxxInfoPath);
			
			// 多打印一行，模块之间有个间隔 
			System.out.println();	
		}
		
		// menu-list.js 菜单列表 
		String menuListPath = GenCfgManager.cfg.getAdminProjectPath() + "sa-frame\\menu-list.js";						// 路径  
		String menuListContent = FreeMarkerUtil.getResult("admin/menu-list.ftl", "abc", 123);		// 内容 
		SUtil.outFile(menuListPath, menuListContent);
		System.out.println("menu-list.js 菜单列表, 写入成功：\t\t" + menuListPath);
		
	}

	// 开始生成接口文档地址 
	public static void doOutApidoc() {
		// 模块
		for (DbTable t : GenCfgManager.cfg.tableList) {
			// 接口文档 
			String xxxMdPath = t.getApidocIoPath() + t.getKebabName() + ".md";			// 路径 
			String xxxMdContent = FreeMarkerUtil.getResult("apidoc/xxx-md.ftl", "t", t);			// 内容 
			SUtil.outFile(xxxMdPath, xxxMdContent);
			System.out.println(t.getKebabName() + ".md 写入成功：\t\t\t" + xxxMdPath);

			// 多打印一行，模块之间有个间隔 
//			System.out.println();	
		}
		
		// sidebar.md 菜单列表 
		String sidebarPath = GenCfgManager.cfg.getApidocProjectPath() + "_sidebar.md";						// 路径  
		String sidebarContent = FreeMarkerUtil.getResult("apidoc/sidebar.ftl", "abc", 123);		// 内容 
		SUtil.outFile(sidebarPath, sidebarContent);
		System.out.println("_sidebar.js 接口文档目录树, 写入成功：\t\t" + sidebarPath);
		
	}
	
	
	
	
	
	// init 相关依赖  
	private static void initTables() {
		// 初始化数据表集合
		if(GenCfgManager.cfg.tableNameList == null || GenCfgManager.cfg.tableNameList.size() == 0){
			for (String tName : ReadUtil.getTableList(GenCfgManager.cfg.sqlFly.getConnection())) {
				GenCfgManager.cfg.tableNameList.add(tName);
			}
		}
	}

	
	
}
