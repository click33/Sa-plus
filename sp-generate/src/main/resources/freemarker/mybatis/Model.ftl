package ${t.packagePath};

import java.io.Serializable;
<#if t.hasFo("date", "date-create", "date-update") >import java.util.*;
</#if>
<#if cfg.mybatisPlus>
import com.baomidou.mybatisplus.annotation.*;
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
@TableName(${t.modelName}.TABLE_NAME)
@EqualsAndHashCode(callSuper = false)
</#if>
public class ${t.modelName}<#if cfg.mybatisPlus> extends Model<${t.modelName}></#if> implements Serializable {

	// ---------- 模块常量 ----------
<#if cfg.modelDocWay = 2 || cfg.modelDocWay = 3 >${cfg.getStarDoc('序列化版本id')}</#if>
	private static final long serialVersionUID = 1L;	<#if cfg.modelDocWay == 1>	// 序列化版本id </#if>
<#if cfg.modelDocWay = 2 || cfg.modelDocWay = 3 >${cfg.getStarDoc('此模块对应的表名')}</#if>
	public static final String TABLE_NAME = "${t.tableName}";	<#if cfg.modelDocWay == 1>	// 此模块对应的表名 </#if>
<#if cfg.modelDocWay = 2 || cfg.modelDocWay = 3 >${cfg.getStarDoc('此模块对应的权限码')}</#if>
	public static final String PERMISSION_CODE = "${t.kebabName}";	<#if cfg.modelDocWay == 1>	// 此模块对应的权限码 </#if>


	// ---------- 表中字段 ----------
<#list t.t1List as c>
<#if cfg.modelDocWay = 2 || cfg.modelDocWay = 3>${cfg.getStarDoc(c.columnComment)}</#if>
<#if cfg.mybatisPlus && t.primaryKey.columnName == c.columnName>	@TableId(type = IdType.AUTO)${cfg.line}</#if><#rt>
	${cfg.modelVisitWayString} ${c.fieldType} ${c.fieldName};	<#if cfg.modelDocWay == 1>	// ${c.columnComment} </#if><#if cfg.modelAddLine = 1>${cfg.line}</#if>
</#list>


<#if t.t23List?size != 0 || t.hasFt('tree-lazy')>
	// ---------- 额外字段 ----------
<#list t.t23List as c>
<#if cfg.modelDocWay = 2 || cfg.modelDocWay = 3 >${cfg.getStarDoc(c.tx.comment)}</#if>
	${cfg.modelVisitWayString} ${c.getJavaType()} ${c.fieldName};	<#if cfg.modelDocWay == 1>	// 外键: ${c.tx.comment} </#if><#if cfg.modelAddLine = 1>${cfg.line}</#if>
</#list>
</#if>
<#if t.hasFt('tree-lazy')>
<#if cfg.modelDocWay = 2 || cfg.modelDocWay = 3>${cfg.getStarDoc('是否包含子节点')}</#if>
	public Boolean getHasChildren() {
		return ${t.getDbColumnByFalg('tree-lazy-children-count').fieldName} > 0;
	}
</#if>


	


}
