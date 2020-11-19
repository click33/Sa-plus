package ${cfg.packagePath};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

<#list cfg.tableList as t>
import ${t.packagePath}.${t.mkNameBig}Mapper;
</#list>

/**
 * SpringBean依赖清单，所有Bean在此定义
 */
@Component
public class FC {

	// ======================================== 所有Mapper ============================================== 

<#list cfg.tableList as t>
	/** Mapper依赖：${t.tableComment}  */
	public static ${t.mkNameBig}Mapper ${t.varName}Mapper;
	@Autowired
	public void set${t.mkNameBig}Mapper(${t.mkNameBig}Mapper ${t.varName}Mapper) {
		FC.${t.varName}Mapper = ${t.varName}Mapper;
	}
	
</#list>

}