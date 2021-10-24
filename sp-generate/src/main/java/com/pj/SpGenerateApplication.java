package com.pj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fly.jdbc.cfg.FlyConfig;
import com.fly.jdbc.cfg.FlyObjects;
import com.fly.spring.SqlFlySetup;
import com.pj.gen.GenUtil;
import com.pj.gen.cfg.GenCfgManager;

@SqlFlySetup
@SpringBootApplication
public class SpGenerateApplication {
	
	// 直接运行代码生成器
	public static void main(String[] args) {

		// 启动springboot   
		SpringApplication.run(SpGenerateApplication.class, args); 
		

		// ===================================  设置连接信息  =================================== 
        FlyConfig config = new FlyConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUrl("jdbc:mysql://127.0.0.1:3306/sp-dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("root123456");
        config.setPrintSql(true);		// 是否打印sql  
        FlyObjects.setConfig(config);    // 注入到框架中 
        // ！！！注意：如果报错创建连接失败，可尝试将连接字符串中的 useSSL=true 改为 useSSL=false 
		
        
		// ===================================  你可以重写一些内部逻辑，填充一些功能  =================================== 
		// 请参考本文件最底部示例 
        
		
		// ===================================  一些全局设置  =================================== 
		GenCfgManager.cfg
			.setProjectPath("E:/work/project-yun/sa-plus/")	// 总项目地址 (生成代码的路径) 
	        .setServerProjectName("sp-server")				// 服务端 - 项目名称 
//	        .setServerProjectName("sp-com/sp-core")			// 服务端 - 项目名称 (sp-com多模块版填此格式)
	        .setCodePath("src/main/java/")					// 服务端代码 - 存放路径 
	        .setPackagePath( "com.pj.project")				// 服务端代码 - 总包名 
	        .setPackage_utils("com.pj.utils.sg.*")			// 服务端代码 - util类包地址 
	        .setAuthor("shengzhang")						// 服务端代码 - 代码作者 (一定要换成您的大名哦，哈哈)
	        .setAdminProjectName("sp-admin")				// 后台管理 - 项目名称 
            .setAdminCodePath("sa-view/")					// 后台管理-代码存放目录 
	        .setApidocProjectName("sp-apidoc") 				// 接口文档 - 项目名称 
            .setApidocCodePath("project/")					// 接口文档 - 存放目录  
            .setFileUploadWay(1)			// 文件上传方式 (1=普通文件上传, 2=阿里云oss文件服务器[需要集成阿里云oss相关工具类]) 
            .setModelStyle(2) 				// 实体类字段风格 (1=保留下划线, 2=下划线转驼峰 [如果打开下划线转驼峰，需采用resultMap手动映射模式，或打开yml配置文件的 map-underscore-to-camel-case=true 选项])
            .setResultMapWay(2) 			// resultMap映射模式  (1=自动模式, 2=手动模式)
            .setModelVisitWay(2)			// 实体类的访问权限修饰符 (1=private, 2=public)  
            .setModelDocWay(3)				// 实体类的注释形式 (1=行尾注释, 2=单行双星文档注释, 3=标准双星文档注释)  
            .setModelAddLine(1) 			// 实体类的每个字段之间是否隔一个空行(1=是, 2=否)
            .setUtilDocWay(1) 				// util类的注释风格 (1=行尾注释, 2=行上注释, 3=无注释) 
            .setPackageUnderlineTo("_")		// 将包名中的下划线强制转换成指定字符串，比如：$、2、4 或者空字符串""  
            .setApiMappingWay(1) 			// apiMapping模式  (1=@RequsetMapping, 2=@GetMapping, 3=@PostMapping) 
            .setSqlSelectColumnWay(1) 		// mapper.xml中的通用查询，是select * 还是所有列 (1=select *, 2=select 所有列) 
            .setSqlEnclose(2) 				// 生成的sql语句中，是否将字段用`包裹起来(1=是,2=否) 
            .setSaTokenAuthWay(2)   		// 鉴权代码的方式 (1=代码式鉴权, 2=注解式鉴权)
			.setMybatisPlus(false)			// 是否生成的代码遵循mybatis-plus风格(继承一些mp的特定父类)
			.setOutFC(true)					// 是否输出FC.java工厂类 
			.setDefaultMeunIcon("el-icon-folder-opened")	// 生成后台管理页面时，默认的菜单图标
			.setWebLibImportWay(1) 			// 前端js库导入方式(1=cdn导入, 2=本地导入[需将sa-admin附带js包复制到kj文件夹])
			// .addTableName("sys_user")	// 添加要生成的表 (单个添加)
			.addTableAll()		// 添加要生成的表 (一次性添加所有表)
            .removeTableName("sp_role", "sp_role_permission", "sp_admin", "sp_apilog", "sp_cfg")	// 移除这些内置的表，不必生成代码
            ; 
		
		System.out.println("\n\n\n--------------------------------------------\n\n\n");
		

		// ===================================  开始读取并输出   =================================== 
		GenUtil.doRead();	// 从数据库读取数据 
		GenUtil.doOutMyBatis();	// 输出java代码 （mybatis版本） 
//		GenUtil.doOutMyBatisService();	// 输出java代码 - 追加service层 
		GenUtil.doOutAdminHtml();	// 输出 admin后台管理页面  
		GenUtil.doOutApidoc();	// 输出 接口文档页面 
		

		
		// ===================================  完结输出   =================================== 
		System.out.println("\n\n------------------------------ 完结撒花 ------------------------------");
		System.out.println(" - sa-plus 快速开发平台,  当前版本v1.26.0，更新于2021-10-24 ");
		System.out.println(" - 在线文档： http://sa-plus.dev33.cn");
		System.out.println(" - 开源地址： https://github.com/click33/sa-plus\n\n");
		
	}
	
	
	
	
	
	
	
	

	// ===================================  你可以重写一些内部逻辑，填充一些功能  =================================== 
	// 请参考本文件最底部示例
//	// 例如 以下代码代表截取掉表前缀 (把这段代码复制上去) 
//	DbModelManager.manager = new DbModelManager() {
//		// 重写创建 DbTable 的函数 
//		public DbTable getDbTable() {
//			return new DbTable() {
//				// 重写获取模块名称的函数 
//				public String getMkName(){
//					String tableName = this.getTableName();
//					return tableName.replaceAll("sys_", "").replaceAll("_table", "");	// 结果: sys_user_table --> user 	
//				}
//			};
//		}
//	};
	// ================== end 
	

	

}