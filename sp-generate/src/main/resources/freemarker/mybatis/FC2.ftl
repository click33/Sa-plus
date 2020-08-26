package ${cfg.packagePath};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

<#list cfg.tableList as t>
import ${t.packagePath}.${t.mkNameBig}Mapper;
</#list>
import com.pj.project4sf.public4mapper.PublicMapper;
import com.pj.project4sf.public4mapper.PublicService;

/**
 * SpringBean依赖清单，项目中所有Bean在此定义
 */
@Component
public class FC {

	// ======================================== 所有Mapper ============================================== 

<#list cfg.tableList as t>
	public static ${t.mkNameBig}Mapper ${t.varName}Mapper;		// Mapper依赖：${t.tableComment}
</#list>
	public static PublicMapper publicMapper;					// Mapper: 公共Mapper 



	// ======================================== 所有Service ============================================== 

	public static PublicService publicService;						// Service：公共service



	// ======================================== 所有注入所有Bean ============================================== 
	
	// 注入 
	@Autowired
	public void setBean(
<#list cfg.tableList as t>
			${t.mkNameBig}Mapper ${t.varName}Mapper, 
</#list>
			PublicMapper publicMapper,
			PublicService publicService
			) {
<#list cfg.tableList as t>
			FC.${t.varName}Mapper = ${t.varName}Mapper;
</#list>
			FC.publicMapper = publicMapper;
			FC.publicService = publicService;
	}


}