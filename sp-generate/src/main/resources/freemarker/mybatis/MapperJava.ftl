package ${t.packagePath};

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pj.utils.so.*;
<#if cfg.mybatisPlus>
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
</#if>
import org.springframework.stereotype.Repository;

/**
 * Mapper: ${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 */

@Mapper
@Repository
public interface ${t.mkNameBig}Mapper<#if cfg.mybatisPlus> extends BaseMapper <${t.modelName}></#if> {

	/**
	 * 增  
	 * @param ${t.varNameSimple} 实体对象 
	 * @return 受影响行数 
	 */
	int add(${t.modelName} ${t.varNameSimple});

	/**
	 * 删  
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(${t.primaryKey.fieldType} ${t.primaryKey.fieldName});	<#-- 根据主键 --> 

	/** 
	 * 改  
	 * @param ${t.varNameSimple} 实体对象 
	 * @return 受影响行数 
	 */
	int update(${t.modelName} ${t.varNameSimple});

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	${t.modelName} getById(${t.primaryKey.fieldType} ${t.primaryKey.fieldName});	<#-- 根据主键 --> 

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<${t.modelName}> getList(SoMap so);


}
