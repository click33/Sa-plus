package ${t.packagePath};

import java.io.Serializable;
<#if t.hasFo("date", "date-create", "date-update") >import java.util.*;
</#if>
<#if cfg.mybatisPlus>
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
</#if>

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Model: ${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 */
@Data
@Accessors(chain = true)
<#if cfg.mybatisPlus>
@TableName("${t.tableName}")
@EqualsAndHashCode(callSuper = false)
</#if>
public class ${t.modelName} <#if cfg.mybatisPlus> extends Model<${t.modelName}></#if> implements Serializable {

	private static final long serialVersionUID = 1L;

<#list t.columnList as c>
<#if  cfg.modelDocWay == 2 >	/** ${c.columnComment} */
</#if>
	<#if cfg.mybatisPlus&& t.primaryKey.columnName == c.columnName>
	@TableId(type = IdType.AUTO)
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
