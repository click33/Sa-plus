package ${t.packagePath};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ${cfg.package_utils};

/**
 * 工具类：${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 *
 */
@Component
public class ${t.mkNameBig}Util {

	
	/** 底层 Mapper 对象 */
	static ${t.mkNameBig}Mapper ${t.varName}Mapper;
	@Autowired
	public void set${t.mkNameBig}Mapper(${t.mkNameBig}Mapper ${t.varName}Mapper) {
		${t.mkNameBig}Util.${t.varName}Mapper = ${t.varName}Mapper;
	}
	
	/** 
	 * 验证一个${t.modelName} 是否符合标准 (方便表单校验用)【G】 
	 */
	static void check(${t.modelName} ${t.varNameSimple}) {
		
<#-- private时 -->
<#if cfg.modelVisitWay == 1>
		<#list t.columnList as c>
<#if  cfg.utilDocWay == 2 >		// 验证: ${c.columnComment}
</#if>
		AjaxError.throwByIsNull(${t.varNameSimple}.get${c.getset}(), "${c.columnComment}不能为空");	<#if cfg.utilDocWay == 1>	// 验证: ${c.columnComment}	 </#if>
		</#list>
</#if>
<#-- public时 -->
<#if cfg.modelVisitWay == 2>
		<#list t.columnList as c>
<#if  cfg.utilDocWay == 2 >		// 验证: ${c.columnComment}
</#if>
		AjaxError.throwByIsNull(${t.varNameSimple}.${c.fieldName}, "${c.columnComment}不能为空");	<#if cfg.utilDocWay == 1>	// 验证: ${c.columnComment}	 </#if>
		</#list>
</#if>
		
		// 重重检验，最终合格
	}




<#-- private时 -->
<#if cfg.modelVisitWay == 1>
	/** 
	 * 获取一个${t.modelName} (方便复制代码用)【G】 
	 */ 
	static ${t.modelName} get${t.modelName}() {
		
		${t.modelName} ${t.varNameSimple} = new ${t.modelName}();
		<#list t.columnList as c>
<#if  cfg.utilDocWay == 2 >		// ${c.columnComment}
</#if>
		<#if c.fieldType == "String">
		${t.varNameSimple}.set${c.getset}("");	<#if cfg.utilDocWay == 1>	// ${c.columnComment}	 </#if>
		</#if>
		<#if c.fieldType == "long" || c.fieldType == "int">
		${t.varNameSimple}.set${c.getset}(0);	<#if cfg.utilDocWay == 1>	// ${c.columnComment}	 </#if>
		</#if>
		</#list>
		
		return ${t.varNameSimple};
	}
</#if>
<#-- public时 -->
<#if cfg.modelVisitWay == 2>
	/** 
	 * 获取一个${t.modelName} (方便复制代码用)【G】 
	 */ 
	static ${t.modelName} get${t.modelName}() {
		
		${t.modelName} ${t.varNameSimple} = new ${t.modelName}();
		<#list t.columnList as c>
<#if  cfg.utilDocWay == 2 >		// ${c.columnComment}
</#if>
		<#if c.fieldType == "String">
		${t.varNameSimple}.${c.fieldName} = "";	<#if cfg.utilDocWay == 1>	// ${c.columnComment}	 </#if>
		</#if>
		<#if c.fieldType == "long" || c.fieldType == "int">
		${t.varNameSimple}.${c.fieldName} = 0;	<#if cfg.utilDocWay == 1>	// ${c.columnComment}	 </#if>
		</#if>
		</#list>
		
		return ${t.varNameSimple};
	}
</#if>

	
	
	
	
	
}
