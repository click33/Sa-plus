package ${t.packagePath};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ${cfg.package_utils};
import com.pj.project4sp.SP;


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

	/** 权限常量 */
	static final String PERMISSION_CODE = "${t.kebabName}";

	/** 底层 Mapper 对象 */
	@Autowired
	${t.mkNameBig}Mapper ${t.varName}Mapper;

	/** 增 */  
	@${cfg.apiMappingWayString}("add")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(PERMISSION_CODE)
</#if>
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(${t.modelName} ${t.varNameSimple}){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(PERMISSION_CODE);
</#if>
		${t.varName}Mapper.add(${t.varNameSimple});
		${t.varNameSimple} = ${t.varName}Mapper.getById(SP.publicMapper.getPrimarykey());
		return AjaxJson.getSuccessData(${t.varNameSimple});
	}

	/** 删 */  
	@${cfg.apiMappingWayString}("delete")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(PERMISSION_CODE)
</#if>
	AjaxJson delete(${t.primaryKey.fieldType} id){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(PERMISSION_CODE);
</#if>
		int line = ${t.varName}Mapper.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@${cfg.apiMappingWayString}("deleteByIds")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(PERMISSION_CODE)
</#if>
	AjaxJson deleteByIds(){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(PERMISSION_CODE);
</#if>
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class); 
		int line = SP.publicMapper.deleteByIds("${t.tableName}", ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改 */  
	@${cfg.apiMappingWayString}("update")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(PERMISSION_CODE)
</#if>
	AjaxJson update(${t.modelName} ${t.varNameSimple}){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(PERMISSION_CODE);
</#if>
		int line = ${t.varName}Mapper.update(${t.varNameSimple});
		return AjaxJson.getByLine(line);
	}

	/** 查 */  
	@${cfg.apiMappingWayString}("getById")
	AjaxJson getById(${t.primaryKey.fieldType} id){
		${t.modelName} ${t.varNameSimple} = ${t.varName}Mapper.getById(id);
		return AjaxJson.getSuccessData(${t.varNameSimple});
	}

	/** 查 - 集合（参数为null或0时默认忽略此条件） */  
	@${cfg.apiMappingWayString}("getList")
	AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
		List<${t.modelName}> list = ${t.varName}Mapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
	
	
	// ------------------------- 前端接口 -------------------------
	
	
	// 如需使用此接口，你可能需要删除鉴权代码, 并添加鉴别身份代码  
	/** 改 - 空值不改【G】 */  
	@RequestMapping("updateByNotNull")
<#if cfg.saTokenAuthWay == 2 >	@SaCheckPermission(PERMISSION_CODE)
</#if>
	AjaxJson updateByNotNull(){
<#if cfg.saTokenAuthWay == 1 >		StpUtil.checkPermission(PERMISSION_CODE);
</#if>
		SoMap so = SoMap.getRequestSoMap();
		// 鉴别身份，是否为数据创建者
		/*long userId = SP.publicMapper.getColumnByIdToLong("${t.tableName}", "user_id", so.get("id"));*/
		/*AjaxError.throwBy(userId != StpUserUtil.getLoginIdAsLong(), "此数据您无权限修改");*/
		// 开始修改 (请只保留需要修改的字段)
		so.clearNotIn(${t.getAllColumnString3()}).clearNull()<#if cfg.modelStyle == 2 >.humpToLineCase()</#if>;	
		int line = SP.publicMapper.updateBySoMapBy("${t.tableName}", so, "id", so.get("id"));
		return AjaxJson.getByLine(line);
	}
	
	
	
	
	

}
