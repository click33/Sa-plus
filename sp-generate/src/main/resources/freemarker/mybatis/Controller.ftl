package ${t.packagePath};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.pj.utils.sg.*;
import com.pj.utils.so.*;
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

	/** 底层 Mapper 对象 */
	@Autowired
	${t.mkNameBig}Mapper ${t.varName}Mapper;

	/** 增 */  
	@${cfg.apiMappingWayString}("add")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(${t.modelName} ${t.varNameSimple}){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		${t.varName}Mapper.add(${t.varNameSimple});
		${t.varNameSimple} = ${t.varName}Mapper.getById(SP.publicMapper.getPrimarykey());
		return AjaxJson.getSuccessData(${t.varNameSimple});
	}

	/** 删 */  
	@${cfg.apiMappingWayString}("delete")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson delete(${t.primaryKey.fieldType} id){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		int line = ${t.varName}Mapper.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@${cfg.apiMappingWayString}("deleteByIds")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson deleteByIds(){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class); 
		int line = SP.publicMapper.deleteByIds("${t.tableName}", ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改 */  
	@${cfg.apiMappingWayString}("update")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(${t.modelName}.PERMISSION_CODE)${cfg.line}</#if><#rt>
	public AjaxJson update(${t.modelName} ${t.varNameSimple}){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(${t.modelName}.PERMISSION_CODE);${cfg.line}</#if><#rt>
		int line = ${t.varName}Mapper.update(${t.varNameSimple});
		return AjaxJson.getByLine(line);
	}

	/** 查 - 根据id */  
	@${cfg.apiMappingWayString}("getById")
	public AjaxJson getById(${t.primaryKey.fieldType} id){
		${t.modelName} ${t.varNameSimple} = ${t.varName}Mapper.getById(id);
		return AjaxJson.getSuccessData(${t.varNameSimple});
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	@${cfg.apiMappingWayString}("getList")
	public AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
		List<${t.modelName}> list = ${t.varName}Mapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
	
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
