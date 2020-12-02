package ${t.packagePath};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ${cfg.package_utils};
import com.pj.project4sp.SP;

import com.pj.current.satoken.StpUserUtil;
<#if cfg.saTokenAuthWay == 1 >import cn.dev33.satoken.stp.StpUtil;
<#else>import cn.dev33.satoken.annotation.SaCheckPermission;
</#if>


/**
 * Controller: ${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 */
@RestController
@RequestMapping("/${t.mkNameBig}/")
public class ${t.mkNameBig}Controller {

	/** 底层 Service 对象 */
	@Autowired
	${t.mkNameBig}Service ${t.varName}Service;

	/** 增 */  
	@${cfg.apiMappingWayString}("add")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(${t.modelName} ${t.varNameSimple}){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		${t.varName}Service.add(${t.varNameSimple});
		${t.varNameSimple} = ${t.varName}Service.getById(SP.publicMapper.getPrimarykey());
		return AjaxJson.getSuccessData(${t.varNameSimple});
	}

	/** 删 */  
	@${cfg.apiMappingWayString}("delete")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson delete(${t.primaryKey.fieldType} id){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		int line = ${t.varName}Service.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@${cfg.apiMappingWayString}("deleteByIds")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson deleteByIds(){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class); 
		int line = SP.publicMapper.deleteByIds(${t.modelName}.TABLE_NAME, ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改 */  
	@${cfg.apiMappingWayString}("update")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson update(${t.modelName} ${t.varNameSimple}){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		int line = ${t.varName}Service.update(${t.varNameSimple});
		return AjaxJson.getByLine(line);
	}

	/** 查 - 根据id */  
	@${cfg.apiMappingWayString}("getById")
	public AjaxJson getById(${t.primaryKey.fieldType} id){
		${t.modelName} ${t.varNameSimple} = ${t.varName}Service.getById(id);
		return AjaxJson.getSuccessData(${t.varNameSimple});
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	@${cfg.apiMappingWayString}("getList")
	public AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
<#if t.hasFo("logic-delete")>		so.set("${t.getDbColumnByFoType('logic-delete').fieldName}", ${t.getDbColumnByFoType('logic-delete').tx.yes});
</#if>
		List<${t.modelName}> list = ${t.varName}Service.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
<#if t.hasFt('tree') >
	/** 查集合 (整个表数据转化为tree结构返回) */  
	@${cfg.apiMappingWayString}("getTree")
	public AjaxJson getTree() { 
		// 获取记录 
		SoMap so = SoMap.getRequestSoMap();
<#if t.hasFo("logic-delete")>		so.set("${t.getDbColumnByFoType('logic-delete').fieldName}", ${t.getDbColumnByFoType('logic-delete').tx.yes});
</#if>
		List<${t.modelName}> list = ${t.varName}Service.getList(so);
		// 转为tree结构，并返回 
		List<SoMap> listMap = SoMap.getSoMapByList(list);
		List<SoMap> listTree = SoMap.listToTree(listMap, "${t.getTreeIdkey()}", "${t.getTreeFkey()}", "${t.getTreeCkey()}");
		return AjaxJson.getPageData(Long.valueOf(listMap.size()), listTree);
	}
</#if>
	
	
<#list t.getTallListByTxKey('switch') as c>
	/** 改 - ${c.columnComment} */  
	@${cfg.apiMappingWayString}("update${c.fieldNameFnCat}")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson update${c.fieldNameFnCat}(${t.primaryKey.fieldType} id, ${c.fieldType} value){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		int line = SP.publicMapper.updateColumnById(${t.modelName}.TABLE_NAME, "${c.columnName}", value, id);
		return AjaxJson.getByLine(line);
	}
	
</#list>
	
	// ------------------------- 前端接口 -------------------------
	
	
	/** 改 - 不传不改 [G] */
	@RequestMapping("updateByNotNull")
	public AjaxJson updateByNotNull(${t.primaryKey.fieldType} id){
		AjaxError.throwBy(true, "如需正常调用此接口，请删除此行代码");
		// 鉴别身份，是否为数据创建者 
		long userId = SP.publicMapper.getColumnByIdToLong(${t.modelName}.TABLE_NAME, "user_id", id);
		AjaxError.throwBy(userId != StpUserUtil.getLoginIdAsLong(), "此数据您无权限修改");
		// 开始修改 (请只保留需要修改的字段)
		SoMap so = SoMap.getRequestSoMap();
		so.clearNotIn(${t.getT1ListCatString3()}).clearNull()<#if cfg.modelStyle == 2 >.humpToLineCase()</#if>;	
		int line = SP.publicMapper.updateBySoMapById(${t.modelName}.TABLE_NAME, so, id);
		return AjaxJson.getByLine(line);
	}
	
	
	
	
	
	

}
