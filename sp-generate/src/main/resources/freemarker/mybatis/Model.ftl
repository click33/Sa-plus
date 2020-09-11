package ${t.packagePath};

import java.io.Serializable;
<#if t.hasFo("date", "date-create", "date-update") >import java.util.*;
</#if>
	



import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Model: ${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 */
@Data
@Accessors(chain = true)
public class ${t.modelName} implements Serializable {

	private static final long serialVersionUID = 1L;

<#list t.columnList as c>
<#if  cfg.modelDocWay == 2 >	/** ${c.columnComment} */
</#if>
	${cfg.modelVisitWayString} ${c.fieldType} ${c.fieldName};	<#if cfg.modelDocWay == 1>	// ${c.columnComment} </#if>
</#list>


<#if t.getAllDbFk_12()?size != 0 || t.getAllDbFk_jh()?size != 0>
	// 额外字段 
<#list t.getAllDbFk_12() as fk>
<#if  cfg.modelDocWay == 2 >	/** 外键: ${fk.fkPkConcatComment} */
</#if>
	${cfg.modelVisitWayString} String ${fk.fieldName};	<#if cfg.modelDocWay == 1>	// 外键: ${fk.fkPkConcatComment} </#if>
</#list>
<#list t.getAllDbFk_jh() as fk>
<#if  cfg.modelDocWay == 2 >	/** 外键: ${fk.tx.comment} */
</#if>
	${cfg.modelVisitWayString} ${fk.getJavaType()} ${fk.getAsColumnName_fs()};	<#if cfg.modelDocWay == 1>	// 外键: ${fk.tx.comment} </#if>
</#list>
</#if>

	


}
