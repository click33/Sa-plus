package ${t.packagePath};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${cfg.package_utils};

/**
 * Service: ${t.tableName} -- ${t.tableComment}
 * @author ${cfg.author} 
 */
@Service
public class ${t.mkNameBig}Service {

	/** 底层 Mapper 对象 */
	@Autowired
	${t.mkNameBig}Mapper ${t.varName}Mapper;

	/** 增 */
	int add(${t.modelName} ${t.varNameSimple}){
		return ${t.varName}Mapper.add(${t.varNameSimple});
	}

	/** 删 */
	int delete(${t.primaryKey.fieldType} ${t.primaryKey.fieldName}){
		return ${t.varName}Mapper.delete(${t.primaryKey.fieldName});
	}

	/** 改 */
	int update(${t.modelName} ${t.varNameSimple}){
		return ${t.varName}Mapper.update(${t.varNameSimple});
	}

	/** 查 */
	${t.modelName} getById(${t.primaryKey.fieldType} ${t.primaryKey.fieldName}){
		return ${t.varName}Mapper.getById(${t.primaryKey.fieldName});
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	List<${t.modelName}> getList(SoMap so) { 
		return ${t.varName}Mapper.getList(so);	
	}
	

}
