package ${t.packagePath};

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ${cfg.package_utils};

/**
 * Mapper: ${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 */
@Mapper
public interface ${t.mkNameBig}Mapper {

	/**
	 * 增  
	 * @param ${t.varName} 新增对象 
	 * @return 受影响行数 
	 */
	int add(${t.modelName} ${t.varName});

	/**
	 * 删  
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(${t.primaryKey.fieldType} ${t.primaryKey.fieldName});	<#-- 根据主键 --> 

	/** 
	 * 改  
	 * @param ${t.varName} 修改对象 
	 * @return 受影响行数 
	 */
	int update(${t.modelName} ${t.varName});

	/** 
	 * 查  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	${t.modelName} getById(${t.primaryKey.fieldType} ${t.primaryKey.fieldName});	<#-- 根据主键 --> 

	/**
	 * 查 - 集合（参数为null或0时默认忽略此条件）
	 * @param so 参数集合 
	 * @return 数据集合 
	 */
	List<${t.modelName}> getList(SoMap so);


}
