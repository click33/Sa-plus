package ${t.packagePath};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.utils.sg.*;
<#if t.hasFo("date", "date-create", "date-update") >import java.util.*;
</#if>

/**
 * 工具类：${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 *
 */
@Component
public class ${t.mkNameBig}Util {

	
	/** 底层 Mapper 对象 */
	public static ${t.mkNameBig}Mapper ${t.varName}Mapper;
	@Autowired
	private void set${t.mkNameBig}Mapper(${t.mkNameBig}Mapper ${t.varName}Mapper) {
		${t.mkNameBig}Util.${t.varName}Mapper = ${t.varName}Mapper;
	}
	
	
	/** 
	 * 将一个 ${t.modelName} 对象进行进行数据完整性校验 (方便add/update等接口数据校验) [G] 
	 */
	static void check(${t.modelName} ${t.varNameSimple}) {
${t.getT1List_ByUtilCheck()}<#rt>
	}

	/** 
	 * 获取一个${t.modelName} (方便复制代码用) [G] 
	 */ 
	static ${t.modelName} get${t.modelName}() {
<#if  cfg.utilDocWay == 2 >		// 声明对象${cfg.line}</#if><#rt>
		${t.modelName} ${t.varNameSimple} = new ${t.modelName}();<#if cfg.utilDocWay == 1>	// 声明对象 </#if>
${t.getT1List_ByUtilGetModel()}<#rt>
		return ${t.varNameSimple};
	}
	
	
	
	
	
}
