package com.pj.project4sp.admin4login;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pj.utils.so.*;
import org.springframework.stereotype.Repository;

/**
 * Mapper: sp_admin_login -- 管理员登录日志表 
 * 
 * @author shengzhang 
 */
@Mapper
@Repository
public interface SpAdminLoginMapper {

	/**
	 * 增  
	 * @param s 实体对象 
	 * @return 受影响行数 
	 */
	int add(SpAdminLogin s);

	/**
	 * 删  
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(Long id);	 

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	SpAdminLogin getById(Long id);	 

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<SpAdminLogin> getList(SoMap so);

}
